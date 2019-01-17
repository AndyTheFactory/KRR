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
    public GridCell(int x,int y,int e,int c){
        this.x=x;
        this.y=y;
        this.color=c;
        this.elevation=e;
    }
    public GridCell(int e,int c){
        this.color=c;
        this.elevation=e;
    }
    public void setPosition(int x,int y){
        this.x=x;this.y=y;
    }
    public boolean isNeighbour(int x1,int y1){
        return (Math.abs(x-x1)<=1)&&(Math.abs(y-y1)<=1)&&((x1!=x)||(y1!=y));
    }
    public boolean isNeighbour(GridCell cell){
        return isNeighbour(cell.x,cell.y);
    }
    public boolean isEqual(GridCell cell){
        return cell.x==x && cell.y==y;
    }
}
