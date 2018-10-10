/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;

/**
 *
 * @author Dell
 */
public class LObject {
    public static final String[] OBJECTS={"Sunny","Beautiful","Dogs"};
    
    public static String getAllObjectsRegex()
    {
        StringBuilder sb=new StringBuilder();
        
        for(String s:LObject.OBJECTS){
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
        for(i=0;i<OBJECTS.length;i++){
            if (sequence.equals(OBJECTS[i])) return i;
        }
        return -1;
    }
     
}
