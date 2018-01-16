package rpg;

import rpg.emitters.*;
import rpg.models.*;
import rpg.options.*;

public class Main
{
    public static void main(String[] args)
    throws Exception
    {
        Options options = new Options();
        OptionParser parser = new OptionParser();
        parser.setOptions(args, options);
        parser.validate(options);

        if (parser.hasErrors()) {
            for (String s: parser.getDiagnostics()) {
                System.err.println(s);
            }
            System.exit(1);
        }

        RpgModel model = (RpgModel)Class.forName(options.model).newInstance();
        model.setOptions(options);
        Object expected = model.getExpected();
        Node root = model.generate();

        if (options.verbose) {
            System.out.printf("expected: %s\n", model.getExpected());
            System.out.println(root);
        }

        Emitter emitter = new Emitter(options, expected);
        emitter.generate(root, expected);
    }
}
