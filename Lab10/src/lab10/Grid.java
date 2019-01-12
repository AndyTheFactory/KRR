/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab10;

import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class Grid {
    GridCell [][]cells;
    double pt,po;
    static int NRCOLORS=4;
    
    public Grid(int [] elevations, int[] colors){
        int nr=0;
        pt=0.8;
        po=0.8;
        cells=new GridCell[4][4];
        for (int i=0;i<4;i++)
            for(int j=0;j<4;j++)
            {
                cells[i][j]=new GridCell(elevations[nr],colors[nr]);
                cells[i][j].setPosition(i, j);
                nr++;
            }
        
    }
    public ArrayList<State> getNeighbours(State s){        
        return getNeighbours(s.cell.x,s.cell.y);
    }
    public ArrayList<State> getNeighbours(int x, int y){        
        ArrayList<State> res=new ArrayList<>();
        int N,Nmax=0,Hmax=0;
        N=cells.length*cells[0].length;
        
        for(int i=x-1;i<=x+1;i++)
            for(int j=y-1;j<=y+1;j++)
                if (i!=x && j!=y){
                    if (i>=0 && j<=0 && i<cells.length && j<cells[0].length){
                        State s=new State(cells[i][j]);                        
                        Hmax=Math.max(s.cell.elevation,Hmax);
                        res.add(s);
                    }
                }
        for(int i=0;i<res.size();i++){
            if (res.get(i).cell.elevation>=Hmax)
                Nmax++;
        }    
        
        for(int i=0;i<res.size();i++){
            if (res.get(i).cell.elevation>=Hmax)
                res.get(i).Probability=(pt/Nmax)+(1-pt)/N;
            else
                res.get(i).Probability=(1-pt)/N;
        }    
        return res;
    }
    public ArrayList<ColorState> getColors(State s){
        return getColors(s.cell.x, s.cell.y);
    }        
    public ArrayList<ColorState> getColors(int x, int y){        
        ArrayList<ColorState> res=new ArrayList<>();
        for(int i=0;i<=NRCOLORS-1;i++){
            ColorState cs=new ColorState(i,cells[x][y]);
            cs.Probability=(cells[x][y].color==i)?(
                        po+(1-po/NRCOLORS)
                    ):(
                        (1-po)/NRCOLORS
                    );
                
            res.add(cs);
            
        }
        return res;
    }
    public double getProbability(State si, State sj){
        if (!si.cell.isNeighbour(sj.cell)) 
            return 0;
        ArrayList<State> neighbours=getNeighbours(si);
        double prob=0;
        for(State s:neighbours)
            if (s.equals(sj)) 
                prob=sj.Probability;
        return prob;        
    }
    public double forwardAlg(int[]x,int[]y){
        double pi0=1/(cells.length*cells[0].length);
        return 0;
    }
    double[][] getProbabilityMatrix()
    {
        int nrcells=cells.length*cells[0].length;
        double[][] res=new double[nrcells][nrcells];
        for(int i=0;i<nrcells;i++)
            for(int j=0;j<nrcells;j++){
                res[i][j]=getProbability(
                    new State(i % cells.length, (int)(i/cells.length)),
                    new State(j % cells.length, (int)(j/cells.length))
                );
            }
        return res;
    }
    double getEmissionProbability(State si,ColorState c)
    {
        if (si.cell.color==c.color){
            c.Probability=po+(1-po)/NRCOLORS;
        }else{
            c.Probability=(1-po)/NRCOLORS;
        }
        return c.Probability;
    }
    double[][][] getEmissionProbabilityMatrix()
    {
        double[][][] res=new double[cells.length][cells[0].length][NRCOLORS];
        for(int i=0;i<cells.length;i++)
            for(int j=0;j<cells[0].length;j++)
                for(int c=0;c<NRCOLORS;c++)
                {
                    res[i][j][c]=getEmissionProbability(
                        new State(i ,j),
                        new ColorState(c)
                    );
                }
        return res;
    }
}
