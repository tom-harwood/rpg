package rpg.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import rpg.Options;
import jburg.BurgInput;

/**
 * Arithmetic is a simple model that generates
 * self-verifying programs that exercise control flow.
 */
public class Arithmetic implements RpgModel<ArithmeticOpcode>
{
    Options options;
    Integer expected;
    Random prng = new Random();

    @Override
    public void setOptions(Options options)
    {
        this.options = options;
    }

    @Override
    public Object getExpected()
    {
        expected= prng.nextInt(options.scaleFactor);
        return expected;
    }

    @Override
    public rpg.models.Node generate()
    {
        return decompose(expected, 0);
    }

    Node decompose(int value, int depth)
    {
        if (expected == null) {
            expected= prng.nextInt(options.scaleFactor);
        }

        Node result = null;

        if (depth > options.maxDepth || value <= 2) {
            result = new Node(value);

        } else {
            int driverOperand = getPrimeFactor(value);
            int index = prng.nextInt(6);

            switch(index) {
                case 0:
                case 1:
                    result = new Node(ArithmeticOpcode.Add, value, decompose(value - driverOperand, depth+1), decompose(driverOperand, depth+1));
                    break;
                    
                case 2:
                    result = new Node(ArithmeticOpcode.Subtract, value, decompose(value + driverOperand, depth+1), decompose(driverOperand, depth+1));
                    break;
                    
                case 3:
                case 4:
                    result = new Node(ArithmeticOpcode.Multiply, value, decompose(driverOperand, depth+1), decompose(value / driverOperand, depth+1));
                    break;
                    
                case 5:
                    // The driver operand has to evenly divide the value and remain in range of an int.
                    // The prime factor algorithm is deterministic, so fall back on monte carlo.
                    // TODO: Probably faster to adjust the driverOperand.
                    while (value % driverOperand != 0 || ((long)value * (long)driverOperand) >= (long)Integer.MAX_VALUE) {
                        driverOperand = prng.nextInt(value - 2) + 1;
                    }
                    result = new Node(ArithmeticOpcode.Divide, value, decompose(value * driverOperand, depth+1), decompose(driverOperand, depth+1));
                    break;

                default:
                    throw new IllegalStateException(String.format("Generator index %d out of range", index));
            }
        }

        return result;
    }

    /**
     * @return the largest prime factor of the input.
     * @param n the number to be factored.
     */
    int getPrimeFactor(int n)
    {
        while (n%2 == 0) {
            n = n/2;
        }
 
        // n must be odd at this point.  So we can skip 
        // one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(n); i = i+2) {
            // While i divides n, print i and divide n
            while (n%i == 0) {
                n = n/i;
            }
        }
 
        if (n > 2) {
            return n;
        } else {
            return 1;
        }
    }

    static class Node extends rpg.models.Node<ArithmeticOpcode>
    {
        final ArithmeticOpcode  opcode;
        final Integer           value;
        final List<rpg.models.Node>        children;

        Node(int value)
        {
            this.opcode = ArithmeticOpcode.IntLiteral;
            this.value = value;
            this.children = Collections.emptyList();
        }

        Node(ArithmeticOpcode opcode, int value, Node onlyChild)
        {
            this.opcode = opcode;
            this.value = value;
            this.children = new ArrayList<rpg.models.Node>();
            this.children.add(onlyChild);
        }

        Node(ArithmeticOpcode opcode, int value, Node lhs, Node rhs)
        {
            this.opcode = opcode;
            this.value = value;
            this.children = new ArrayList<rpg.models.Node>();
            this.children.add(lhs);
            this.children.add(rhs);
        }

        public Object getPayload()
        {
            if (value != null) {
                return value;
            } else {
                throw new IllegalStateException(String.format("Node has no value: %s", toString()));
            }
        }

        @Override
        public ArithmeticOpcode getNodeType()
        {
            return opcode;
        }

        @Override
        public int getSubtreeCount()
        {
            return children.size();
        }

        @Override
        public BurgInput<Nonterminal, ArithmeticOpcode>  getSubtree(int idx)
        {
            return children.get(idx);
        }


        @Override
        public String toString()
        {
            if (opcode == ArithmeticOpcode.IntLiteral) {
                return value.toString();
            } else {
                return String.format("%s(%d) %s", opcode, value, children);
            }
        }
    }
}
