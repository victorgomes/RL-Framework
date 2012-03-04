package gridworld;

import java.awt.Point;
import java.util.*;
import rlf.tools.Action;

/**
 *
 * @author Victor
 */
public class Maze {
    
    protected int width;
    protected int height;
    
    protected int maze[][];
    
    protected final int Walls = GridActions.E | GridActions.N | GridActions.W | GridActions.S;
    
    protected Maze(int width, int height) {
        this.width = width;
        this.height = height;
        
        maze = new int[width][height];
    }
    
    public Maze (int width, int height, String strMaze) {
		this.width = width;
        this.height = height;
        
        maze = new int[width][height];
		
		int i = 0, j = 0;
		StringTokenizer rows = new StringTokenizer(strMaze, "\n");
		while (rows.hasMoreTokens()) {
			StringTokenizer walls = new StringTokenizer(rows.nextToken());
			while (walls.hasMoreTokens()) {
				maze[i++][j] = Integer.parseInt(walls.nextToken());
			}
			i=0; j++; 
		}
	}
	
	public int getWidth() { return width; }
	
	public int getHeight() { return height; }
    
    public boolean isWall (int x, int y, Action a) {
        return (maze[x][y] & a.toUniqueId()) > 0;
    }
    
    public boolean isOutOfBounds (int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return true;
        }
        return false;
    }
    
    protected void generateDFS() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                maze[i][j] = Walls;
        
        Random random = new Random();
        Stack<Point> visited = new Stack<Point>();       
        
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        Point currentCell = new Point(x,y);
        
        int totalCells = width*height;
        int visitedCells = 1;
        
        do {
            ArrayList<Point> neighbors = getValidNeighbors(currentCell.x, currentCell.y);
            if (!neighbors.isEmpty()) {
                Point nextCell = neighbors.get(random.nextInt(neighbors.size()));
                RemoveWallBetween(currentCell, nextCell);
                visited.push(nextCell);
                currentCell = nextCell;
                visitedCells++;
            } else {
                currentCell = visited.pop();
            }
        } while (visitedCells < totalCells && !visited.isEmpty());

    }
    
    private void RemoveWallBetween(Point p1, Point p2) {
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;
        
        if (dx > 0) {
            maze[p1.x][p1.y] -= GridActions.E;
            maze[p2.x][p2.y] -= GridActions.W;
        }
        
        if (dx < 0) {
            maze[p1.x][p1.y] -= GridActions.W;
            maze[p2.x][p2.y] -= GridActions.E;
        }
        
        if (dy > 0) {
            maze[p1.x][p1.y] -= GridActions.S;
            maze[p2.x][p2.y] -= GridActions.N;
        }
        
        if (dy < 0) {
            maze[p1.x][p1.y] -= GridActions.N;
            maze[p2.x][p2.y] -= GridActions.S;
        }
        
    }
    
    private ArrayList<Point> getValidNeighbors(int x, int y) {
        ArrayList<Point> neightbors = new ArrayList<Point>();
        
        if (x - 1 >= 0 && maze[x-1][y] == Walls)
            neightbors.add(new Point(x-1, y));
        if (x + 1 < width && maze[x+1][y] == Walls)
            neightbors.add(new Point(x+1, y));
        if (y - 1 >= 0 && maze[x][y-1] == Walls)
            neightbors.add(new Point(x, y-1));
        if (y + 1 < height && maze[x][y+1] == Walls)
            neightbors.add(new Point(x, y+1));
        
        return neightbors;
    }
    
    public int[][] getMazeInfo() {
        return maze;
    }
    
    public void testGenerate() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                maze[i][j] = Walls;
        
        maze[0][0] -= GridActions.S;
        maze[0][1] -= GridActions.N;
        maze[0][1] -= GridActions.E;
        maze[1][1] -= GridActions.W;
    }
    
    /**
     * DFS Algorithm
     */
    public static Maze generateDFSMaze(int width,int height) {
        Maze maze = new Maze(width, height);
        maze.generateDFS();
        //maze.testGenerate();
        return maze;
    }

    
}
