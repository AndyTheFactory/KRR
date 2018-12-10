/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import java.util.ArrayList;

/**
 *
 * @author andrei
 */
public class WorkGraph
{
    String name;
    ArrayList<WorkGraph> edgeTo;
    int nrEdgesToAdd;
    ArrayList<WorkEdge> edgesToAdd;
    
    public WorkGraph()
    {
        edgeTo=new ArrayList<>();
        edgesToAdd=new ArrayList<>();
    }
    public String toString(){
        StringBuilder sb=new StringBuilder();
        
        sb.append("\nNode: "+name+"\n");
        sb.append(">>> connectedTo: ");
        
        for(WorkGraph BNode:edgeTo)
            sb.append(BNode.name+" ");
        sb.append("\n");
        sb.append("----------------");
        return sb.toString();
    }
    
}
