/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author andrei
 */
public class JunctionTree {
    Set<String> nodes;
    ArrayList<JunctionTree> parents;
    ArrayList<JunctionTree> children;
    
    public JunctionTree()
    {
        nodes=new HashSet<>();
        parents=new ArrayList<>();
        children=new ArrayList<>();
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
    
    
    
}
