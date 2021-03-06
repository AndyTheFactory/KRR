/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;

/**
 *
 * @author andrei
 */
public class NotExpressionNode extends OperatorExpressionNode
{

    public NotExpressionNode(ExpressionNode lhs)
    {
        super(lhs, null);
    }

    @Override
    public int getType()
    {
        return ExpressionNode.NOT_NODE;
    }

    @Override
    public boolean getValue()
    {
        return !lhs.getValue();
    }
    
}
