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
        res=ifOp();        
        if(res==null) res=affirmOp();
        return res;
    }
    private ExpressionNode expression(ExpressionNode expr)
    {
        ExpressionNode res;
        switch(lookahead.token){
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
    
    private ExpressionNode ifOp()
    {
        if(lookahead.token==Token.IF){
            NextToken();
            NotExpressionNode lhs=new NotExpressionNode(affirmOp());
            ExpressionNode rhs=thenOp();     
            OrExpressionNode exp=new OrExpressionNode(lhs, rhs);
                    
            return exp;
        }else
            return null;
    }
    private ExpressionNode affirmOp()
    {
        if (lookahead.token==Token.OPEN_BRACKET){
            NextToken();
            ExpressionNode expr=affirmOp();
            if (lookahead.token!=Token.CLOSED_BRACKET)
                throw new ParserException(String.format("Expected ) but found %s", lookahead.toString()));
            NextToken();
            return expr;
        }else if (lookahead.token==Token.NOT){
            NextToken();
            NotExpressionNode expr=new NotExpressionNode(affirmOp());
            return expr;
            
        }else if (lookahead.token==Token.SUBJECT){
            VariableExpressionNode expr=new VariableExpressionNode(lookahead.sequence);
            NextToken();
            if (lookahead.token!=Token.PREDICATE)
                throw new ParserException(String.format("Expected Predicate but found %s", lookahead.toString()));
            NextToken();
            if (lookahead.token!=Token.OBJECT)
                throw new ParserException(String.format("Expected Object but found %s", lookahead.toString()));
            NextToken();
            
            if (lookahead.token==Token.EPSILON)
                return expr;
            else
                return expression(expr);
            
        }else if (lookahead.token==Token.EPSILON){
            return null;
        }
        return null;
        
    }
    private ExpressionNode thenOp()
    {
        if (lookahead.token==Token.THEN){
            ExpressionNode expr=affirmOp();
            if (lookahead.token==Token.UNLESS){
                NotExpressionNode expr2=new NotExpressionNode(affirmOp());
                IffExpressionNode res=new IffExpressionNode(expr2, expr);
                return res;
            }else 
                return expr;
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
