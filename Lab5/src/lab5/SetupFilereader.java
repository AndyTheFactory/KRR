/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author andrei
 */
public class SetupFilereader
{
    public static File ChooseFile(){
        return ChooseFile("txt");
    }
    
    public static File ChooseFile(String fileExt){
        String currentPath=Paths.get(".").toAbsolutePath().normalize().toString();
        System.out.printf("Looking for Parameter Files in folder %s\n",currentPath);
        File[] files=new File(currentPath).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith("."+fileExt);
            }
        });
        int i=0;
        for (File file:files){
            System.out.println((++i)+". "+file);
        }
        System.out.println("\nPlease choose parameter file: ");
        Scanner input=new Scanner(System.in);
        i=input.nextInt();
        return files[i-1];
    }
}
