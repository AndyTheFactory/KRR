/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Dell
 */
public class Grid {
    GridCell [][]cells;
    double pt,po;
    static int NRCOLORS=4;
    static int H=4;
    static int W=4;
    
    public Grid(int [] elevations, int[] colors){
        int nr=0;
        pt=0.8;
        po=0.8;
        cells=new GridCell[W][H];
        for (int i=0;i<W;i++)
            for(int j=0;j<H;j++)
            {
                cells[i][j]=new GridCell(elevations[nr],colors[nr]);
                cells[i][j].setPosition(i, j);
                nr++;
            }
        
    }
    public int getNRStates(){
        return (cells.length>0)?cells.length*cells[0].length:0;
    }
    public GridCell stateNrToCell(int nr){
        return cells[nr % W][(int)(nr/W)];
    }   
    public int cellToStateNr(GridCell cell){
        return cell.x + cell.y*W;
    }
    public ArrayList<State> getNeighbours(State s){        
        return getNeighbours(s.cell.x,s.cell.y);
    }
    public ArrayList<State> getNeighbours(int x, int y){        
        ArrayList<State> res=new ArrayList<>();
        int N,Nmax=0,Hmax=0;
        
        for(int i=x-1;i<=x+1;i++)
            for(int j=y-1;j<=y+1;j++)
                if ((i!=x || j!=y)&&(i==x || j==y)){
                    if (i>=0 && j>=0 && i<cells.length && j<cells[0].length){
                        State s=new State(cells[i][j]);                        
                        Hmax=Math.max(s.cell.elevation,Hmax);
                        res.add(s);
                    }
                }
        N=res.size();
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
            if (s.isEqual(sj)) 
                prob=s.Probability;
        return prob;        
    }
    double[][] getProbabilityMatrix()
    {
        int nrcells=getNRStates();
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
                        new State(cells[i][j]),
                        new ColorState(c)
                    );
                }
        return res;
    }
    GridCell getRandCell()
    {
        
        int x=(int)(Math.random()*cells.length);
        int y=(int)(Math.random()*cells[0].length);
        return cells[x][y];
    }
    int getObservation(int x, int y){
        double cumulativeProb=0;
        double sample=Math.random();
        for(int c=0;c<NRCOLORS;c++){
            cumulativeProb+=getEmissionProbability(new State(cells[x][y]), new ColorState(c));
            if (sample<cumulativeProb) return c;
        }
        
        return NRCOLORS;
    }
    GridCell getNextCell(GridCell cell){
        double cumulativeProb=0;
        double sample=Math.random();
        ArrayList<State> neighbours=getNeighbours(cell.x, cell.y);
        
        for(int i=0;i<neighbours.size();i++){
            cumulativeProb+=getProbability(new State(cell), neighbours.get(i));
            if (sample<cumulativeProb) return neighbours.get(i).cell;
        }
        
        return neighbours.get(neighbours.size()-1).cell;
    }
    public ArrayList<ColorState> getSequence(int length){
        GridCell start=this.getRandCell();
        ArrayList<ColorState> res=new ArrayList<>();
        res.add(new ColorState(
                this.getObservation(start.x,start.y),start
        ));
        for(int i=1;i<length;i++){
            start=this.getNextCell(start);
            res.add(new ColorState(
                    this.getObservation(start.x,start.y),start
            ));            
        }
        return res;
    }
    
    double[][] getAlphas(int[] observations){
        int nrobs=observations.length;

        double[][] alpha=new double[nrobs][getNRStates()];
        double pi0=1/(double)getNRStates();
        
        for(int i=0;i<getNRStates();i++)
            alpha[0][i]=pi0*getEmissionProbability(new State(stateNrToCell(i)), new ColorState(observations[0]));
        
        for(int t=1;t<nrobs;t++)
            for(int i=0;i<getNRStates();i++){
                double pt=0;
                for(int j=0;j<getNRStates();j++){
                    pt+=alpha[t-1][j]*getProbability(new State(stateNrToCell(j)), new State(stateNrToCell(i)));
                }
                alpha[t][i]=pt*getEmissionProbability(new State(stateNrToCell(i)), new ColorState(observations[t]));
            }
        return alpha;
    }
    double getForwardAlgorithm(int[] observations){
        int nrobs=observations.length;
        double[][] alphas=getAlphas(observations);
        double prob=0;
        for(int i=0;i<getNRStates();i++)
            prob+=alphas[nrobs-1][i];
        return prob;
    }
    ArrayList<State> getViterbi(int[] observations) throws IOException{
        int nrobs=observations.length;
        double[][] deltas=new double[nrobs][getNRStates()];
        int[][] backlinks=new int[nrobs][getNRStates()];
        ArrayList<State> path=new ArrayList<>();

        double pi0=1/(double)getNRStates();
        
        for(int i=0;i<getNRStates();i++)
            deltas[0][i]=pi0*getEmissionProbability(new State(stateNrToCell(i)), new ColorState(observations[0]));
        
        for(int t=1;t<nrobs;t++){
            for(int i=0;i<getNRStates();i++){
                double bestprob=0;
                int beststate=-1;
                for(int j=0;j<getNRStates();j++){
                    double prob=deltas[t-1][j]*getProbability(new State(stateNrToCell(j)), new State(stateNrToCell(i)));
                    if (bestprob<prob){
                        bestprob=prob;
                        beststate=j;
                    }
                }
                deltas[t][i]=bestprob*getEmissionProbability(new State(stateNrToCell(i)), new ColorState(observations[0]));
                backlinks[t][i]=beststate;
            }
        }
        double lastprob=0;
        int laststatenr=-1;
        State laststate=null;
        
        for(int i=0;i<getNRStates();i++){
            if(lastprob<deltas[nrobs-1][i])
            {
                lastprob=deltas[nrobs-1][i];
                laststate=new State(stateNrToCell(i));
                laststatenr=i;
            }
        }
        
        path.add(laststate);
        
        for(int i=nrobs-1;i>0;i--){
            State prevstate=new State(stateNrToCell(backlinks[i][laststatenr]));
            path.add(prevstate);
            laststatenr=backlinks[i][laststatenr];
        }
        Collections.reverse(path);
        Lab10Helper.saveDeltasToFile(deltas, "deltas.txt");
        return path;
    }
}
