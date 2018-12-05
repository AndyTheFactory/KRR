/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author andrei
 */
public class BayesGraph
{
    ArrayList<BayesGraph> parents;
    ArrayList<BayesGraph> children;
    String name;
    //Map<String,Double> CPD;
    CPD cpd;
    public String toString(){
        StringBuilder sb=new StringBuilder();
        
        sb.append("\nNode: "+name+"\n");
        sb.append(">>> Parents: ");
        
        for(BayesGraph BNode:parents)
            sb.append(BNode.name+" ");
        sb.append("\n");
        
        sb.append(">>> Children: ");
        
        for(BayesGraph BNode:children)
            sb.append(BNode.name+" ");
        sb.append("\n");
        sb.append("----------------");
        return sb.toString();
    }
}
