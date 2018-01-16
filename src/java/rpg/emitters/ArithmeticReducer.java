package rpg.emitters;

import rpg.models.Node;

public class ArithmeticReducer
{
    public String verifyProgram(Node n, Integer computedResult)
    {
        Integer expected = new Integer(n.getPayload().toString());
        return String.format("%s == %s? %s", expected, computedResult, expected.equals(computedResult));
    }

    public Integer add(Node n, Integer lhs, Integer rhs)
    {
        return lhs + rhs;
    }

    public Integer subtract(Node n, Integer lhs, Integer rhs)
    {
        return lhs - rhs;
    }

    public Integer multiply(Node n, Integer lhs, Integer rhs)
    {
        return lhs * rhs;
    }

    public Integer divide(Node n, Integer lhs, Integer rhs)
    {
        return lhs / rhs;
    }

    public Integer modulus(Node n, Integer lhs, Integer rhs)
    {
        return lhs % rhs;
    }

    public Integer intLiteral(Node n)
    {
        return new Integer(n.getPayload().toString());
    }
}
