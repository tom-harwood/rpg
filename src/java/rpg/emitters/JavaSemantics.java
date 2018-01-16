package rpg.emitters;

import java.util.Locale;
import java.util.Map;

import org.stringtemplate.v4.AttributeRenderer;

import rpg.Options;
public class JavaSemantics extends Semantics
{
    JavaSemantics(Options options, Object expectedValue)
    {
        super(options, expectedValue);
    }

    @Override
    public String toString(Object o, String formatString, Locale locale)
    {
        if ("hashCode".equals(formatString)) {
            return String.format("%x", o.hashCode());
        } else if ("className".equals(formatString)) {
            return "Potzrebie";

        } else if ("declare.dependent.variables".equals(formatString)) {
            return "// Declare dependent vars...";

        } else if ("expected.value".equals(formatString)) {
            return expectedValue.toString();

        } else if ("grammar.name".equals(formatString)) {
            return options.codeGeneratorGrammar;

        } else if ("timestamp".equals(formatString)) {
            return new java.util.Date().toString();

        } else if ("typename".equals(formatString)) {
            return "int";

        } else if ("verifier.type".equals(formatString)) {
            return "int";

        } else if ("version".equals(formatString)) {
            return "I dunno... 0.1?";

        } else if ("typename".equals(formatString)) {
            return "int";

        } else {
            return o.toString();
        }
    }
}
