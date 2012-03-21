package rlf.main;

import java.util.*;
import rlf.tools.*;

/**
 * Learning behaviour simulating a model of the environment
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-0-04
 * 
 */
public class ModelBased extends Agent {
	
	/**
     * Reward discount factor
     */
    protected double gamma;
    
    /**
     * Learning rate
     */
    protected double alpha;
    
    /**
     * E-Greedy policy
     */
    protected double epsilon;
    
    /**
     * Multi-step planning
     **/
    protected double k;
    
    /**
     * Previous action taken
     */
    protected Action action;
    
    /**
     * Previous state
     */
    protected State state;
    
    /**
     * Q state-action value function
     */
    protected Q q;
    
    protected R r;
    
    protected Model model;
	
	/**
     * @param gamma     Reward discount factor (between 0 and 1)
     * @param alpha     Learning rate (between 0 and 1)
     * @param epsilon   Epsilon parameter for e-greedy policy (between 0 and 1)
     */
    public ModelBased(double gamma, double alpha, double epsilon, int k) {
		super();
		this.gamma = gamma;
		this.alpha = alpha;
		this.epsilon = epsilon;
        this.k = k;
	}
	
    /**
     * Choose a action following a E-Greedy policy
     * This method set the next newAction to be done
     * 
     * @param	newState	the next probable state
     * @return chosed action
     */
    protected Action eGreedy(State newState) {        
        Random random = new Random();
        double explore = random.nextDouble();
        
        // If agent wants to explore
        if (explore < epsilon)
            return getRandomAction();
        
        // Else choose max value
        Action newAction = null;
        Collections.shuffle(actionList);        
        double maxValue = Double.MIN_VALUE;
        for (Action a : actionList) {
            double value = q.get(newState, a);
            if (value > maxValue) {
                maxValue = value;
                newAction = a;
            }
        }
        
        // Else (nothing in the list) get random
        if (newAction == null)
            return getRandomAction();
        
        return newAction;
    }
    
    public void newTrial() {
		valueFunction = q = new Q();
		r = new R();
		model = new Model();
	}
    
    /**
     * Called for the first step of the agent
     */
    @Override
    public Action init(State initialState) {
		super.init(initialState);
		
		// Choose action a using policy (e-greedy) on the value function
        return action = eGreedy(state = initialState);
    }
	
	/**
     * Choose next agent action
     * 
     * @param   s Current state
     * @param   r Reward received for the last action done
     * @return  Next action
     */
    @Override
    public Action chooseAction(State newState, double reward) {
		super.chooseAction(newState, reward);
		
		// Update the model
		r.set(state, action, reward);
		model.update(state, action, newState);
		
		// Q(s,a) <= R(s,a) + gamma*maxQ(s',a')
		q.set(state, action, reward + gamma*maxValue(newState));
		
		// Planning
		planning();
        
        // Choose action a using policy (e-greedy) on the value function
        action = eGreedy(newState);
        
        // Update variables
        state = newState;
        
        // Execute a, observe reward r and new state s'
        return action;
    }
    
    protected void planning() {
		StateActionPair pair;
		State s, newS;
		Action a;
		double reward;
		
		for (int i = 0; i < k; i++) {
			// Choose random state and action
			pair = model.getRandomStateAction();
			s = pair.getState();
			a = pair.getAction();
			
			// Get reward and new state from models
			reward = r.get(s, a);
			newS = model.getNewState(s, a);
			
			// Update value function
			// Q(s,a) <= R(s,a) + gamma*maxQ(s',a')
			q.set(s, a, reward + gamma*maxValue(newS));
		}
	}
    
    protected double maxValue(State newState) {
		// maxQ(s',a')
        double maxValue = Double.MIN_VALUE;
        for (Action a : actionList) {
            double value = q.get(newState, a);
            if (value > maxValue)
                maxValue = value;
        }
        return maxValue;
	}
    
    /**
     * Called during its last reward
     * @param	r Reward received for the last action
     */
    @Override
    public void end (State newState, double r) {
		super.end(newState, r);
				
		// V' = r + gamma * prediction
        //double newValue = r + gamma * predict(newState, eGreedy(newState));
        
        // V
        //double oldValue = q.get(state, action);
        
        // V <= V + alpha * (V' - V)
        //q.set(state, action, oldValue + alpha * (newValue - oldValue));
    }
    
    /**
     * @return learning rate
     */
    public double getAlpha() {
        return alpha;
    }

	/**
	 *	@param alpha 	set learning rate
	 */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

	/**
     * @return e-Greedy policy
     */
    public double getEpsilon() {
        return epsilon;
    }

	/**
	 *	@param epsilon	set e-Greedy policy
	 */
    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

	/**
     * @return reward discount factor
     */
    public double getGamma() {
        return gamma;
    }

	/**
	 *	@param gamma	set reward discount factor
	 */
    public void setGamma(double gamma) {
        this.gamma = gamma;
    }
}
