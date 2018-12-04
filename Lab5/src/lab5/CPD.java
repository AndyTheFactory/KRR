/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

/**
 *
 * @author andrei
 */
public class CPD {
    String vars;
    double[] prob;
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
    public void setProbability(String vars, String values,double probability){
        
    }
}
