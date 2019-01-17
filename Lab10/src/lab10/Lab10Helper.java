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
    static void saveAToFile(double[][] A,String filename) throws IOException{
        
        PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
        printWriter.print("\t");
        for(int i=0;i<A.length;i++)
            printWriter.print(String.format("  S(%d)  \t",i));       
        printWriter.print("\r\n");
        
        DecimalFormat formatter=new DecimalFormat("0.#####E0");
        for(int i=0;i<A[0].length;i++){
            printWriter.print(String.format("S(%d)\t",i));
            for(int j=0;j<A.length;j++){
                printWriter.print(A[j][i]);
                printWriter.print("\t");
            }
            printWriter.print("\r\n");
        }
        printWriter.close();
        
    }    
    static void saveBToFile(double[][] B,String filename) throws IOException{
        
        PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
        printWriter.print("\t");
        for(int i=0;i<B.length;i++)
            printWriter.print(String.format("  t(%d)  \t",i));       
        printWriter.print("\r\n");
        
        DecimalFormat formatter=new DecimalFormat("0.#####E0");
        for(int i=0;i<B[0].length;i++){
            printWriter.print(String.format("S(%d)\t",i));
            for(int j=0;j<B.length;j++){
                printWriter.print(B[j][i]);
                printWriter.print("\t");
            }
            printWriter.print("\r\n");
        }
        printWriter.close();
        
    }    
   
    static void baumWelch(int[] observations) throws IOException
    {
        int N=Grid.H*Grid.W;
        int M=Grid.NRCOLORS;
        int T=observations.length;

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
        
        double oldP=0;
        double logP=0;
        double[][] alpha=new double[T][N];
        double[][] beta=new double[T][N];
        double[] Scale=new double[T];

        logP=forward_backward(observations, PI, A, B, alpha, beta,Scale);
        
        for(int iterations=0;iterations<50;iterations++){
        //while (Math.abs(logP-oldP)<EPSILON){
            oldP=logP;
            
            update_pi(PI,alpha,beta,Scale);
            
            update_a(observations,A,B,alpha,beta,Scale);
            
            update_b(observations,B,alpha,beta,Scale);


            logP=forward_backward(observations, PI, A, B, alpha, beta,Scale);
            
        }
        
        
        saveAToFile(A, "estimated_A.txt");
        saveBToFile(B, "estimated_B.txt");
        
            
    }
    static void update_pi(double[] PI, double[][] alpha, double[][] beta, double[] Scale )
    {
        double numitor=0;
        
        for(int i=0;i<PI.length;i++){
           numitor+=alpha[1][i]*beta[1][i]/Scale[1]; 
        }
        for(int i=0;i<PI.length;i++){
            PI[i]=alpha[1][i]*beta[1][i]/Scale[1];
            PI[i]=divide(PI[i],numitor);
        }
        
    }
    static void update_a(int[] O, double[][] A, double[][]B, double[][] alpha, double[][] beta, double[] Scale )
    {
        for(int i=0;i<A.length;i++)
            for(int j=0;j<A.length;j++){
                double numarator=0,numitor=0;
                for (int t=0;t<O.length-1;t++){
                    numarator+=alpha[t][i]*A[i][j]*B[j][O[t+1]]*beta[t+1][j];
                    
                    for(int j0=0;j0<A.length;j0++)
                        numitor+=alpha[t][i]*A[i][j]*B[j0][O[t+1]]*beta[t+1][j0];
                }
                A[i][j]=divide(numitor,numarator);
                    
            }
        
    }
    
    static void update_b(int[] O,double[][] B, double[][] alpha, double[][] beta, double[] Scale )
    {
        for(int j=0;j<B.length;j++)
            for(int k=0;k<B[0].length;k++){
                double numarator=0,numitor=0;
                for (int t=0;t<O.length-1;t++){ 
                    if (O[t]==k)
                        numarator+=alpha[t][j]*beta[t][j]/Scale[t];                    
                    
                    numitor+=alpha[t][j]*beta[t][j]/Scale[t];
                }
                B[j][k]=divide(numitor,numarator);
                    
            }
        
    }
    static double forward_backward(int[] O, double[] PI, double[][] A, double[][] B, double[][] alpha, double[][] beta,double[]Scale){
        double P=0;
        //forward
        
        for(int i=0;i<PI.length;i++){
            alpha[0][i]=PI[i]*B[i][O[0]];
        }
        for(int t=1;t<O.length;t++)
            for(int i=0;i<PI.length;i++){
                alpha[t][i]=0;
                for(int j=0;j<PI.length;j++){
                    alpha[t][i]+= alpha[t-1][j]*A[i][j];
                }
                alpha[t][i]*=B[i][O[t]];
            }
        
        //backward
        int T=O.length;
        for(int i=0;i<PI.length;i++){
            beta[T-1][i]=1;
        }
        
        for(int t=T-2;t>=0;t--)
            for(int i=0;i<PI.length;i++){
                beta[t][i]=0;
                for(int j=0;j<PI.length;j++){
                    beta[t][i]+= beta[t+1][j]*A[i][j]*B[j][O[t+1]];
                }
            }
        for(int t=0;t<O.length;t++){
            Scale[t]=0;        
            for(int i=0;i<PI.length;i++)
                Scale[t]+=alpha[t][i];
        }
        
        
        
        return P;
    }
  static double divide(double n,double m){
      if (m==0) return 0;
      else return n/m;
  }
}
