package rpg.models;
import java.util.List;
import jburg.BurgInput;

public abstract class Node<OpcodeType> implements BurgInput<Nonterminal, OpcodeType>
{
    private int     stateNumber = 0;
    private Object  stateInfo = null;

    public abstract Object getPayload();

    @Override
    public abstract OpcodeType   getNodeType();

    @Override
    public abstract int getSubtreeCount();

    @Override
    public abstract BurgInput<Nonterminal, OpcodeType>  getSubtree(int idx);

    @Override
    public void setStateNumber(int stateNumber)
    {
        this.stateNumber = stateNumber;
    }

    @Override
    public int getStateNumber()
    {
        return this.stateNumber;
    }

    @Override
    public void setTransitionTableLeaf(Object ttleaf)
    {
        this.stateInfo = ttleaf;
    }

    @Override
    public Object getTransitionTableLeaf()
    {
        return this.stateInfo;
    }
}
