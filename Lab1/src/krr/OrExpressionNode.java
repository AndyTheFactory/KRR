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
public class OrExpressionNode  extends OperatorExpressionNode{

    public OrExpressionNode(ExpressionNode lhs, ExpressionNode rhs) {
        super(lhs, rhs);
    }
    
    public int getType()
    {
        return ExpressionNode.OR_NODE;
    }
    public boolean getValue()
    {
        return lhs.getValue() | rhs.getValue();
    }
    
}
