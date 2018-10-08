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
public class ExpressionHelper {
    public static ExpressionNode normalizeNot(ExpressionNode expr, boolean negate)
    {
        ExpressionNode newexpr;
        
        if(negate){
            switch(expr.getType()){
                default:
                case ExpressionNode.VARIABLE_NODE:
                {
                    VariableExpressionNode v=(VariableExpressionNode) expr;
                    if (negate) v.negation=!v.negation;
                    newexpr=v;
                    break;
                }
                case ExpressionNode.OR_NODE:
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    newexpr=new AndExpressionNode(
                        ExpressionHelper.normalizeNot(n.lhs,true),
                        ExpressionHelper.normalizeNot(n.rhs,true)
                    );
                    break;
                }
                case ExpressionNode.AND_NODE:
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    newexpr=new OrExpressionNode(
                        ExpressionHelper.normalizeNot(n.lhs,true),
                        ExpressionHelper.normalizeNot(n.rhs,true)
                    );
                    break;
                }
                case ExpressionNode.IMPLIES_NODE:                
                case ExpressionNode.IFF_NODE:                
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    newexpr=ExpressionHelper.normalizeNot(ExpressionHelper.normalizeNot(expr,false), true);
                    break;
                }
                case ExpressionNode.NOT_NODE:
                {
                    NotExpressionNode v=(NotExpressionNode) expr;
                    newexpr=v.lhs;
                    break;
                }


            }
        }else{
            switch(expr.getType()){
                default:
                case ExpressionNode.VARIABLE_NODE:
                {
                    newexpr=expr;
                    break;
                }
                case ExpressionNode.OR_NODE:
                case ExpressionNode.AND_NODE:
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    n.lhs=ExpressionHelper.normalizeNot(n.lhs, false);
                    n.rhs=ExpressionHelper.normalizeNot(n.rhs, false);
                    newexpr=n;
                    break;
                }
                case ExpressionNode.IMPLIES_NODE:                
                {
                    ImpliesExpressionNode n=(ImpliesExpressionNode) expr;                    
                    newexpr=new OrExpressionNode(
                                ExpressionHelper.normalizeNot(n.lhs,true),
                                ExpressionHelper.normalizeNot(n.rhs,false)
                            );
                    break;
                }
                case ExpressionNode.IFF_NODE:                
                {
                    IffExpressionNode n=(IffExpressionNode) expr;                    
                    newexpr=new OrExpressionNode(
                                new AndExpressionNode(ExpressionHelper.normalizeNot(n.lhs,false), ExpressionHelper.normalizeNot(n.rhs,false)),
                                new AndExpressionNode(ExpressionHelper.normalizeNot(n.lhs,true), ExpressionHelper.normalizeNot(n.rhs,true))
                            );
                    break;
                }
                case ExpressionNode.NOT_NODE:
                {
                    NotExpressionNode v=(NotExpressionNode) expr;
                    newexpr=ExpressionHelper.normalizeNot(v.lhs, true);
                    break;
                }


            }
        }
        return newexpr;
    }
    public static String printExpression(ExpressionNode expr)
    {
        StringBuilder res=new StringBuilder();
        
        switch(expr.getType()){
            case ExpressionNode.VARIABLE_NODE:
            {
                VariableExpressionNode v=(VariableExpressionNode) expr;
                if (v.negation) res.append('¬');
                res.append(v.name+" ");
                break;
            }
            case ExpressionNode.OR_NODE:
            case ExpressionNode.AND_NODE:
            case ExpressionNode.IMPLIES_NODE:                
            case ExpressionNode.IFF_NODE:                
            {
                OperatorExpressionNode n=(OperatorExpressionNode) expr;
                res.append("("+ExpressionHelper.printExpression(n.lhs)+" ");
                res.append(n.toString()+" ");
                res.append(ExpressionHelper.printExpression(n.rhs)+") ");
                break;
            }
            case ExpressionNode.NOT_NODE:
            {
                NotExpressionNode v=(NotExpressionNode) expr;
                res.append('¬');
                res.append("("+ExpressionHelper.printExpression(v.lhs)+") ");
                break;
            }
               
            
        }
        return res.toString();
    }
    
}

