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
    public static final int EPSILON             =0;
    public static final int IF                  =1;
    public static final int IFF                 =2;
    public static final int OPEN_BRACKET        =3;
    public static final int CLOSED_BRACKET      =4;
    public static final int NOT                 =5;
    public static final int AND                 =6;
    public static final int OR                  =7;
    public static final int BOX                 =8;
    public static final int DIAMOND             =9;
    public static final int VARIABLE            =10;
    
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
            case Token.IFF: return "IFF";
            case Token.OPEN_BRACKET: return "O_BRACKET";
            case Token.CLOSED_BRACKET: return "C_BRACKET";
            case Token.NOT: return "NOT";
            case Token.OR: return "OR";
            case Token.AND: return "AND";
            case Token.BOX: return "[]";
            case Token.DIAMOND: return "<>";
            case Token.VARIABLE: return "VAR";
        }
    }
}
