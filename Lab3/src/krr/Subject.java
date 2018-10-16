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
    public static final String[] SUBJECTS={"Weather","Park","People"};
    
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
    public static int getHash(String sequence)
    {
        int i;
        for(i=0;i<Subject.SUBJECTS.length;i++){
            if (sequence.equals(SUBJECTS[i])) return i;
        }
        return -1;
    }
    
}
