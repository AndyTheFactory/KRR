/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class Lab9 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        BayesGraphParser parsefile=new BayesGraphParser();
        parsefile.readSampleFile(new File("./samples_bn1"));
        
        File setupfile=new File("./bnet.txt");
        ArrayList<BayesGraph> myGraph=parsefile.parseFile(setupfile);
        
        
        for(BayesGraph BNode:myGraph){
            Lab9Helper.CPDbySample(BNode.cpd, parsefile.vars, parsefile.sampleData);
        }
        System.out.println(myGraph);
    }
    
}
