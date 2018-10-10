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
    public static final int THEN=2;
    public static final int IFF=3;
    public static final int OPEN_BRACKET=4;
    public static final int CLOSED_BRACKET=5;
    public static final int NOT=6;
    public static final int AND=7;
    public static final int OR=8;
    public static final int SUBJECT=9;
    public static final int PREDICATE=10;
    public static final int OBJECT=11;
    
    public final int token;
    public final String sequence;
    public final int pos;
    
    public Token(int token, String sequence,int pos)
    {
        super();
        this.token=token;
        this.sequence=sequence;
        this.pos = pos;    
    }
    public String toString()
    {
        switch (this.token){
            default:
            case Token.EPSILON: return "€";
            
            case Token.IF: return "IF";
            case Token.THEN: return "THEN";
            case Token.IFF: return "IFF";
            case Token.OPEN_BRACKET: return "O_BRACKET";
            case Token.CLOSED_BRACKET: return "C_BRACKET";
            case Token.NOT: return "NOT";
            case Token.OR: return "OR";
            case Token.AND: return "AND";
            case Token.SUBJECT: return "[Subject]";
            case Token.PREDICATE: return "[Pred]";
            case Token.OBJECT: return "[Object]";
        
        }
    }
}
