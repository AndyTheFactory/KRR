/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab10;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author andrei
 */
public class Lab10Helper {
    static double EPSILON=0.00001;
    static void saveTransitionGridToFile(Grid g,String filename) throws IOException{
        double[][] prob=g.getProbabilityMatrix();

        PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
        printWriter.print("\t");
        for(int i=0;i<prob.length;i++)
            printWriter.print(String.format("To %d\t",i));
        printWriter.print("\r\n");
        for(int i=0;i<prob.length;i++){
            printWriter.print(String.format("From %d\t",i));
            for(int j=0;j<prob[i].length;j++){
                printWriter.print(String.format("%.2f",prob[i][j]));
                printWriter.print("\t");
            }
            printWriter.print("\r\n");
        }
        printWriter.close();
        
    }
    static void saveTransitionEmissionsToFile(Grid g,String filename) throws IOException{
        double[][][] prob=g.getEmissionProbabilityMatrix();

        PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
        printWriter.print("\t");
        for(int i=0;i<prob.length;i++)
            printWriter.print(String.format("  	  X= %d     \t",i));
        printWriter.print("\r\n");
        for(int i=0;i<prob.length;i++){
            printWriter.print(String.format("Y= %d\t",i));
            for(int j=0;j<prob[i].length;j++){
                for (int c=0;c<Grid.NRCOLORS;c++)
                    printWriter.print(String.format("%.2f ",prob[i][j][c]));
                printWriter.print("\t");
            }
            printWriter.print("\r\n");
        }
        printWriter.close();
        
    }    
    static void printSequence(ArrayList<ColorState> sequence){
        System.out.println("Starting Sequence > ");
        for(ColorState c:sequence){
            System.out.println(String.format("(%d,%d) --> Color %d / RealColor=%d",c.cell.x,c.cell.y,c.color,c.cell.color));
        }
    }
    static void saveDeltasToFile(double[][] deltas,String filename) throws IOException{
        
        PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
        printWriter.print("\t");
        for(int i=0;i<deltas.length;i++)
            printWriter.print(String.format("  t(%d)  \t",i));       
        printWriter.print("\r\n");
        
        DecimalFormat formatter=new DecimalFormat("0.#####E0");
        for(int i=0;i<deltas[0].length;i++){
            printWriter.print(String.format("S(%d)\t",i));
            for(int j=0;j<deltas.length;j++){
                printWriter.print(formatter.format(deltas[j][i]));
                printWriter.print("\t");
            }
            printWriter.print("\r\n");
        }
        printWriter.close();
        
    }    
   
    static void baumWelch(int[] observations)
    {
        int N=Grid.H*Grid.W;
        int M=Grid.NRCOLORS;

        double[] PI=new double[N];
        double[][] A=new double[N][N];
        double[][] B=new double[N][M];
        
        

        for(int i=0;i<N;i++)
        {
            PI[i]=1/(double)N;
            double kk=1;
            for (int j=0;j<N;j++){
                A[i][j]=Math.random()*kk;
                kk-=A[i][j];
            }
            A[i][N-1]=kk;
            
            for(int k=0;k<M;k++){
                B[i][k]=1/(double)M;
            }
        }
            
    }
}
