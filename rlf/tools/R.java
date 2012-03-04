package rlf.tools;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Victor
 */
public class R {
    protected TreeMap<State, TreeMap<Action, Double>> r;  
    
    public R() {
        r = new TreeMap<State, TreeMap<Action, Double>>();
    }
    
    public double get(State s, Action a) {
        return r.get(s).get(a);
    }
    
    public void set(State s, Action a, double d) {
        TreeMap<Action, Double> actions = r.get(s);
        
        if (actions == null) {
            actions = new TreeMap<Action, Double>();
            r.put(s, actions);
        }
        
        actions.put(a, d);        
    }
}
