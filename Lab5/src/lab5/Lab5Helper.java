/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.util.ArrayList;
import java.util.Arrays;

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
    public static WorkGraph findNodeWork(String name,ArrayList<WorkGraph> CGraph)
    {
        for(WorkGraph CNode:CGraph)
            if (CNode.name.equals(name.trim().toUpperCase())){
                return CNode;
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
    public static ArrayList<WorkGraph> MorlaizeGraph(ArrayList<BayesGraph> BGraph)
    {
        ArrayList<WorkGraph> workGraph=new ArrayList<>();
        
        for(BayesGraph BNode:BGraph){
            WorkGraph C=findNodeWork(BNode.name, workGraph);
                        
            if (C==null){
                C=new WorkGraph();
                C.name=BNode.name;
                workGraph.add(C);
            }
            for(BayesGraph PNode:BNode.parents){
                WorkGraph CC=findNodeWork(PNode.name, workGraph);                
                if(CC==null){
                    CC=new WorkGraph();
                    CC.name=PNode.name;
                    workGraph.add(CC);
                }
                if (findNodeWork(C.name, CC.edgeTo)==null){
                    CC.edgeTo.add(C);
                    C.edgeTo.add(CC);
                }
            }
            //Edgerize
            for(BayesGraph PNode1:BNode.parents){
                for(BayesGraph PNode2:BNode.parents){
                    if (!PNode1.name.equals(PNode2.name)){
                        WorkGraph PCNode1=findNodeWork(PNode1.name, workGraph);
                        WorkGraph PCNode2=findNodeWork(PNode2.name, workGraph);
                        if (findNodeWork(PCNode1.name,PCNode2.edgeTo)==null){
                            PCNode1.edgeTo.add(PCNode2);
                            PCNode2.edgeTo.add(PCNode1);
                        }
                    }
                }
            }
            for(BayesGraph PNode:BNode.children){
                WorkGraph CC=findNodeWork(PNode.name, workGraph);                
                if(CC==null){
                    CC=new WorkGraph();
                    CC.name=PNode.name;
                    workGraph.add(CC);
                }
                if (findNodeWork(C.name, CC.edgeTo)==null){
                    CC.edgeTo.add(C);
                    C.edgeTo.add(CC);
                }
            }
            
        }
        return workGraph;
    }
    static void Triangulate(ArrayList<WorkGraph> wGraph){
        ArrayList<WorkGraph> tempGraph=new ArrayList<>();
        ArrayList<WorkEdge> newEdges=new ArrayList<>();
        
        for(WorkGraph GNode:wGraph){
            WorkGraph tempNode=new WorkGraph();
            tempNode.name=GNode.name;
            tempGraph.add(tempNode);
        }
        do{
            countMyEdges(tempGraph,wGraph);
            WorkGraph minNode=findMinfill(tempGraph);
            tempGraph.remove(minNode);
            newEdges.addAll(minNode.edgesToAdd);
            
        }while(tempGraph.size()>0);
        
        for(WorkEdge E:newEdges){
            WorkGraph PNode1=findNodeWork(E.P1.name, wGraph);
            WorkGraph PNode2=findNodeWork(E.P2.name, wGraph);
            
            PNode1.edgeTo.add(PNode2);
            PNode2.edgeTo.add(PNode1);
        }
        
    }
    static void countMyEdges(ArrayList<WorkGraph> tempGraph,ArrayList<WorkGraph> wGraph){
        
        for(WorkGraph PNode:tempGraph){
            String addedEdges="";
            PNode.nrEdgesToAdd=0;
            PNode.edgesToAdd.clear();
             
            WorkGraph origNode=findNodeWork(PNode.name, wGraph); //caut in graphul orig
            for(WorkGraph NNode:origNode.edgeTo){
                if (findNodeWork(NNode.name, tempGraph)!=null) //nu l-am eliminat
                {
                    for(WorkGraph OtherNNode:origNode.edgeTo){
                        if (
                                !PNode.name.equals(OtherNNode.name) && //not same node
                                !NNode.name.equals(OtherNNode.name) && //not same node
                                (findNodeWork(OtherNNode.name, NNode.edgeTo)==null) && //is not connected to
                                (findNodeWork(OtherNNode.name, tempGraph)!=null) &&//is still of interest
                                (!addedEdges.contains("$"+OtherNNode.name+"#"+NNode.name+"$")) //do not cout twice
                                )
                        { 
                            PNode.nrEdgesToAdd++;
                            WorkEdge myEdge=new WorkEdge();
                            myEdge.P1=new WorkGraph();
                            myEdge.P2=new WorkGraph();
                            myEdge.P1.name=NNode.name;
                            myEdge.P2.name=OtherNNode.name;
                            
                            PNode.edgesToAdd.add(myEdge);
                            addedEdges+="$"+OtherNNode.name+"#"+NNode.name+"$"+
                                        "$"+NNode.name+"#"+OtherNNode.name+"$";
                            
                        }
                        
                    }
                }
            }
        }
        
    }
    static WorkGraph findMinfill(ArrayList<WorkGraph> tempGraph){
        if (tempGraph.size()<=0) return null;
        
        WorkGraph minNode=tempGraph.get(0);
        for(WorkGraph NNode:tempGraph){
            
            if (minNode.nrEdgesToAdd>NNode.nrEdgesToAdd){
                minNode=NNode;
                
            }
        }
        return minNode;
        
    }
 }
