/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krr;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author andrei
 */
public class ExpressionHelper {
    static ArrayList<VariableTableauNode> vv;
    public static ExpressionNode normalizeNot(ExpressionNode expr, boolean negate)
    {
        
        if(negate){
            switch(expr.getType()){
                default:
                case ExpressionNode.VARIABLE_NODE:
                {
                    VariableExpressionNode v=(VariableExpressionNode) expr;
                    VariableExpressionNode newexpr=new VariableExpressionNode(v.name,v.value);
                    newexpr.negation=!v.negation;
                    return newexpr;
                }
                case ExpressionNode.OR_NODE:
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    AndExpressionNode newexpr=new AndExpressionNode(
                        ExpressionHelper.normalizeNot(n.lhs,true),
                        ExpressionHelper.normalizeNot(n.rhs,true)
                    );
                    return newexpr;
                }
                case ExpressionNode.AND_NODE:
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    OrExpressionNode newexpr=new OrExpressionNode(
                        ExpressionHelper.normalizeNot(n.lhs,true),
                        ExpressionHelper.normalizeNot(n.rhs,true)
                    );
                    return newexpr;
                }
                case ExpressionNode.IMPLIES_NODE:                
                case ExpressionNode.IFF_NODE:                
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    ExpressionNode newexpr=ExpressionHelper.normalizeNot(ExpressionHelper.normalizeNot(expr,false), true);
                    return newexpr;
                }
                case ExpressionNode.NOT_NODE:
                {
                    NotExpressionNode v=(NotExpressionNode) expr;
                    ExpressionNode newexpr=ExpressionHelper.normalizeNot(v.lhs,false);
                    return newexpr;
                }
                case ExpressionNode.BOX_NODE:
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    DiamondExpressionNode newexpr=new DiamondExpressionNode(ExpressionHelper.normalizeNot(n.lhs,true));
                    return newexpr;
                }
                case ExpressionNode.DIAMOND_NODE:
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    BoxExpressionNode newexpr=new BoxExpressionNode(ExpressionHelper.normalizeNot(n.lhs,true));
                    return newexpr;
                }
                


            }
        }else{
            switch(expr.getType()){
                default:
                case ExpressionNode.VARIABLE_NODE:
                {
                    VariableExpressionNode v=(VariableExpressionNode) expr;
                    VariableExpressionNode newexpr=new VariableExpressionNode(v.name,v.value);
                    newexpr.negation=v.negation;
                    return newexpr;
                }
                case ExpressionNode.OR_NODE:
                case ExpressionNode.AND_NODE:
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    n.lhs=ExpressionHelper.normalizeNot(n.lhs, false);
                    n.rhs=ExpressionHelper.normalizeNot(n.rhs, false);
                    if (expr.getType()==ExpressionNode.AND_NODE){
                        AndExpressionNode newexpr=new AndExpressionNode(n.lhs, n.rhs);
                        return newexpr;
                    }else{
                        OrExpressionNode newexpr=new OrExpressionNode(n.lhs, n.rhs);
                        return newexpr;
                    }
                }
                case ExpressionNode.IMPLIES_NODE:                
                {
                    ImpliesExpressionNode n=(ImpliesExpressionNode) expr;                    
                    OrExpressionNode newexpr=new OrExpressionNode(
                                ExpressionHelper.normalizeNot(n.lhs,true),
                                ExpressionHelper.normalizeNot(n.rhs,false)
                            );
                    return newexpr;
                }
                case ExpressionNode.IFF_NODE:                
                {
                    IffExpressionNode n=(IffExpressionNode) expr;                    
                    OrExpressionNode newexpr=new OrExpressionNode(
                                new AndExpressionNode(ExpressionHelper.normalizeNot(n.lhs,false), ExpressionHelper.normalizeNot(n.rhs,false)),
                                new AndExpressionNode(ExpressionHelper.normalizeNot(n.lhs,true), ExpressionHelper.normalizeNot(n.rhs,true))
                            );
                    return newexpr;
                }
                case ExpressionNode.NOT_NODE:
                {
                    NotExpressionNode v=(NotExpressionNode) expr;
                    ExpressionNode newexpr=ExpressionHelper.normalizeNot(v.lhs, true);
                    return newexpr;
                }
                case ExpressionNode.BOX_NODE:
                case ExpressionNode.DIAMOND_NODE:
                {
                    OperatorExpressionNode n=(OperatorExpressionNode) expr;
                    n.lhs=ExpressionHelper.normalizeNot(n.lhs, false);
                    if (expr.getType()==ExpressionNode.BOX_NODE){
                        BoxExpressionNode newexpr=new BoxExpressionNode(n.lhs);
                        return newexpr;
                    }else{
                        DiamondExpressionNode newexpr=new DiamondExpressionNode(n.lhs);
                        return newexpr;
                    }
                    
                }
                



            }
        }
    }
    public static String printExpression(ExpressionNode expr)
    {
        StringBuilder res=new StringBuilder();
        
        switch(expr.getType()){
            case ExpressionNode.VARIABLE_NODE:
            {
                VariableExpressionNode v=(VariableExpressionNode) expr;
                if (v.negation) res.append('¬');
                res.append(v.name+" ");
                break;
            }
            case ExpressionNode.OR_NODE:
            case ExpressionNode.AND_NODE:
            case ExpressionNode.IMPLIES_NODE:                
            case ExpressionNode.IFF_NODE:                
            {
                OperatorExpressionNode n=(OperatorExpressionNode) expr;
                res.append("("+ExpressionHelper.printExpression(n.lhs)+" ");
                res.append(n.toString()+" ");
                res.append(ExpressionHelper.printExpression(n.rhs)+") ");
                break;
            }
            case ExpressionNode.NOT_NODE:
            {
                NotExpressionNode v=(NotExpressionNode) expr;
                res.append('¬');
                res.append("("+ExpressionHelper.printExpression(v.lhs)+") ");
                break;
            }
            case ExpressionNode.BOX_NODE:
            {
                BoxExpressionNode v=(BoxExpressionNode) expr;
                res.append('□');
                res.append("("+ExpressionHelper.printExpression(v.lhs)+") ");
                break;
            }
            case ExpressionNode.DIAMOND_NODE:
            {
                DiamondExpressionNode v=(DiamondExpressionNode) expr;
                res.append('◊');
                res.append("("+ExpressionHelper.printExpression(v.lhs)+") ");
                break;
            }
               
            
        }
        return res.toString();
    }
    
    public static TableauNode Expression2Tableau(List<ExpressionNode> exprlist)
    {
        int i;
        for(i=0;i<exprlist.size();i++)
        {
        
            if (exprlist.get(i).getType()==ExpressionNode.AND_NODE){
                Collections.swap(exprlist,0,i); //start with an AND node (trunk)
                break;
            }
        }
        List<TableauNode> tableaus=new ArrayList<TableauNode>();
        for(ExpressionNode expr:exprlist){
            //take _not_ into nodes, expand _not_ in paranthesis
            ExpressionNode expr2=ExpressionHelper.normalizeNot(expr, false);
            TableauNode tab=ExpressionHelper.Expression2Tableau(expr2);
            tableaus.add(tab);
        }
        
        return tableaus.get(0);
    }
    public static TableauNode Expression2Tableau(ExpressionNode expr)
    {
        if (expr==null) return null;
        
        switch (expr.getType()) {
            case ExpressionNode.VARIABLE_NODE:{
                VariableExpressionNode n=(VariableExpressionNode)expr;
                VariableTableauNode tnode=new VariableTableauNode(n.name,n.negation);
                tnode.next=null;
                return tnode;
            }
            case ExpressionNode.AND_NODE:{
                AndExpressionNode n=(AndExpressionNode)expr;
                TrunkTableauNode tnode=new TrunkTableauNode();
                tnode.next=ExpressionHelper.Expression2Tableau(n.lhs);
                ExpressionHelper.AppendTableauToLeafs(ExpressionHelper.Expression2Tableau(n.rhs), tnode);
                return tnode;
                
            }
            case ExpressionNode.OR_NODE:{
                OrExpressionNode n=(OrExpressionNode)expr;
                BranchTableauNode tnode=new BranchTableauNode();
                tnode.left=ExpressionHelper.Expression2Tableau(n.lhs);
                tnode.right=ExpressionHelper.Expression2Tableau(n.rhs);
                return tnode;
                
            }
            default:
                throw new ParserException(String.format("Tableau creation Error. unexpected Node type %s", expr.toString()));
        }
    }
    static public void AppendTableauToLeafs(TableauNode tableauToAppend,TableauNode tableau){
        if (tableauToAppend==null) return;
        switch (tableau.getType()){
            case TableauNode.VARIABLE_NODE :
            {
                VariableTableauNode t=(VariableTableauNode) tableau;
                if (t.next==null){
                    t.next=ExpressionHelper.CloneTableau(tableauToAppend);
                    return;
                }else{
                    ExpressionHelper.AppendTableauToLeafs(tableauToAppend, t.next);
                }
                return;
            }
            case TableauNode.TRUNK_NODE:
            {
                TrunkTableauNode t=(TrunkTableauNode) tableau;
                ExpressionHelper.AppendTableauToLeafs(tableauToAppend, t.next);
                return;
            }
            case TableauNode.BRANCH_NODE:
            {
                BranchTableauNode t=(BranchTableauNode) tableau;
                ExpressionHelper.AppendTableauToLeafs(tableauToAppend, t.left);
                ExpressionHelper.AppendTableauToLeafs(tableauToAppend, t.right);
                return;
            }
            default:
                throw new ParserException(String.format("Tableau creation Error. unexpected Node type %s", tableau.toString()));
                
        }
    }
    static public TableauNode CloneTableau(TableauNode tableau){
        switch (tableau.getType()){
            case TableauNode.VARIABLE_NODE :
            {
                VariableTableauNode t=(VariableTableauNode) tableau;
                VariableTableauNode tnew=new VariableTableauNode(t.var,t.not);
                
                if (t.next!=null){
                    tnew.next=ExpressionHelper.CloneTableau(t.next);
                }
                return tnew;
            }
            case TableauNode.TRUNK_NODE:
            {
                TrunkTableauNode t=(TrunkTableauNode) tableau;
                TrunkTableauNode tnew=new TrunkTableauNode();
                tnew.next=ExpressionHelper.CloneTableau(t.next);
                return tnew;
            }
            case TableauNode.BRANCH_NODE:
            {
                BranchTableauNode t=(BranchTableauNode) tableau;
                BranchTableauNode tnew=new BranchTableauNode();
                tnew.left=ExpressionHelper.CloneTableau(t.left);
                tnew.right=ExpressionHelper.CloneTableau(t.right);
                return tnew;
            }
            default:
                throw new ParserException(String.format("Tableau creation Error. unexpected Node type %s", tableau.toString()));
                
        }
        
    }
    public static boolean isContradiction(ArrayList<VariableTableauNode> tlist)
    {
        ArrayList<String> vv=new ArrayList<String>();
        for(VariableTableauNode tnode:tlist){
            if ((tnode.not && vv.contains(tnode.var))||
                    !tnode.not&&vv.contains("!"+tnode.var)){
                return true;
            }else{
                vv.add(tnode.not?("!"+tnode.var):tnode.var);
            }
        }
        return false;
    }
    public static boolean isContradiction(TableauNode tableau)
    {
        if(vv==null){
            vv=new ArrayList<VariableTableauNode>();
        }
        switch (tableau.getType()){
            case TableauNode.VARIABLE_NODE :
            {
                VariableTableauNode t=(VariableTableauNode) tableau;
                vv.add(t);
                if (ExpressionHelper.isContradiction(vv)) return true;
                
                if (t.next==null) return false;
                
                boolean b= ExpressionHelper.isContradiction(t.next);
                vv.remove(t);
                return b;

            }
            case TableauNode.TRUNK_NODE:
            {
                TrunkTableauNode t=(TrunkTableauNode) tableau;
                return ExpressionHelper.isContradiction(t.next);
            }
            case TableauNode.BRANCH_NODE:
            {
                BranchTableauNode t=(BranchTableauNode) tableau;
                BranchTableauNode tnew=new BranchTableauNode();
                
                return ExpressionHelper.isContradiction(t.left)&
                        ExpressionHelper.isContradiction(t.right);
            }
            default:
                throw new ParserException(String.format("Tableau creation Error. unexpected Node type %s", tableau.toString()));
                
        }
        
    }
    
    public static boolean isSatisfied(ArrayList<TableauNode> tableaus,TableauNode statement)
    {
        int i,j;
        if (tableaus.size()<=0) return !ExpressionHelper.isContradiction(statement);
           
        TableauNode ttest=ExpressionHelper.CloneTableau(tableaus.get(0));
        
        for(i=1;i<tableaus.size();i++){
            ExpressionHelper.AppendTableauToLeafs(tableaus.get(i), ttest);
            if (ExpressionHelper.isContradiction(ttest)) return false;
            
        }
        ExpressionHelper.AppendTableauToLeafs(statement, ttest);
        return !ExpressionHelper.isContradiction(ttest);
            
    }
    public static boolean evalInWorld(ExpressionNode expr, KripkeWorld kw){        
        boolean res=false;
        switch (expr.getType()){
            case ExpressionNode.BOX_NODE:{
                res=kw.relations.size()>0;
                BoxExpressionNode n=(BoxExpressionNode)expr;
                for(KripkeWorld k:kw.relations){           
                    res=res & ExpressionHelper.evalInWorld(n.lhs, k);
                }
                break;
            }
            case ExpressionNode.DIAMOND_NODE:{
                BoxExpressionNode n=(BoxExpressionNode)expr;
                for(KripkeWorld k:kw.relations){           
                    res=res | ExpressionHelper.evalInWorld(n.lhs, k);
                }
                break;
                
            }
            default:{
                TableauNode tab=ExpressionHelper.Expression2Tableau(ExpressionHelper.normalizeNot(expr,false));
                ArrayList<TableauNode> t= new ArrayList<TableauNode>();
                t.add(tab);
                res=true;
                for(KripkeVariable kv:kw.variables){
                    VariableExpressionNode v=new VariableExpressionNode(kv.name);
                    v.negation=kv.not; //isSatisfied checks contrdiction
                    VariableTableauNode vt=(VariableTableauNode)ExpressionHelper.Expression2Tableau(v);
                    if (vv!=null) vv.clear();
                    res&=ExpressionHelper.isSatisfied(t,vt);
                    
                }
                break;
            }
                
            
        }
        return res;
    }
}

