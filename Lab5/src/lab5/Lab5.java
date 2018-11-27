/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author andrei
 */
public class Lab5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException{
        // TODO code application logic here
        File setupfile=new File("./bnet.txt");
        
        BayesGraphParser parsefile=new BayesGraphParser();
        
        ArrayList<BayesGraph> myGraph=parsefile.parseFile(setupfile);
        
        ArrayList<WorkGraph> myWorkGraph=Lab5Helper.MorlaizeGraph(myGraph);
        
        Lab5Helper.Triangulate(myWorkGraph);
        
        System.out.println(myWorkGraph);
        ArrayList<Set<WorkGraph>> Cliques=new ArrayList<Set<WorkGraph>>();
        ArrayList<WorkGraph> R=new ArrayList<>();
        ArrayList<WorkGraph> X=new ArrayList<>();
        
        
        Lab5Helper.BronKerbosch(R, myWorkGraph, X, Cliques);
        int i=0;
        for(Set<WorkGraph> S:Cliques){
            i++;
            System.out.println("Clique "+i+":");
            for(WorkGraph N:S)
            {
                System.out.print(N.name+",");
            }
            System.out.println("");
        }
        ArrayList<CliqueGraph> Graph=Lab5Helper.constructCliqueGraph(Cliques);
        CliqueGraph Tree=Lab5Helper.getCliqueTree(Graph);
        
        System.out.println(Tree.printTree());
        
    }
    
}
