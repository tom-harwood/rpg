package rpg.models;

import rpg.Options;

public interface RpgModel<T>
{
    /**
     * Set options.
     */
    public void setOptions(Options ops);

    /**
     * Generate a program using current settings.
     */
    public Node generate();

    /**
     * Get the verifying result.
     */
    public Object getExpected();
}
