/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author andrei
 */
public class Parser {
    LinkedList<Token> tokens;
    Token lookahead;
    
    public void parse(LinkedList<Token> tokens)
    {
        this.tokens=(LinkedList<Token>)tokens.clone();
     
        lookahead=this.tokens.getFirst();
        
        expression();
        
        if (lookahead.token!=Token.EPSILON)
            throw new ParserException("Unexpected %s in expression",lookahead);
       
    }
    private void expression()
    {
        ifOp();
        affirmOp();
    }
    
    private void ifOp()
    {
        if(lookahead.token==Token.IF){
            NextToken();
            affirmOp();
            thenOp();            
        }
    }
    private void affirmOp()
    {
        
    }
    private void thenOp()
    {
        if (lookahead.token==Token.THEN){
            affirmOp();
            unlessOp();
        }
    }
    private void unlessOp()
    {
        if (lookahead.token==Token.UNLESS){
            affirmOp();
        }        
    }
    private void NextToken()
    {
        tokens.pop();
        if (tokens.isEmpty())
            lookahead=new Token(Token.EPSILON,"",-1);
        else
            lookahead=tokens.getFirst();
              
    }
           
}
