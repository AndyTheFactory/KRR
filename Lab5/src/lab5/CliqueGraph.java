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
public class CliqueGraph
{
    String name;
    ArrayList<CliqueGraph> edgeTo;
    public CliqueGraph()
    {
        edgeTo=new ArrayList<>();
    }
    public String toString(){
        StringBuilder sb=new StringBuilder();
        
        sb.append("\nNode: "+name+"\n");
        sb.append(">>> connectedTo: ");
        
        for(CliqueGraph BNode:edgeTo)
            sb.append(BNode.name+" ");
        sb.append("\n");
        sb.append("----------------");
        return sb.toString();
    }
    
}
