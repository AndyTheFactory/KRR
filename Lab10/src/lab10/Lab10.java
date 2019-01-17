/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab10;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Dell
 */
public class Lab10 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Grid g1,g2,g3;
        g1=new Grid(new int[]{1, 2, 3, 5, 2, 2, 1, 2,3, 2, 1, 1,0, 0, 0, 0}, 
                    new int[]{0, 3, 1, 2, 3, 1, 2, 0,2, 2, 0, 0, 3, 0, 3, 1});
        g2=new Grid(new int[]{0, 0, 1, 1, 2, 1, 0, 2, 1, 0, 0, 2, 4, 4, 3, 3}, 
                    new int[]{0, 3, 1, 2, 3, 1, 2, 0, 2, 2, 0, 0, 3, 0, 3, 1});
        g3=new Grid(new int[]{2, 1, 2, 3, 1, 1, 2, 2, 1, 0, 1, 1, 2, 1, 1, 2}, 
                    new int[]{2, 3, 1, 0, 1, 3, 3, 1, 0, 2, 0, 2, 2, 1, 1, 2});
        System.out.println("0=black,1=red,2=green, 3=blue");
        int[] testi=new int[]{0, 1, 2, 5, 10, 13, 15};
        int[] testj=new int[]{1, 0, 3, 4, 9, 2, 14};
        
        Lab10Helper.saveTransitionGridToFile(g1, "probability_G1.txt");
        Lab10Helper.saveTransitionGridToFile(g2, "probability_G2.txt");
        Lab10Helper.saveTransitionGridToFile(g3, "probability_G3.txt");

        Lab10Helper.saveTransitionEmissionsToFile(g1, "emissions_G1.txt");
        Lab10Helper.saveTransitionEmissionsToFile(g2, "emissions_G2.txt");
        Lab10Helper.saveTransitionEmissionsToFile(g3, "emissions_G3.txt");
        
        ArrayList<ColorState> seq=g1.getSequence(20);
        
        System.out.println("Random sequence: ");
        Lab10Helper.printSequence(seq);
        
        int[] observations=new int[]{0,1,3,0,3,2,1,0,3};
        double pg1=g1.getForwardAlgorithm(observations);
        double pg2=g2.getForwardAlgorithm(observations);
        double pg3=g3.getForwardAlgorithm(observations);
        
        System.out.println("probability for Sequence of Observations on G1="+pg1+", G2="+pg2+", G3="+pg3);
        if (pg1>=pg2 && pg1>=pg3)
            System.out.println(" ===> most probable G1");
        if (pg2>=pg1 && pg2>=pg3)
            System.out.println(" ===> most probable G2");
        if (pg3>=pg1 && pg3>=pg2)
            System.out.println(" ===> most probable G3");
            
        ArrayList<State> path=g1.getViterbi(new int[]{0,0,1,3});
        System.out.println("Viterbi Path:");
        for(State s:path){
            System.out.print(String.format("(%d,%d),", s.cell.x,s.cell.y));
        }
        System.out.println("");
        int[] obs=new int[seq.size()];
        for(int i=0;i<obs.length;i++)
            obs[i]=seq.get(i).color;
            
        Lab10Helper.baumWelch(obs);
        
    }
    
}
