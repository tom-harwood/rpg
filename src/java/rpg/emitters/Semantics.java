package rpg.emitters;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.stringtemplate.v4.AttributeRenderer;

import rpg.Options;

@SuppressWarnings("unchecked")
public abstract class Semantics implements AttributeRenderer
{
    final Options options;
    final Object expectedValue;

    Semantics(Options options, Object expectedValue)
    {
        this.options = options;
        this.expectedValue = expectedValue;
    }
}
