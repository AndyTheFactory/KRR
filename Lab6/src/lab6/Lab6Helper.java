/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab6;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Dell
 */
public class Lab6Helper {
    static void addLineToGraph(ArrayList<BNode> graph,String line){
        String[] sp=line.split(" ");
        BNode node = new BNode(sp[0]);
        for(int i=1;i<sp.length;i++){
            BNode par=findOrCreateParent(graph,sp[i]);
            node.Parents.add(par);
            par.Children.add(node);
        }
        graph.add(node);
        
        
        
    }
    static BNode findOrCreateParent(ArrayList<BNode> graph,String name){
        
        BNode res=null;
        for(BNode nn:graph)
            if (nn.name.equals(name)){
                res=nn;
                break;
            }
        if (res==null){
            res=new BNode(name);
        }
        return res;
    }
    static void addLineToExpressions(ArrayList<Expresie> expr, String line){
        //Pattern p=Pattern.compile("^([a-zA-Z\\s]+);([a-zA-Z\\s]+)\\|([a-zA-Z\\s]+)");  
        //Matcher ma = p.matcher(line);
        
        String[] sspl=line.split(";");
        String[] sspl2=sspl[1].split("/");
        Expresie e= new Expresie();
        
        for(String x:sspl[0].split("\\s"))
            if (!x.isEmpty())
                e.X.add(x.trim());
        for(String y:sspl2[0].split("\\s"))
            if(!y.isEmpty())
                e.Y.add(y.trim());
        if (sspl2.length>1)
            for(String z:sspl2[1].split("\\s"))
                if (!z.isEmpty())
                    e.Z.add(z.trim());
        
        expr.add(e);
        
    }
    static void setObservable(ArrayList<BNode> graph, ArrayList<String> Z){
        int i=0;
        for(BNode b:graph){
            
            b.isObserved=false;
            for(String name:Z){
                if (b.name.equals(name)){
                    b.isObserved=true;
                }
            }
            graph.set(i, b);i++;
        }
    }
    static BNode findNode(ArrayList<BNode> graph,String name){
        BNode res=null;
        for(BNode b:graph){
            if (b.name.equals(name)) res=b;
        }
        return res;
    }
    static boolean isInList(String name,ArrayList<BNode> graph){
        boolean res=false;
        for(BNode b:graph){
            if (b.name.equals(name)){
                res=true;
                break;
            }
        }
        return res;
    }
    static boolean inStrList(String A,ArrayList<String> AL){
        for(String N:AL){
            if (N.equals(A)) return true;
        }
        return false;
    }
    static boolean findPath(ArrayList<BNode> graph,BNode x, BNode y){
        boolean res=false;
        
        if (x.Parents.size()>0)
            for(BNode n:x.Parents){
                if (!n.isObserved){
                    if (isInList(y.name, n.Parents)) {
                        res=true;
                        return res;
                    }
                    if (isInList(y.name, n.Children)) {
                        res=true;
                        return res;
                    }
                    res=findPath(graph, n, y);
                }
            }
        if (res) return res; 
        if (x.Children.size()>0)
            for(BNode n:x.Children){
                if (!n.isObserved){
                    if (isInList(y.name, n.Parents)) {
                        res=true;
                        return res;
                    }
                    if (isInList(y.name, n.Children)) {
                        res=true;
                        return res;
                    }
                    res=findPath(graph, n, y);
                }
            }
         return res;      
    }
}
