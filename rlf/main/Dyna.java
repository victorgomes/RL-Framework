package rlf.main;

import rlf.tools.*;

/**
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-03-21
 * 
 */
public class Dyna extends ModelBased {
    
    /**
     * @param gamma     Reward discount factor (between 0 and 1)
     * @param epsilon   Epsilon parameter for e-greedy policy (between 0 and 1)
     */
    public Dyna (double gamma, double alpha, double epsilon, int k) {
        super (gamma, alpha, epsilon, k);
    }
    
}

