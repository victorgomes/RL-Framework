package rlf.tools;

import java.util.*;

/**
 *
 * @author Victor
 */
public class R {
    protected TreeMap<StateActionPair, Double> r;  
    
    public R() {
        r = new TreeMap<StateActionPair, Double>();
    }
    
    public double get(State s, Action a) {
		StateActionPair key = new StateActionPair(s,a);
        if (r.containsKey(key))
			return r.get(key);
		return 0;
    }
    
    public void set(State s, Action a, double d) {
        r.put(new StateActionPair(s,a),d);       
    }
}
