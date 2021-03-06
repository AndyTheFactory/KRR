/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author andrei
 */
public class BayesGraphParser extends SetupFileparser<BayesGraph>
{
            
    /**
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    int[][] sampleData;
    String vars;
    @Override
    public  ArrayList<BayesGraph> parseFile(File file) throws FileNotFoundException{
        return parseLines(file);
    }
    public BayesGraph parseLine(String line){
        String[] spl=line.trim().split(";");
        BayesGraph B;
        
        try{
            B=Lab9Helper.findNode(spl[0], result);
            if (B==null) {
                B=new BayesGraph();
                B.name=spl[0].trim().toUpperCase();
                B.children=new ArrayList<BayesGraph>();
            }
            B.parents=readParents(spl[1]);
            B.cpd=new CPD();
            B.cpd.readFromDef(line);
            //set children
            for(BayesGraph BNode:B.parents){
                if (Lab9Helper.findNode(B.name, BNode.children)==null){
                    BNode.children.add(B);
                }
            }
            
        }catch(Exception e){
            System.out.println("Ceva nu e bine la linia "+line);
            throw e;
        }
        return B;
    }
    private ArrayList<BayesGraph> readParents(String line)
    {
        ArrayList<BayesGraph> BParent=new ArrayList<>();
        for(String P:line.trim().split(" "))
            if (!P.isEmpty())
            {
                BayesGraph BNode=Lab9Helper.findNode(P, result);
                if (BNode==null){
                    BNode=new BayesGraph();
                    BNode.name=P.trim().toUpperCase();
                    result.add(BNode);
                }
                BParent.add(BNode);
            }
        return BParent;
    }
    private Map<String,Double> readCPD(String parents, String values)
    {
        Map<String,Double> res=new HashMap<String, Double>();
        if(parents.trim().isEmpty()){
            res.put("me", Double.valueOf(values));
            return res;
        }
        String[] splparent=parents.trim().split(" ");
        String[] splvalues=values.trim().split(" ");
        for(int i=0;i<splvalues.length;i++ ){
            try{
                // return something like ABC=010
                String bb="000000000000000000000"+Integer.toBinaryString(i);

                res.put(String.format("%s=%s",Lab9Helper.joinVars(splparent),bb.substring(bb.length()-splparent.length)), Double.valueOf(splvalues[i]));
            }catch(Exception e){
                System.out.println(String.format("Ceva nu e bine la linia parents=%s , values=%v",parents,values));
                throw e;

            }
        }
        return res;
    }
    public void readSampleFile(File file) throws FileNotFoundException{
        Scanner fr=new Scanner(file);            
        result.clear();
        
        String line;
        line=fr.nextLine().trim();
        vars=line.replace(" ", "");
        int nrvars=line.split(" ").length;
        
        int nrsamples=0;
        while(fr.hasNextLine()){
            fr.nextLine();
            nrsamples++;
         }
        sampleData=new int[nrvars][nrsamples+1];
        fr=new Scanner(file);            
        line=fr.nextLine().trim();
        int i=0;
        while(fr.hasNextLine()){
            line=fr.nextLine().trim();
            int j=0;
            for(String s:line.split(" ")){
                sampleData[j][i]=Integer.parseInt(s);
                j++;
            }
            i++;
         }
        
    }

}
