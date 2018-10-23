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
public class VariableTableauNode implements TableauNode
{
    public String var;
    public boolean not;
    public TableauNode next;
    public TableauNode parent;
    public int world=1;
    
    public VariableTableauNode(String name,boolean not)
    {
        this.var=name;
        this.not=not;
    }
    public VariableTableauNode(String name)
    {
        this.var=name;
        this.not=false;
    }
    
    public int getType()
    {
        return TableauNode.VARIABLE_NODE;
    }
        
    @Override
    public int getWorld()
    {
        return world;
    }
    
    @Override
    public ExpressionNode getExpression()
    {
        VariableExpressionNode expr=new VariableExpressionNode(var);
        expr.negation=not;
        return expr;
    }
    
}
