/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab10;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        g1=new Grid(new int[]{1, 2, 3, 5,2, 2, 1, 2,3, 2, 1, 1,0, 0, 0, 0}, 
                    new int[]{0, 3, 1, 2, 3, 1, 2, 0,2, 2, 0, 0, 3, 0, 3, 1});
        g2=new Grid(new int[]{0, 0, 1, 1, 2, 1, 0, 2, 1, 0, 0, 2, 4, 4, 3, 3}, 
                    new int[]{0, 3, 1, 2, 3, 1, 2, 0, 2, 2, 0, 0, 3, 0, 3, 1});
        g3=new Grid(new int[]{2, 1, 2, 3, 1, 1, 2, 2, 1, 0, 1, 1, 2, 1, 1, 2}, 
                    new int[]{2, 3, 1, 0, 1, 3, 3, 1, 0, 2, 0, 2, 2, 1, 1, 2});
        
        int[] testi=new int[]{0, 1, 2, 5, 10, 13, 15};
        int[] testj=new int[]{1, 0, 3, 4, 9, 2, 14};
        
        double[][] prob=g1.getProbabilityMatrix();

        PrintWriter printWriter = new PrintWriter(new FileWriter("probability.txt"));
        for(int i=0;i<prob.length;i++){
            for(int j=0;j<prob[i].length;j++){
                printWriter.print(prob[i][j]);
                printWriter.print("\t");
            }
            printWriter.print("\r\n");
        }
        printWriter.close();
        
    }
    
}
