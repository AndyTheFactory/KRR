/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab10;

/**
 *
 * @author Dell
 */
public class State {
    GridCell cell;
    double Probability;
    public State(int x, int y){
        cell=new GridCell();
    }
    public State(GridCell cell){
        this.cell=cell;
    }
}
