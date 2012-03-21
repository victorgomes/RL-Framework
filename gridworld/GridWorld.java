package gridworld;

import rlf.main.*;
import rlf.tools.*;
import utils.*;

import java.util.*;


/**
 * @author Victor
 */
public class GridWorld implements IEnvironment {    
    protected int height, width;
    
    protected GridState agentInitState;
    protected GridState agentState;
    protected GridState goalState;

    protected int maxSteps;
    
    protected Agent agent;
    protected Maze maze;
    
    
    public GridWorld(Agent agent, int width, int height) {
        this.agent = agent;
        
        this.height = height;
        this.width = width;
        
        maze = Maze.generateDFSMaze(width, height);
        
        agentInitState = new GridState(0,0);
        goalState = new GridState(height-1, width-1);
        maxSteps = 50;
    }
    
    public GridWorld(Essay essay, Maze maze) {
		this.agent = essay.getAgent();
		
		this.height = maze.getHeight();
		this.width = maze.getWidth();
		
		this.maze = maze;
		
		agentInitState = (GridState) essay.getStartState();
        goalState = (GridState) essay.getGoalState();
        maxSteps = essay.getMaxSteps();
	}

    @Override
    public State init() {
		// Random init
		Random random = new Random();
		return agentState = new GridState(random.nextInt(width),
			random.nextInt(height));
        //return agentState = agentInitState;
    }

    @Override
    public double doAction(Action a) {                    
        if (agent.getStep() >= maxSteps)
            return GridRewards.LastReward;
        
        GridState state = getNextState(a);
        
        if (maze.isOutOfBounds(state.getX(),state.getY()))
            return GridRewards.OutOfBounds;
        
        if (maze.isWall(agentState.getX(), agentState.getY(), a))
            return GridRewards.HitWall;
        
        agentState = state;
        
        if (reachGoalState(state))
            return GridRewards.GoalReached;
        
        return GridRewards.ValidMove;
    }
    
    @Override
    public double end() {
        if (agent.getStep() > maxSteps)
            return GridRewards.LastReward;     
        return GridRewards.GoalReached;
    }
    
    protected boolean reachGoalState(GridState state) {
		//System.out.println("State = (" + state.getX() + "," + state.getY() + ")");
		//System.out.println("GoalState = (" + goalState.getX() + "," + goalState.getY() + ")");
        return state.equals(goalState);
    }

    @Override
    public boolean isEpisodeOver() {
        return reachGoalState(agentState) || agent.getStep() >= maxSteps;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    protected GridState getNextState(Action a) {
        int dx = 0, dy = 0;
        switch (a.toUniqueId()) {
            case GridActions.E:
                dx++;
                break;
            case GridActions.N:
                dy--;
                break;
            case GridActions.W:
                dx--;
                break;
            case GridActions.S:
                dy++;
                break;
        }
        return new GridState(
                agentState.getX() + dx,
                agentState.getY() + dy
        );
    }
    
    public State setStartPosition (int x, int y) {
        if (height < y || width < x)
            return null;
        return agentInitState = new GridState(x, y);
    }
    
    public State setGoalState(int x, int y) {
        if (height < y || width < x)
            return null;
        return goalState = new GridState(x,y);
    }
    
    public int getMaxSteps() {
        return maxSteps;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }
    
    @Override
    public State getCurrentState() {
		return agentState;
    }
    
    public Maze getMaze() {
        return maze;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    
}
