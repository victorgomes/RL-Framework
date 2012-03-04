package rlf.main;

import java.util.*;
import rlf.tools.*;

/**
 * RL agent interface interacting with the environement
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.3
 * @since 2012-02-24
 * 
 */
public interface IAgent {
    
    /**
     * Called for the first step of the agent
     */
    public Action init(State initialState);
    
    /**
     * Choose next agent action
     * 
     * @param   s Current state
     * @param   r Reward received for the last action done
     * @return  Next action
     */
    public Action chooseAction (State s, double r);
    
    /**
     * Called during its last reward
     * 
     * @param   s Current state
     * @param	r Reward received for the last action
     */
    public void end (State s, double r);
    
    /**
     * @param	actions 	set the action list use
     */
    public void setActionList (ArrayList<Action> actions);
    
    /**
     * @return Cumulative reward
     */
    public double getReward();
    
    /**
     * @return episode count
     */
    public int getEpisode();

	/**
	 * @return step count
	 */
    public int getStep();
    
    /**
     * @return value function
     */  
    public IValueFunction getValueFunction();
}
