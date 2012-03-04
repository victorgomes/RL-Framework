package rlf.main;

import rlf.tools.*;

/**
 * RL environement interface
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-02-24
 * 
 */
public interface IEnvironment {
    
    /**
     * Initialize environment
     * @return      Initial state
     */
    public State init();
    
    /**
     * Execute the given action
     * @param a     Action to execute
     * @return      Reward value
     */
    public double doAction(Action a);
    
    /**
     * Called in the end of each episode
     **/
    public double end();
    
    /**
     * @return current environment state
     */
    public State getCurrentState();
    
    /**
     * @return if the episode for the agent is over
     */
    public boolean isEpisodeOver();
    
    /**
     * Reset the environment to its initial state
     */
    public void reset();
}
