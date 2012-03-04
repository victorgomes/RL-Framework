package utils;

import rlf.main.*;
import rlf.tools.*;
import java.util.concurrent.*;

public class EssaySettings {
	private Essay essay;
	private boolean graphics = false;
	private BlockingQueue<LogMsg> logQueue = null;
	private int speed = 10;
	private State currentStartState;
	private State currentGoalState;
	
	public Essay getEssay() { return essay; }
	public boolean isGraphical() { return graphics; }
	
	public BlockingQueue<LogMsg> getLogQueue() { return logQueue; }
	public void setLogQueue(BlockingQueue<LogMsg> queue) { logQueue = queue; }
	
	public int getSpeed() { return speed; }
	public void setSpeed(int speed) { this.speed = speed; }
	
	public State getCurrentStartState() { return currentStartState; }
	public void setCurrentStartState(State state) { this.currentStartState = state; }
	
	public State getCurrentGoalState() { return currentGoalState; }
	public void setCurrentGoalState(State state) { this.currentGoalState = state; }
	
	public EssaySettings(Essay essay, boolean graphics) {
		this.essay = essay;
		this.graphics = graphics;
		this.currentStartState = essay.getStartState();
		this.currentGoalState = essay.getGoalState();
	}
}
