package krr;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andrei
 */
public class Lab3_3 {
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
        i=3;
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
            ExpressionNode expr=prs.parse(line);
            System.out.println("------> "+ExpressionHelper.printExpression(expr));

            System.out.println("-----------> "+ExpressionHelper.printExpression(
                        ExpressionHelper.normalizeNot(expr, false)
                    )
            );
            elist.add(expr);
            
        }
    }
    
    
}
