package rlf.main;

import java.util.*;
import rlf.tools.*;

/**
 * Temporal difference learning method (E-greedy policy)
 * 
 * V_s = V_s + alpha * (predicted_value - V_s)
 *    where predicted_value = r + gamma * V_s'
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-02-20
 * 
 */
public abstract class TD extends Agent {
    
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
     * Q state-action value function
     */
    protected Q q;
    
    /**
     * Previous action taken
     */
    protected Action action;
    
    /**
     * Previous state
     */
    protected State state;
    
    /**
     * New action
     */
    protected Action newAction;
    
    /**
     * @param gamma     Reward discount factor (between 0 and 1)
     * @param alpha     Learning rate (between 0 and 1)
     * @param epsilon   Epsilon parameter for e-greedy policy (between 0 and 1)
     */
    public TD(double gamma, double alpha, double epsilon) {
        super();
        this.gamma = gamma;
        this.alpha = alpha;
        this.epsilon = epsilon;
        valueFunction = q = new Q();
    }
    
    /**
     * @return predicted reward for the current step
     */
    protected abstract double predict (State state, Action action);
    
    /**
     * Choose a action following a E-Greedy policy
     * This method set the next newAction to be done
     * 
     * @param	newState	the next probable state
     * @return expected reward value
     */
    protected double eGreedy(State newState) {
        newAction = null;
        
        Random random = new Random();
        double explore = random.nextDouble();
        
        // If agent wants to explore
        if (explore < epsilon) {
            newAction = getRandomAction();
            double value = predict(newState, newAction);
            return value;
        }
        
        // Else choose max value
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
        if (newAction == null) {
            maxValue = 0;
            newAction = getRandomAction();
        }
        
        return maxValue;
    }
    
    /**
     * Called for the first step of the agent
     */
    @Override
    public Action init(State initialState) {
		super.init(initialState);
        eGreedy(state = initialState);
        return action = newAction;
    }

	/**
     * Choose next agent action
     * 
     * @param   s Current state
     * @param   r Reward received for the last action done
     * @return  Next action
     */
    @Override
    public Action chooseAction(State newState, double r) {
		super.chooseAction(newState, r);
        double newQ = eGreedy(newState);
        double oldQ = q.get(state, action);
        q.set(state, action, oldQ + alpha * (r + gamma * newQ - oldQ));
        action = newAction;
        state = newState;
        return action;
    }
    
    /**
     * Called during its last reward
     * 
     * @param   s Current state
     * @param	r Reward received for the last action
     */
    @Override
    public void end (State s, double r) {
		super.end(s, r);
        double oldQ = q.get(state, action);
        q.set(state, action, oldQ + alpha * (r - oldQ));
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
