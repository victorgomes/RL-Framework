package rlf.main;

import java.util.*;
import rlf.tools.*;

/**
 * RL agent interacting with the environement
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.1
 * @since 2012-02-24
 * 
 */
public abstract class Agent implements IAgent {
    
    /**
     * Episode count
     */
    protected int episode;
    
    /**
     * Step count
     */
    protected int step;
    
    /**
     * Cumulative reward for the current episode
     */
    protected double cumulativeReward;
    
    /**
     * Possible actionList that an agent can made 
     */
    protected ArrayList<Action> actionList;
    
    /**
     * Value function used to store expected rewards
     */
    protected IValueFunction valueFunction;
    
    public abstract void newTrial();
    
    /**
     * Called for the first step of the agent
     */
    public Action init(State initialState) {
		step = 0;
        cumulativeReward = 0;
        return actionList.get(0);
	}
    
    /**
     * Choose next agent action
     * 
     * @param   s Current state
     * @param   r Reward received for the last action done
     * @return  Next action
     */
    public Action chooseAction (State s, double r) {
		cumulativeReward += r;
        step++;
        return actionList.get(0);
	}
    
    /**
     * Called during its last reward
     * 
     * @param   s Current state
     * @param	r Reward received for the last action
     */
    public void end (State s, double r) {
		episode++;
	}
    
    /**
     * @param	actions 	set the action list use
     */
    public void setActionList (ArrayList<Action> actions) {
        this.actionList = actions;
    }
    
    /**
     * @return Cumulative reward
     */
    public double getReward() {
        return cumulativeReward;
    }
    
    /**
     * @return a random action in the action list
     */
    protected Action getRandomAction() {
        Random random = new Random();
        int index = random.nextInt(actionList.size());
        return actionList.get(index);
    }
    
    /**
     * @return episode count
     */
    public int getEpisode() {
        return episode;
    }

	/**
	 * @return step count
	 */
    public int getStep() {
        return step;
    }
    
    /**
     * @return value function
     */    
    public IValueFunction getValueFunction() {
		return valueFunction;
	}
}
