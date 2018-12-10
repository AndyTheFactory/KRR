/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

/**
 *
 * @author andrei
 */
public class CPD {
    String vars; //P(A|B,C)-->ABC
    double[] prob;
    public CPD(){
        vars="";
        prob=null;
    }
    public CPD(String vars){
        this.vars=vars;
        prob=new double[(int)Math.pow(2, vars.length())];
        
    }
    public CPD(String vars, double[] probs){
        this(vars);
        int i=0;
        for(double v:probs){
            this.prob[i]=v;
            i++;
        }
    }
    public void readFromDef(String def){
        StringBuilder sb=new StringBuilder();
        sb.append(def.substring(0,def.indexOf(';')-1).trim());
        def=def.substring(def.indexOf(';')+1);
        sb.append(def.substring(0,def.indexOf(';')-1).replace(" ","").trim());
        vars=sb.toString();

        prob=new double[(int)Math.pow(2, vars.length())];
        
        def=def.substring(def.indexOf(';')+1).trim();
        int i=0;
        for(String p:def.split(" ")){
            prob[i]=1-Double.valueOf(p); //P(A=0|B,C)
            prob[i|(1<<(vars.length()-1))]=Double.valueOf(p); //P(A=0|B,C)
            i++;
        }
        
        
        
    }
    public String toString()
    {
        if (vars.length()<=0) return "";
        return "P("+vars.charAt(0)+"|"+vars.substring(1)+")";
    }
}
