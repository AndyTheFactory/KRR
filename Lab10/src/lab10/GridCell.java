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
public class GridCell {
    int elevation;
    int color;
    int x,y;
    public GridCell(int e,int c){
        this.color=c;
        this.elevation=e;
    }
    public void setPosition(int x,int y){
        this.x=x;this.y=y;
    }
}
