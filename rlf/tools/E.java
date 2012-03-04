package rlf.tools;

import java.util.TreeMap;

/**
 * Eligibility function
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-02-24
 * 
 */
public class E extends TreeMap<StateActionPair, Double> {
	
	public static final long serialVersionUID = 128L;
	
	/**
	 * Return a value
	 * 
	 * @param	s	state
	 * @param	a	action
	 * @return	value
	 */
    public double get(State s, Action a) {		
        StateActionPair key = new StateActionPair(s,a);
        if (this.containsKey(key))
            return this.get(key);
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
        this.put(new StateActionPair(s,a),d);
    }
}
