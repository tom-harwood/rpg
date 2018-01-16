package rpg.emitters;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import rpg.Options;
import rpg.models.Node;
import rpg.models.Nonterminal;

import org.stringtemplate.v4.ST;

import jburg.ProductionTable;
import jburg.Reducer;
import jburg.frontend.XMLGrammar;

/**
 * The Emitter loads an emitter grammar
 * and uses it to reduce the testcase Node
 * tree.
 */
public class Emitter
{
    final Options options;
    Reducer<Nonterminal, Node> reducer;

    public Emitter(Options options, Object expectedValue)
    throws Exception
    {
        this.options = options;

        XMLGrammar<Nonterminal,Node> grammarBuilder = new XMLGrammar<Nonterminal,Node>("rpg.models.Nonterminal", "rpg.models.ArithmeticOpcode");
        ProductionTable<Nonterminal, Node> productions = grammarBuilder.build(convertToFileURL(options.codeGeneratorGrammar));
        this.reducer = new Reducer<Nonterminal,Node>(createVisitor(expectedValue), productions);
    }

    public void generate(Node root, Object verifierToken)
    throws Exception
    {
        reducer.label(root);
        ST macGuffin = (ST)reducer.reduce(root, Nonterminal.Program);
        PrintWriter out = new PrintWriter(new FileWriter(options.outputFile));
        out.println(macGuffin.render());
        out.flush();
        out.close();
    }

    public static String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    private Visitor createVisitor(Object expectedValue)
    throws Exception
    {
        TemplateGroup templates;

        if (options.outputFile.endsWith(".java")) {
            templates = new TemplateGroup("src/templates", "java.stg");

        } else {
            throw new IllegalArgumentException(String.format("Don't know how to emit %s", options.outputFile));
        }

        Semantics semantics = (Semantics)Class.forName(options.codeGeneratorSemantics).getDeclaredConstructor(Options.class, Object.class).newInstance(options, expectedValue);
        templates.registerRenderer(Object.class, semantics);

        Visitor result = (Visitor)Class.forName(options.codeGeneratorVisitor).newInstance();
        result.setTemplates(templates);
        result.setSemantics(semantics);
        return result;
    }
}
