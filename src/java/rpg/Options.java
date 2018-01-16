package rpg;

import rpg.options.*;

public class Options
{
    public String outputFile;

    @Optional
    public String model = "rpg.models.Arithmetic";

    @Optional
    public String codeGeneratorGrammar = "src/grammars/JavaArithmetic.jbg";

    @Optional
    public String codeGeneratorVisitor = "rpg.emitters.ArithmeticVisitor";

    @Optional
    public String codeGeneratorSemantics = "rpg.emitters.JavaSemantics";

    @Optional
    public int scaleFactor = 100000;

    @Optional
    public int maxDepth = 7;

    @Optional
    public boolean verbose = false;
}
