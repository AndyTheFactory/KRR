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
public class CliqueGraph {
    Set<WorkGraph> nodes;
    ArrayList<CliqueGraph> edgeTo;
    
    public CliqueGraph(Set<WorkGraph> clique){
        nodes=new HashSet<>(clique);
        edgeTo=new ArrayList<>();
    }
    public CliqueGraph(){
        nodes=new HashSet<>();
        edgeTo=new ArrayList<>();
    }
    public WorkGraph findNode(String name){
        for(WorkGraph N:nodes)
            if (N.name.equals(name))
                return N;
        return null;
    }
    public int edgeCardinality(CliqueGraph NeighbourNode){
        int res=0;
        if (edgeTo.contains(NeighbourNode)){
            for(WorkGraph N:nodes){
                if (NeighbourNode.findNode(N.name)!=null)
                    res++;
            }
        }
        
        return res;            
        
    }
    
    public ArrayList<String> edgeLabel(CliqueGraph NeighbourNode){
        ArrayList<String> res=new ArrayList<>();
        if (edgeTo.contains(NeighbourNode)){
            for(WorkGraph N:nodes){
                if (NeighbourNode.findNode(N.name)!=null)
                    res.add(N.name);
            }
        }
        return res;
    }
    public ArrayList<String> intersect(CliqueGraph Node){
        ArrayList<String> res=new ArrayList<>();
            for(WorkGraph N:nodes){
                if (Node.findNode(N.name)!=null)
                    res.add(N.name);
            }
        return res;
    }
    public boolean isEqual(CliqueGraph Node){
        if (this.nodes.size()!=Node.nodes.size())
            return false;
        for(WorkGraph N:this.nodes)
            if (Node.findNode(N.name)==null)
                return false;
        return true;
    }
    public boolean findWay(CliqueGraph Node){
        return findWay(Node,new ArrayList<>());
    }
    public boolean findWay(CliqueGraph Node,ArrayList<CliqueGraph> X){
        for(CliqueGraph N:Node.edgeTo){
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
    public String printTree()
    {
        return printTree(new ArrayList<CliqueGraph>());
    }
    public String printTree(ArrayList<CliqueGraph> alreadyNodes){
        StringBuilder sb=new StringBuilder();
        sb.append(this);
        
        for(CliqueGraph N:edgeTo){
            if (alreadyNodes.contains(N))
                continue;
            alreadyNodes.add(N);
            sb.append(N.printTree(alreadyNodes));
        }
        return sb.toString();
    }
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("Node: ");
        for(WorkGraph N:nodes)
            sb.append(N.name+", ");
        sb.append("\n");
        
        return sb.toString();
    }
    
}
