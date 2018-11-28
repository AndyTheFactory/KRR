/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import org.apache.commons.lang3.StringUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringJoiner;

/**
 *
 * @author Dell
 */
public class GraphVisualization {
    public static void writeJSON(String filename, CliqueGraph Tree) throws IOException{
        ArrayList<CliqueGraph> graph=treeWalk(Tree);
        writeJSON(filename, graph,true);
    }
    private static ArrayList<CliqueGraph> treeWalk(CliqueGraph Tree){
        ArrayList<CliqueGraph> res=new ArrayList<>();
        res.add(Tree);
        for(CliqueGraph Node:Tree.edgeTo){
            res.addAll(treeWalk(Node));
        }
        return res;
    }
    public static void writeJSON(String filename, ArrayList<CliqueGraph> Graph) throws IOException{
        writeJSON(filename, Graph,false);
    }
    public static void writeJSON(String filename, ArrayList<CliqueGraph> Graph,Boolean firstIsRoot) throws IOException{
        BufferedWriter file=new BufferedWriter(new FileWriter(filename));
        StringBuilder sb=new StringBuilder("{\n\"comment\":\"Bla\",\n\"nodes\":");
        ArrayList<EdgeClass> edges=new ArrayList<>();
        
        StringJoiner sj=new StringJoiner(",","[\n","\n]");
        int i=0;
        for(CliqueGraph Node:Graph){
            i++;
            sj.add(
                 String.format("    {      \"id\": %d,\n" +
                                "      \"caption\": \"%s\",\n" +
                                "      \"root\": %s\n    }\n"
                    ,Node.hashCode(),Node.toString(),(firstIsRoot&&i==1)?"true":"false"
                 )
            );
            for(CliqueGraph N:Node.edgeTo){
                EdgeClass edge=new EdgeClass();
                edge.from=Node.hashCode();
                edge.to=N.hashCode();
                edge.caption=StringUtils.join(Node.intersect(N),",");
                edges.add(edge);
            }
            
        }
        sb.append(sj.toString());
        sb.append("\n,\n\"edges\":");
        sj=new StringJoiner(",","[\n","\n]");
        for(EdgeClass edge:edges){
            sj.add(edge.toString());
        }
        sb.append(sj.toString());
        sb.append("\n}\n");
        
        file.write(sb.toString());
        file.close();
    }
    static private boolean isEdge(CliqueGraph Node1,CliqueGraph Node2, ArrayList<EdgeClass> edges){
        for(EdgeClass edge:edges){
            if((edge.from==Node1.hashCode() && edge.to==Node2.hashCode())||
                   (edge.from==Node2.hashCode() && edge.to==Node1.hashCode()))
                    return true;
        }
        return false;
    }
}
class EdgeClass{
    int from,to;
    String caption;
    public String toString()
    {
        return String.format("    {\n" +
            "      \"source\": %d,\n" +
            "      \"target\": %d,\n" +
            "      \"caption\": \"%s\"\n" +
            "    }\n",from,to,caption);
    }
}