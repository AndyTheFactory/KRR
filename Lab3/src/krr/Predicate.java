/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;

import static krr.Subject.SUBJECTS;

/**
 *
 * @author Dell
 */
public class Predicate {
    public static final String[] PREDICATES={"is","walk","full of"};
    
    public static String getAllPredicateRegex()
    {
        StringBuilder sb=new StringBuilder();
        
        for(String s:Predicate.PREDICATES){
            sb.append(s);
            sb.append("|");
        }
        if(sb.charAt(sb.length()-1)=='|') 
            sb.deleteCharAt(sb.length()-1);
        
        return sb.toString();
    }
   public static  int getHash(String sequence)
    {
        int i;
        for(i=0;i<PREDICATES.length;i++){
            if (sequence.equals(PREDICATES[i])) return i;
        }
        return -1;
    }
     
}
