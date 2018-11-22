/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
        
        ArrayList<CliqueGraph> myCliqueGraph=Lab5Helper.MorlaizeGraph(myGraph);
        System.out.println(myCliqueGraph);
        
        
    }
    
}
