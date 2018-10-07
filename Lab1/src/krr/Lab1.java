/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author andrei
 */
public class Lab1 {

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
        
        Scanner input=new Scanner(System.in);
        i=input.nextInt();
        
        Scanner fr=new Scanner(files[i-1]);            
        String line;
        i=0;
        Tokenizer tk=Tokenizer.getExpressionTokenizer();        
        while(fr.hasNextLine()){
            line=fr.nextLine();
            System.out.println((++i)+". "+line);
            tk.tokenize(line);
            System.out.println("------> \n"+tk);
            
        }
        
    }
    
}
