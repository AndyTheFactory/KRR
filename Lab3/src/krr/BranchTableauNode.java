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
public class BranchTableauNode implements TableauNode
{
    public TableauNode left;
    public TableauNode right;
    public TableauNode parent;
    ExpressionNode expr;
    
    public int world=1;
    
    public int getType()
    {
        return TableauNode.BRANCH_NODE;
    }

    @Override
    public int getWorld()
    {
        return world;
    }

    @Override
    public ExpressionNode getExpression()
    {
        return expr;
    }
        
    
    
}
