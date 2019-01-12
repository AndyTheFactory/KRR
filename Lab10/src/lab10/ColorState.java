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
public class ColorState {
    int color;
    GridCell cell;
    double Probability;
    public ColorState(int color){
        this.color=color;
    }
    public ColorState(int color,GridCell cell){
        this.color=color;
        this.cell=cell;
    }
}
