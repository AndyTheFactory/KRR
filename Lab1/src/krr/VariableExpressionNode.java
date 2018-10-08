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
public class VariableExpressionNode implements ExpressionNode{
    public String name;
    public boolean negation;
    public boolean value;
    
    public VariableExpressionNode(String name)
    {
        this.name=name;
        this.value=true;
        this.negation=false;
        
    }
    public VariableExpressionNode(String name,boolean value)
    {
        this.name=name;
        this.value=value;
        this.negation=false;
    }
    public int getType()
    {
        return ExpressionNode.VARIABLE_NODE;
    }
    public boolean getValue()
    {
        return negation?!value:value;
    }
    
    public String toString()
    {
        return String.format("Var(%s)", name);
    }
            
}
