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
public class Token {
    public static final int EPSILON=0;
    public static final int IF=1;
    public static final int GOTO=2;
    public static final int OPEN_BRACKET=3;
    public static final int CLOSED_BRACKET=4;
    public static final int EVENT=5;
    public static final int PERSON=6;
    
    public final int token;
    public final String sequence;
    
    public Token(int token, String sequence)
    {
        super();
        this.token=token;
        this.sequence=sequence;
        
    }
}
