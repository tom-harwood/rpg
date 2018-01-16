package rpg.emitters;

import rpg.models.Node;

public class ArithmeticVisitor extends Visitor
{
    public Object program(Node n, Object x)
    {
        return templates.getTemplate("classDef", "node", n, "computation", x);
    }

    public Object intLiteral(Node n)
    {
        return templates.getTemplate("intLiteral", "node", n);
    }

    public Object add(Node n, Object lhs, Object rhs)
    {
        return templates.getTemplate("simpleBinop", "operator", "+", "lhs", lhs, "rhs", rhs);
    }

    public Object subtract(Node n, Object lhs, Object rhs)
    {
        return templates.getTemplate("simpleBinop", "operator", "-", "lhs", lhs, "rhs", rhs);
    }

    public Object multiply(Node n, Object lhs, Object rhs)
    {
        return templates.getTemplate("simpleBinop", "operator", "*", "lhs", lhs, "rhs", rhs);
    }

    public Object divide(Node n, Object lhs, Object rhs)
    {
        return templates.getTemplate("simpleBinop", "operator", "/", "lhs", lhs, "rhs", rhs);
    }

    public Object modulus(Node n, Object lhs, Object rhs)
    {
        return templates.getTemplate("simpleBinop", "operator", "%", "lhs", lhs, "rhs", rhs);
    }
}
