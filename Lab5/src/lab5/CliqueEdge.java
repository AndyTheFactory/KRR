/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5;

import java.util.ArrayList;

/**
 *
 * @author andrei
 */
public class CliqueEdge {
    CliqueGraph N1,N2;
    int weight;
    ArrayList<String> label;
    public CliqueEdge(CliqueGraph Node1,CliqueGraph Node2){
        N1=Node1;
        N2=Node2;
        weight=Node1.edgeCardinality(Node2);
        label=Node1.edgeLabel(Node2);
    }
}
