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

        Parser prs=new Parser();
        ArrayList<TableauNode> statementlist=new ArrayList<TableauNode>();
        ExpressionNode lastExpr=null;
        TableauNode lastTab=null;
        while(fr.hasNextLine()){
            line=fr.nextLine();
            System.out.println((++i)+". "+line);
            ExpressionNode expr=prs.parse(line);
            System.out.println("------> "+ExpressionHelper.printExpression(expr));
            System.out.println("----------> "+ExpressionHelper.printExpression(ExpressionHelper.normalizeNot(expr,false)));
            TableauNode tab=ExpressionHelper.Expression2Tableau(ExpressionHelper.normalizeNot(expr,false));
            System.out.println(TableauPrinter.printTableau(tab));
            statementlist.add(tab);
            lastExpr=expr;
            lastTab=tab;
        }
        statementlist.remove(lastTab);//remove last
        TableauNode cond=ExpressionHelper.Expression2Tableau(ExpressionHelper.normalizeNot(lastExpr,true));
        System.out.println(" ======= \n");
        System.out.println(ExpressionHelper.printExpression(lastExpr)+"\n");
        
        if (!ExpressionHelper.isSatisfied(statementlist, cond))
            System.out.println(" is satisfied \n");
        else
            System.out.println(" is NOOOT satisfied \n");
    }
    
}
