/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;

import java.util.LinkedList;
/**
 *
 * @author andrei
 */
public abstract class OperatorExpressionNode implements ExpressionNode{
    public ExpressionNode lhs;
    public ExpressionNode rhs;
    
    public OperatorExpressionNode(ExpressionNode lhs,ExpressionNode rhs)
    {
        this.lhs=lhs;
        this.rhs=rhs;        
    }
    
    public String toString()
    {
        switch(this.getType()){
            default:
            case ExpressionNode.VARIABLE_NODE: return "Var";
            case ExpressionNode.OR_NODE: return "V";
            case ExpressionNode.AND_NODE: return "Ʌ";
            case ExpressionNode.NOT_NODE: return "¬";
            case ExpressionNode.IMPLIES_NODE: return "->";
            case ExpressionNode.IFF_NODE: return "<=>";
        }
    }
    
}
