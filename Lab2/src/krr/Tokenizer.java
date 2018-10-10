/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author andrei
 */
public class Tokenizer {

    private class TokenInfo
    {
        public final Pattern regex;
        public final int token;
        public TokenInfo(Pattern regex, int token)
        {
            super();
            this.regex = regex;
            this.token = token;
        }
    }

    private LinkedList<TokenInfo> tokenInfos;
    private LinkedList<Token> tokens;
    private static Tokenizer expressionTokenizer = null;

    public Tokenizer()
    {
        super();
        tokenInfos = new LinkedList<TokenInfo>();
        tokens = new LinkedList<Token>();
    }

    public static Tokenizer getExpressionTokenizer()
    {
        if (expressionTokenizer == null)
          expressionTokenizer = createExpressionTokenizer();
        return expressionTokenizer;
    }
    private static Tokenizer createExpressionTokenizer()
    {
      Tokenizer tokenizer = new Tokenizer();
      tokenizer.add("IF[\\s\\(]", Token.IF);
      tokenizer.add("THEN[\\s\\(]", Token.THEN);
      tokenizer.add("IFF[\\s\\(]", Token.IFF);
      tokenizer.add("\\(", Token.OPEN_BRACKET);
      tokenizer.add("\\)", Token.CLOSED_BRACKET);
      tokenizer.add("NOT[\\s\\(]", Token.NOT);
      tokenizer.add("AND[\\s\\(]", Token.AND);
      tokenizer.add("OR[\\s\\(]", Token.OR);

      String subjects = Subject.getAllSubjectsRegex();
      tokenizer.add( subjects + "[\\s\\)]", Token.SUBJECT);

      String predicates = Predicate.getAllPredicateRegex();
      tokenizer.add( predicates + "[\\s\\)]", Token.PREDICATE);

      String lobjects = LObject.getAllObjectsRegex();
      tokenizer.add( lobjects + "([\\s\\)]|$)", Token.OBJECT);

      return tokenizer;
    }
    public void add(String regex, int token)
    {
      tokenInfos.add(new TokenInfo(Pattern.compile("(?i)^(" + regex+")"), token));
    }

    public void tokenize(String str)
    {
        String s = str.trim();
        int totalLength = s.length();
        tokens.clear();
        while (!s.equals(""))
        {
          int remaining = s.length();
          boolean match = false;
          for (TokenInfo info : tokenInfos)
          {
            Matcher m = info.regex.matcher(s);
            if (m.find())
            {
              match = true;
              String tok = m.group().trim();
              if (info.token!=Token.OPEN_BRACKET && tok.length()>1 && tok.charAt(tok.length()-1)=='(') {
                s = m.replaceFirst("(").trim();
                tok=tok.substring(0,tok.length()-1);
              }else
              if (info.token!=Token.CLOSED_BRACKET && tok.length()>1 && tok.charAt(tok.length()-1)==')') {
                s = m.replaceFirst(")").trim();
                tok=tok.substring(0,tok.length()-1);
              }
              else
                s = m.replaceFirst("").trim();


              tokens.add(new Token(info.token, tok, totalLength - remaining));
              break;
            }
          }
          if (!match)
            throw new ParserException("Unexpected character in input: " + s);
        }
    }
    public LinkedList<Token> getTokens()
    {
      return tokens;
    }
    public String toString()
    {
        StringBuilder res=new StringBuilder();
        for(Token token:tokens){
            res.append(String.format("At %d - %s\n",token.pos,token.toString()));
        }
        return res.toString();
    }

}
