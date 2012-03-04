package rlf.main;

import rlf.tools.*;

/**
 * Q-Learning method created by Watkins, 1989
 * 
 * Q(s_t,a_t) = Q(s_t, a_t) + alpha * (predicted_value -  Q(s_t, a_t))
 *   where predicted_value = r_(t+1) + gamma * max (Q(s_(t+1),a_(t+1)))
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-02-20
 * 
 */
public class QLearningLambda extends QLearning {
    
    /**
     * @param gamma     Reward discount factor (between 0 and 1)
     * @param alpha     Learning rate (between 0 and 1)
     * @param epsilon   Epsilon parameter for e-greedy policy (between 0 and 1)
     */    
    public QLearningLambda (double gamma, double alpha, double epsilon, double lambda) {
		super (gamma, alpha, epsilon, lambda);
	}    
}
