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
    public int getType()
    {
        return TableauNode.TRUNK_NODE;
    }
    
        
}
