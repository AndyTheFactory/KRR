/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab6;
import java.util.ArrayList;
import sun.security.krb5.internal.Krb5;
/**
 *
 * @author Dell
 */
public class BNode {
    ArrayList<BNode> Parents;
    ArrayList<BNode> Children;
    String name;
    Boolean isObserved;
    
    public BNode(String name){
        this.name=name;
        isObserved=false;
        Parents=new ArrayList<>();
        Children=new ArrayList<>();
    }
}
