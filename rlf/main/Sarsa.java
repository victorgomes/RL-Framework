package rlf.main;

import rlf.tools.Action;
import rlf.tools.State;

/**
 * State-Action-Reward-State-Action (SARSA) method
 * 
 * Q(s_t,a_t) = Q(s_t, a_t) + alpha * (predicted_value -  Q(s_t, a_t))
 *   where predicted_value = r_(t+1) + gamma * Q(s_(t+1),a_(t+1))
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-02-20
 * 
 */
public class Sarsa extends ModelFree {
    /**
     * SARSA(lambda) algorithm parameter
     */
    protected double lambda;
    
    /**
     * @param gamma     Reward discount factor (between 0 and 1)
     * @param alpha     Learning rate (between 0 and 1)
     * @param epsilon   Epsilon parameter for e-greedy policy (between 0 and 1)
     * @param lambda	SARSA(lambda) algorithm parameter
     */
    public Sarsa (double gamma, double alpha, double epsilon, double lambda) {
        super(gamma, alpha, epsilon);
        this.lambda = lambda;
    }

	/**
	 * prediction = Q(s_(t+1),a_(t+1))
	 * 
     * @return predicted reward for the current step
     */
    @Override
    protected double predict(State newState, Action action) {
        return q.get(newState, action);
    }  
    
}
