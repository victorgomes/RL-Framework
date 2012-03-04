package rlf.main;

import java.util.*;
import rlf.tools.*;

/**
 * Learning behaviour without creates a model of the environment
 * Using a Temporal difference learning method  and E-greedy policy
 * 
 * V <= V + alpha * (r + gamma *V' - V)
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-02-20
 * 
 */
public abstract class ModelFree extends Agent {
	
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
    
    protected double lambda;
    
    protected boolean eligibility = false;
    
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
    
    protected E e = null;
	
	/**
     * @param gamma     Reward discount factor (between 0 and 1)
     * @param alpha     Learning rate (between 0 and 1)
     * @param epsilon   Epsilon parameter for e-greedy policy (between 0 and 1)
     */
    public ModelFree(double gamma, double alpha, double epsilon) {
		super();
		this.gamma = gamma;
        this.alpha = alpha;
        this.epsilon = epsilon;
	}
	
	public ModelFree(double gamma, double alpha, double epsilon, double lambda) {
		this(gamma, alpha, epsilon);
		this.lambda = lambda;
		eligibility = true;
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
		if (eligibility)
			e = new E();
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
    public Action chooseAction(State newState, double r) {
		super.chooseAction(newState, r);
		
		// Choose action a using policy (e-greedy) on the value function
		Action newAction = eGreedy(newState);
		
		// V' = r + gamma * prediction
        double newValue = r + gamma * predict(newState, newAction);
        
        // V
        double oldValue = q.get(state, action);
        
        // d = V' - V
        double delta = newValue - oldValue;
        
        // Update trace
        if (eligibility) {
			//e(s, a) <= e(s, a) + 1
			e.set(state, action, e.get(state, action) + 1);
			
			// for all s, a:
			for (Map.Entry<StateActionPair,Double> entry : e.entrySet()) {
				StateActionPair pair = entry.getKey();
				// Q(s, a) <= Q(s, a) + alpha*delta*e(s, a)
				q.set(pair, q.get(pair) + alpha*delta*e.get(pair));
				// e(s, a) <= gamma*lamda*e(s, a)
				e.put(pair, gamma*lambda*e.get(pair));
			}
		} else {
			// V <= V + alpha * delta
			q.set(state, action, oldValue + alpha * delta);
		}
        
        // Update variables
        state = newState;
        action = newAction;
        
        // Execute a, observe reward r and new state s'
        return action;
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
