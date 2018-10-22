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
public class IffExpressionNode  extends OperatorExpressionNode{

    public IffExpressionNode(ExpressionNode lhs, ExpressionNode rhs) {
        super(lhs, rhs);
    }
    public int getType()
    {
        return ExpressionNode.IFF_NODE;
    }
    public boolean getValue()
    {
        return (lhs.getValue() && rhs.getValue())|
                    (!lhs.getValue() && !rhs.getValue());
    }
    
}
