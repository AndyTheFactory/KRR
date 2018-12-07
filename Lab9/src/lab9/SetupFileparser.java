/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author andrei
 */
abstract class SetupFileparser<T>
{
    ArrayList<T> result;
    public SetupFileparser(){
        result=new ArrayList<>();
    }
            
    public abstract ArrayList<T> parseFile(File file) throws FileNotFoundException;
    public abstract T parseLine(String line);
    
    public ArrayList<T> parseLines(File file) throws FileNotFoundException{
        Scanner fr=new Scanner(file);            
        result.clear();
        
        String line;
         while(fr.hasNextLine()){
            line=fr.nextLine().trim();
            result.add(this.parseLine(line));
         }
        return result;
    }
    
}
