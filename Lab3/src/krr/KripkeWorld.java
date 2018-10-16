/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author andrei
 */
public class KripkeWorld {
    public String name;
    public ArrayList<KripkeVariable> variables;
    public ArrayList<KripkeWorld> relations;
    
    public  KripkeWorld()
    {
        this.variables=new ArrayList<>();
        this.relations=new ArrayList<>();
    }
    
    public void parseLine(String line)
    {
        Pattern p=Pattern.compile("^(W[0-9]+):(.+)");
        
        Matcher m=p.matcher(line);
        
        if (!m.find()) throw new ParserException(String.format("Line %s malformed", line));
        
        this.name=m.group(1);
        
        String v=m.group(2);
        
        for(String var:v.split(",")){
            var=var.trim();
            KripkeVariable kvar=new KripkeVariable();
            if (var.charAt(0)=='~'){
               kvar.name=var.substring(1);
               kvar.not=true;
            }else{
               kvar.name=var;
               kvar.not=false;
                
            }
            this.variables.add(kvar);
        }
        
    }
    static public void parseRelation(String line,ArrayList<KripkeWorld> graph)
    {
        Pattern p=Pattern.compile("^R\\((W[0-9]+),(W[0-9]+)\\)");
       
        Matcher m=p.matcher(line);
        
        if (!m.find()) throw new ParserException(String.format("Relation line %s malformed", line));
        
        String W1=m.group(1),W2=m.group(2);
        
        KripkeWorld k1=null,k2=null;
 
        for(KripkeWorld world:graph){
            if (world.name.equalsIgnoreCase(W1)) k1=world;
            if (world.name.equalsIgnoreCase(W2)) k2=world;
            
            if (k1!=null && k2!=null ) break;
            
        }
        
        if (k1==null || k2==null ) throw new ParserException(String.format("Relation line %s contains unknown world", line));
        
        k1.relations.add(k2);
        
    }
}
