package rlf.tools;

/**
 * State value function
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.2
 * @since 2012-02-24
 * 
 */
public class V extends ValueFunction {
    
    /**
	 * Return a value
	 * 
	 * @param	s	State value as input
	 * @return	value
	 */
    public double get(State s) {
        return super.get(s);
    }
    
    /**
	 * Store a value
	 * 
	 * @param	s	State value as input
	 * @param	r	Value (tipically reward)
	 */
    public void set(State s, double d) {
        super.set(s, d);
    }
    
    /**
     * Get greedy state (biggest value)
     * 
     * @return greedy state
     */
    public State getGreedyState() {
        return (State) f.lastKey();
    }
    
    /**
     * @return greedy state value
     */
    public double getGreedyValue() {
        return get(getGreedyState());
    }
}
