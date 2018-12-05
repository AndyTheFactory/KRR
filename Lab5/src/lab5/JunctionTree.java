/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author andrei
 */
public class JunctionTree {
    Set<String> nodes;
    ArrayList<JunctionTree> parents;
    ArrayList<JunctionTree> children;
    int nrmessages;
    double m_up;
    double m_down;
    Map<String,CPD> factori;
    
    public JunctionTree()
    {
        nodes=new HashSet<>();
        parents=new ArrayList<>();
        children=new ArrayList<>();
        factori=new HashMap<>();
        nrmessages=0;
    }
    public JunctionTree(CliqueGraph Node)
    {
        this();        
        for(WorkGraph N:Node.nodes){
            nodes.add(N.name);
        }
    }
    
    public boolean findWay(JunctionTree Node){
        return findWay(Node,new ArrayList<>());
    }
    public boolean findWay(JunctionTree Node,ArrayList<JunctionTree> X){
        ArrayList<JunctionTree> candidates=new ArrayList<>(Node.children);
        candidates.addAll(Node.parents);
        for(JunctionTree N:candidates){
            if (X.contains(N))
                continue;            
            if (Node.isEqual(N)) 
                return true;
            X.add(N);
            if (N.findWay(Node,X))
                return true;
        }
        return false;
    }
    public boolean isEqual(JunctionTree Node){
        if (this.nodes.size()!=Node.nodes.size())
            return false;
        for(String N:this.nodes)
            if (Node.nodes.contains(N))
                return false;
        return true;
    }
    public ArrayList<JunctionTree> getLeaves()
    {
        ArrayList<JunctionTree> res=new ArrayList<JunctionTree>();
        if (children.size()<=0){
            res.add(this);
            return res;
        }
        
        for(JunctionTree J:this.children){
            res.addAll(J.getLeaves());
        }
        return res;
    }
    public boolean containsVars(String vars){
        for(char c:vars.toCharArray())
            if (!nodes.contains(Character.toString(c)))
                return false;
        return true;
    }
    
    
}
