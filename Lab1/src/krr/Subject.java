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
public class Subject {
    public static final String[] SUBJECTS={"Alfred","Beth","Claire","Daniel"};
    
    public static String getAllSubjectsRegex()
    {
        StringBuilder sb=new StringBuilder();
        
        for(String s:Subject.SUBJECTS){
            sb.append(s);
            sb.append("|");
        }
        if(sb.charAt(sb.length()-1)=='|') 
            sb.deleteCharAt(sb.length()-1);
        
        return sb.toString();
    }
    
}
