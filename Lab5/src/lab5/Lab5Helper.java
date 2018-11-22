/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.util.ArrayList;

/**
 *
 * @author andrei
 */
public class Lab5Helper
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
    public static ArrayList<CliqueGraph> MorlaizeGraph(ArrayList<BayesGraph> BGraph)
    {
        
    }
}
