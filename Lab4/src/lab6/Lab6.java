/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab6;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Dell
 */
public class Lab6 {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws Exception {
        String currentPath=Paths.get(".").toAbsolutePath().normalize().toString();
        System.out.printf("Looking for Parameter Files in folder %s\n",currentPath);
        File[] files=new File(currentPath).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });
        int i=0;
        for (File file:files){
            System.out.println((++i)+". "+file);
        }
        System.out.println("\nPlease choose parameter file: ");
       // Scanner input=new Scanner(System.in);
       // i=input.nextInt();
i=1;

        Scanner fr=new Scanner(files[i-1]);            
        String line=fr.nextLine();

        String[] sspl=line.split(" ");
        int n=Integer.parseInt(sspl[0]);
        int m=Integer.parseInt(sspl[1]);
        //int n=5;int m=8;
        ArrayList<BNode> graph=new ArrayList<>();
        
        for(i=0;i<n;i++){
            line=fr.nextLine().trim();
            Lab6Helper.addLineToGraph(graph, line);
        }
        
        ArrayList<Expresie> expresii=new ArrayList<>();
        
        for(i=0;i<m;i++){
            line=fr.nextLine().trim();
            Lab6Helper.addLineToExpressions(expresii, line);
        }
        
        System.out.println(expresii);
        for(Expresie ex: expresii){
            Lab6Helper.setObservable(graph,ex.Z);
            boolean B=false;
            for(String x:ex.X){
                for(String y:ex.Y){
                    BNode bx=Lab6Helper.findNode(graph, x);
                    BNode by=Lab6Helper.findNode(graph, y);
                    ArrayList<BNode> path=new ArrayList<>();
                    B=Lab6Helper.findPath(path, bx, by);
                    if (B) break;
                }
                if (B) break;
            }
            System.out.println(ex+" eval to "+(!B));
        }
        
    }
 
}
