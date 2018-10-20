/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author andrei
 */
public class Parser {
    LinkedList<Token> tokens;
    Token lookahead;
    ArrayList<String> variables;
    
    public Parser()
    {
        variables=new ArrayList<>();
    }
    public ExpressionNode parse(String line)
    {
        Tokenizer tk=Tokenizer.getExpressionTokenizer();
        tk.tokenize(line);
        LinkedList<Token> tokens=tk.getTokens();
        
        return parse(tokens);
    }
    
    public ExpressionNode parse(LinkedList<Token> tokens)
    {
        this.tokens=(LinkedList<Token>)tokens.clone();
     
        lookahead=this.tokens.getFirst();
        
        ExpressionNode expr=expression();
        
        if (lookahead.token!=Token.EPSILON)
            throw new ParserException("Unexpected %s in expression",lookahead);
        return expr;
    }
    private ExpressionNode expression()
    {
        ExpressionNode res;
        res=affirmOp();
        return res;
    }
    private ExpressionNode expression(ExpressionNode expr)
    {
        ExpressionNode res;
        switch(lookahead.token){
            case Token.IF:
                NextToken();
                res = new ImpliesExpressionNode(expr, affirmOp());
                break;
            case Token.IFF:
                NextToken();
                res = new IffExpressionNode(expr, affirmOp());
                break;
            case Token.AND:
                NextToken();
                res = new AndExpressionNode(expr, affirmOp());
                break;
            case Token.OR:
                NextToken();
                res = new OrExpressionNode(expr, affirmOp());
                break;
            default:
                return expr;
        }
        return res;
    }
    private ExpressionNode affirmOp()
    {
        switch (lookahead.token) {
            case Token.OPEN_BRACKET:
            {
                NextToken();
                ExpressionNode expr=affirmOp();
                if (lookahead.token!=Token.CLOSED_BRACKET)
                    throw new ParserException(String.format("Expected ) but found %s", lookahead.toString()));
                NextToken();
                if (lookahead.token==Token.EPSILON)
                    return expr;
                else
                    return expression(expr);
            }
            case Token.NOT:
            {
                NextToken();
                NotExpressionNode expr=new NotExpressionNode(affirmOp());
                return expr;
                
            }
            case Token.BOX:
            {
                NextToken();
                BoxExpressionNode expr=new BoxExpressionNode(affirmOp());
                return expr;
                
            }
            case Token.DIAMOND:
            {
                NextToken();
                DiamondExpressionNode expr=new DiamondExpressionNode(affirmOp());
                return expr;
                
            }
            case Token.VARIABLE:
            {
                StringBuilder s=new StringBuilder(lookahead.sequence);
                
                VariableExpressionNode expr=new VariableExpressionNode(lookahead.sequence);
                NextToken();
                expr.name=s.toString();
                
                if (!variables.contains(expr.name.toUpperCase()))
                    variables.add(expr.name.toUpperCase());
                
                if (lookahead.token==Token.EPSILON ||lookahead.token==Token.CLOSED_BRACKET )
                    return expr;
                else
                    return expression(expr);

            }
            default:
            case Token.EPSILON:
                break;
        }
        return null;
        
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
