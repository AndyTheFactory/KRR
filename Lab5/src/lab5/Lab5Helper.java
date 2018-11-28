/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

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
    
    static void BronKerbosch(ArrayList<WorkGraph> R, ArrayList<WorkGraph> P , ArrayList<WorkGraph> X, ArrayList<Set<WorkGraph>> Cliques)
    {
        if (P.isEmpty() && X.isEmpty()){
            Cliques.add(new HashSet<WorkGraph>(R));
        }else{
            ArrayList<WorkGraph> candidates=new ArrayList<WorkGraph>(P);
            for(WorkGraph v:candidates){
                ArrayList<WorkGraph> new_P=new ArrayList<>();
                ArrayList<WorkGraph> new_X=new ArrayList<>();
                
                for(WorkGraph N:v.edgeTo){//Add neighbours in candidates
                    if (findNodeWork(N.name, P)!=null){
                        new_P.add(N);
                    }
                    if (findNodeWork(N.name, X)!=null){
                        new_X.add(N);
                    }
                }
                R.add(v);
                BronKerbosch(R, new_P, new_X, Cliques);
                R.remove(v);
                P.remove(v);
                X.add(v);
            }
        }
        
    }
    
    static ArrayList<CliqueGraph> constructCliqueGraph(ArrayList<Set<WorkGraph>> Cliques){
        ArrayList<CliqueGraph> Graph=new ArrayList<CliqueGraph>();
        
        for(Set<WorkGraph> S:Cliques){
            CliqueGraph Node=new CliqueGraph(S);
            for(CliqueGraph Node2:Graph){
                if (!Node.intersect(Node2).isEmpty()){
                    Node.edgeTo.add(Node2);
                    Node2.edgeTo.add(Node);
                }
            }
            Graph.add(Node);
        }
        return Graph;
    }
    static ArrayList<CliqueEdge> getSortedEdges(ArrayList<CliqueGraph> Graph){
        ArrayList<CliqueEdge> edges=new ArrayList<CliqueEdge>();
        ArrayList<CliqueGraph> doneNodes=new ArrayList<CliqueGraph>();
        for (CliqueGraph Node:Graph)
            for(CliqueGraph Node2:Node.edgeTo)
                if(!doneNodes.contains(Node2)){
                    CliqueEdge e=new CliqueEdge(Node,Node2);
                    edges.add(e);
                    doneNodes.add(Node2);
                }
        edges.sort(new Comparator<CliqueEdge>(){
                @Override
                public int compare(CliqueEdge E1,CliqueEdge E2){
                    return E1.weight>E2.weight?-1 : (E1.weight==E2.weight?0:1);
                }
            }
        );
        return edges;
    }
    
    static CliqueGraph getCliqueTree(ArrayList<CliqueGraph> Graph){
        ArrayList<CliqueEdge> edges=getSortedEdges(Graph);
        ArrayList<CliqueGraph> tree=new ArrayList<CliqueGraph>();
        if (edges.isEmpty()) 
            return null;
        do{
            CliqueEdge E=edges.get(0);
            edges.remove(E);
            
            CliqueGraph Node1=findInTree(E.N1, tree),
                    Node2=findInTree(E.N2, tree);
            
            boolean hasCycle=true;
            if (Node1==null){
               Node1=new CliqueGraph(E.N1.nodes);
               hasCycle=false;
            }
            if (Node2==null){
               Node2=new CliqueGraph(E.N2.nodes);
               hasCycle=false;
            }

            if(hasCycle && !Node1.findWay(Node2)){
                hasCycle=false;
            }
                
            if (!hasCycle){
                if (!tree.contains(Node1))
                    tree.add(Node1);
                if (tree.get(0).nodes.size()<Node1.nodes.size()){
                    Collections.swap(tree, 0, tree.indexOf(Node1));
                }
                if (!tree.contains(Node2))
                    tree.add(Node2);
                if (tree.get(0).nodes.size()<Node2.nodes.size()){
                    Collections.swap(tree, 0, tree.indexOf(Node2));
                }
                Node1.edgeTo.add(Node2);
                Node2.edgeTo.add(Node1);
            }
            
        }while(!edges.isEmpty() && tree.size()<Graph.size());
        
       
        return tree.get(0);
        
    }
    public static JunctionTree convertCliqueTree2Junction(CliqueGraph CliqueTree){
        return convertCliqueTree2Junction(CliqueTree, new ArrayList<CliqueGraph>());
    }
    private static JunctionTree convertCliqueTree2Junction(CliqueGraph CliqueTree,ArrayList<CliqueGraph> alreadyNodes){
        alreadyNodes.add(CliqueTree);
        JunctionTree tree=new JunctionTree(CliqueTree);
        for(CliqueGraph N:CliqueTree.edgeTo)
            if (!alreadyNodes.contains(N))
            {
                JunctionTree child=convertCliqueTree2Junction(N,alreadyNodes);
                tree.children.add(child);
                child.parents.add(tree);
            }
        return tree;
    }
    private static CliqueGraph findInTree(CliqueGraph needle,ArrayList<CliqueGraph> Tree)
    {
        for(CliqueGraph N:Tree)
            if(N.isEqual(needle))
                return N;
        return null;
    }
 }
