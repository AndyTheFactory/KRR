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
public class ParserException extends RuntimeException
{
    private Token token=null;
    
    public ParserException(String message)
    {
        super(message);
    }
    public ParserException(String message, Token token)
    {
        super(String.format(message, token.sequence));
        this.token=token;
    }
    
}
