/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author andrei
 */
public class Lab3 {

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
        i=1;
        /*
        Scanner input=new Scanner(System.in);
        i=input.nextInt();
        */
        Scanner fr=new Scanner(files[i-1]);            
        String line;
        i=0;

        ArrayList<KripkeWorld> kworld=new ArrayList<>();
        Parser prs=new Parser();
        
        ArrayList<ExpressionNode> elist=new ArrayList<>();
        while(fr.hasNextLine()){
            line=fr.nextLine().trim();
            if (line.startsWith("R(")){
                KripkeWorld.parseRelation(line, kworld);
            }else if (line.matches("^(W[0-9]+):.*")){
                KripkeWorld k=new KripkeWorld();
                k.parseLine(line);
                kworld.add(k);
            }else{
                ExpressionNode expr=prs.parse(line);
                System.out.println("------> "+ExpressionHelper.printExpression(expr));
                
                System.out.println("-----------> "+ExpressionHelper.printExpression(
                            ExpressionHelper.normalizeNot(expr, false)
                        )
                );
                elist.add(expr);
            }
            
        }
        for(ExpressionNode expr:elist){
            KripkeWorld kw= kworld.get(0);
                System.out.println(String.format("In Kripkeworld %s, Expression %s evaluates %s", 
                        kw.name,
                        ExpressionHelper.printExpression(expr),
                        ExpressionHelper.evalInWorld( ExpressionHelper.normalizeNot(expr, false),kw)
                        ));
            
        }
    }
    
}
