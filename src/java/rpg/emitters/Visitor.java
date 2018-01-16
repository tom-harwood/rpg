package rpg.emitters;

public abstract class Visitor
{
    TemplateGroup   templates;
    Semantics       semantics;

    public void setTemplates(TemplateGroup templates)
    {
        this.templates = templates;
    }

    public void setSemantics(Semantics semantics)
    {
        this.semantics = semantics;
    }
}
