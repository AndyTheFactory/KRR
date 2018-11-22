/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab6;

import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class Expresie {
    ArrayList<String> X;
    ArrayList<String> Y;
    ArrayList<String> Z;
    public Expresie(){
        X=new ArrayList<>();
        Y=new ArrayList<>();
        Z=new ArrayList<>();
        
    }
    public String toString(){
       StringBuilder sb=new StringBuilder();
       
       for(String x:X){
           sb.append(x);
       }
       sb.append(";");
       for(String y:Y){
           sb.append(y);
       }
       
       sb.append("|");
       for(String z:Z){
           sb.append(z);
       }
      return sb.toString();
   }
}
