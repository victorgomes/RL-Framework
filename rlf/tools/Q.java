package rlf.tools;

/**
 * StateAction value function
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.3
 * @since 2012-02-24
 * 
 */
public class Q extends ValueFunction {    
    
    /**
	 * Return a value
	 * 
	 * @param	s	state
	 * @param	a	action
	 * @return	value
	 */
    public double get(State s, Action a) {		
        StateActionPair key = new StateActionPair(s,a);
        if (f.containsKey(key))
            return f.get(key);
        return 0;
    }
    
    /**
	 * Store a value
	 * 
	 * @param	s	state
	 * @param	a	action
	 * @param	r	Value (tipically reward)
	 */
    public void set(State s, Action a, double d) {
        f.put(new StateActionPair(s,a),d);
    }  
}
