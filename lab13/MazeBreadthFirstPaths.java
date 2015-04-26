import java.util.Observable;
import java.util.LinkedList;
import java.util.Queue;
/** 
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields: 
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze; 
    private Queue<Integer> fringe = new LinkedList<Integer>();

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);     
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s; 
    }

    /** Conducts a breadth first search of the maze starting at vertex x. */
    private void bfs(int v) {
        fringe.add(v);
        if (v == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        while (!fringe.isEmpty()) {
            int currentV = fringe.remove();
            marked[currentV] = true;
            announce();
            for (int w : maze.adj(currentV)) {
                if (!marked[w]) {
                    fringe.add(w);
                    distTo[w] = distTo[currentV] + 1;
                    edgeTo[w] = currentV; 
                }
            }
        }

    }


    @Override
    public void solve() {
        bfs(s);
    }
} 

