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
    public static CliqueGraph findNodeClique(String name,ArrayList<CliqueGraph> CGraph)
    {
        for(CliqueGraph CNode:CGraph)
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
    public static ArrayList<CliqueGraph> MorlaizeGraph(ArrayList<BayesGraph> BGraph)
    {
        ArrayList<CliqueGraph> clique=new ArrayList<>();
        
        for(BayesGraph BNode:BGraph){
            CliqueGraph C=findNodeClique(BNode.name, clique);
                        
            if (C==null){
                C=new CliqueGraph();
                C.name=BNode.name;
                clique.add(C);
            }
            for(BayesGraph PNode:BNode.parents){
                CliqueGraph CC=findNodeClique(PNode.name, clique);                
                if(CC==null){
                    CC=new CliqueGraph();
                    CC.name=PNode.name;
                    clique.add(CC);
                }
                if (findNodeClique(C.name, CC.edgeTo)==null){
                    CC.edgeTo.add(C);
                    C.edgeTo.add(CC);
                }
            }
            //Edgerize
            for(BayesGraph PNode1:BNode.parents){
                for(BayesGraph PNode2:BNode.parents){
                    if (!PNode1.name.equals(PNode2.name)){
                        CliqueGraph PCNode1=findNodeClique(PNode1.name, clique);
                        CliqueGraph PCNode2=findNodeClique(PNode2.name, clique);
                        if (findNodeClique(PCNode1.name,PCNode2.edgeTo)==null){
                            PCNode1.edgeTo.add(PCNode2);
                            PCNode2.edgeTo.add(PCNode1);
                        }
                    }
                }
            }
            for(BayesGraph PNode:BNode.children){
                CliqueGraph CC=findNodeClique(PNode.name, clique);                
                if(CC==null){
                    CC=new CliqueGraph();
                    CC.name=PNode.name;
                    clique.add(CC);
                }
                if (findNodeClique(C.name, CC.edgeTo)==null){
                    CC.edgeTo.add(C);
                    C.edgeTo.add(CC);
                }
            }
            
        }
        return clique;
    }
}
