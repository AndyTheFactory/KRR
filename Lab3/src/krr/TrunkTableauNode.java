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
public class TrunkTableauNode implements TableauNode
{
    public TableauNode next;
    public TableauNode parent;
    public int world=1;
    ExpressionNode expr;
    public int getType()
    {
        return TableauNode.TRUNK_NODE;
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
