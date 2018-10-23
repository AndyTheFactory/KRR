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
public interface ExpressionNode {
    public static final int VARIABLE_NODE   =1;
    public static final int OR_NODE         =2;
    public static final int AND_NODE        =3;
    public static final int NOT_NODE        =4;
    public static final int IMPLIES_NODE    =5;
    public static final int IFF_NODE        =6;
    public static final int BOX_NODE        =7;
    public static final int DIAMOND_NODE    =8;
    
    public int getType();
    public boolean getValue();
    
    public String toString();
}
