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
public interface TableauNode
{
    public final int TRUNK_NODE=1;
    public final int BRANCH_NODE=2;
    public final int VARIABLE_NODE=3;
    
    public int getType();
    
}
