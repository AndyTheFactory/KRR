/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;

import java.util.ArrayList;

/**
 *
 * @author andrei
 */
public class TableauPrinter
{
    static String printTableau(TableauNode t)
    {
        StringBuilder sb=new StringBuilder();
        int levels=TableauPrinter.countLevels(t);
        int width=3*(int)Math.pow(2, levels);
        int i;
    
        for(i=1;i<=levels;i++){
            sb.append(TableauPrinter.printLevel(width, i, t));
            sb.append('\n');
        }
        
        return sb.toString();
    }
    static String printLevel(int width,int level,TableauNode t)
    {
        StringBuilder line=new StringBuilder(new String(new char[width+3]).replace('\0',' '));
        int spaces=(int)width/ ((int)Math.pow(2, level-1)+1);
        ArrayList<TableauNode> tlist=TableauPrinter.getLevel(level, t);
        int pos=0;
        for(TableauNode tnode:tlist){
            pos+=spaces;
            switch(tnode.getType()){
                case TableauNode.BRANCH_NODE:
                    line.setCharAt(pos, '/');
                    line.setCharAt(pos+1, '\\');
                    break;
                case TableauNode.TRUNK_NODE:
                    line.setCharAt(pos, '|');
                    break;
                case TableauNode.VARIABLE_NODE:
                    VariableTableauNode tt=(VariableTableauNode)tnode;
                    if (tt.not) 
                        line.setCharAt(pos-1, '¬');
                    line.setCharAt(pos, tt.var.charAt(0));
                    break;
            }
        }
        return line.toString();
        
    }
    static ArrayList<TableauNode> getLevel(int level, TableauNode t)
    {
        ArrayList<TableauNode> tn=new ArrayList<TableauNode>();
        if (level==1){
            tn.add(t);
        }else{
            switch(t.getType()){
                case TableauNode.BRANCH_NODE:{
                    BranchTableauNode tnode=(BranchTableauNode) t;
                    tn.addAll(TableauPrinter.getLevel(level-1, tnode.left));
                    tn.addAll(TableauPrinter.getLevel(level-1, tnode.right));
                    break;
                }
                case TableauNode.TRUNK_NODE:{
                    TrunkTableauNode tnode=(TrunkTableauNode) t;
                    tn.addAll(TableauPrinter.getLevel(level-1, tnode.next));
                    break;
                }
                case TableauNode.VARIABLE_NODE:{
                    VariableTableauNode tnode=(VariableTableauNode)t;
                    if (tnode.next!=null )
                        tn.addAll(TableauPrinter.getLevel(level-1, tnode.next));
                    break;
                }
            }
            
        }
        return tn;
        
    }
    static int countLevels(TableauNode t)
    {
        if (t==null) return 0;
        
        if (t.getType()==TableauNode.VARIABLE_NODE){
            VariableTableauNode tt=(VariableTableauNode)t;
            return 1+TableauPrinter.countLevels(tt.next);
        }
        if (t.getType()==TableauNode.TRUNK_NODE){
            TrunkTableauNode tt=(TrunkTableauNode)t;
            return 1+TableauPrinter.countLevels(tt.next);
        }
        if (t.getType()==TableauNode.BRANCH_NODE){
            BranchTableauNode tt=(BranchTableauNode)t;
            return 1+Math.max(
                        TableauPrinter.countLevels(tt.left),
                        TableauPrinter.countLevels(tt.right)
                    );
        }
        
        throw new ParserException(String.format("unknown Tableau Node type %d", t.getType()));
    }
}
