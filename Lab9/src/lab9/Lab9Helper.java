/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author andrei
 */
public class Lab9Helper
{
    public static BayesGraph findNode(String name,ArrayList<BayesGraph> BGraph)
    {
        for(BayesGraph BNode:BGraph)
            if (BNode.name.equals(name.trim().toUpperCase())){
                return BNode;
            }
        return null;
    }
    public static String joinVars(String[] vars)
    {
        String res="";
        for(String s:vars){
            res+=s;
        }
        return res;
    }
    public static  void CPDbySample(CPD cpd,String varnames,int[][] sampleData){
        for(int i=0;i<cpd.prob.length;i++){
             String bb="000000000000000000000"+Integer.toBinaryString(i);
             bb=bb.substring(bb.length()-cpd.vars.length());
             double sum_up=0,sum_d=0;
             for(int j=0;j<sampleData[0].length;j++){
                 int xu=1,xd=1;
                 for(int vn=0;vn<cpd.vars.length();vn++){
                     int k=varnames.indexOf(cpd.vars.substring(vn,vn+1));
                     int vv=Integer.parseInt(bb.substring(vn,vn+1));
                     if (sampleData[k][j]!=vv){
                         xu=0;
                         if (vn>0) xd=0;
                     }
                 }
                 sum_up+=xu;
                if (cpd.vars.length()==1)
                    sum_d++;
                 else
                    sum_d+=xd;
                           
             }
             if (sum_d==0) sum_d=Integer.MAX_VALUE;
             cpd.prob[i]=(Double)sum_up/(Double)sum_d;
        }
       
    }

 }
