/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rlf.tools;

import java.util.TreeMap;

/**
 *
 * @author Victor
 */
public class T {
    protected TreeMap<State, TreeMap<Action, TreeMap<State, Double>>> t;
    
    public T() {
        t = new TreeMap<State, TreeMap<Action, TreeMap<State, Double>>>();
    }
    
    public double get(State s, Action a, State ns) {
        return t.get(s).get(a).get(ns);
    }
    
    public void set(State s, Action a, State ns, double d) {
        TreeMap<Action, TreeMap<State, Double>> actions = t.get(s);       
        if (actions == null) {
            actions = new TreeMap<Action, TreeMap<State, Double>>();
            t.put(s, actions);
        }
        
        TreeMap<State, Double> states = actions.get(a);
        if (states == null) {
            states = new TreeMap<State, Double>();
            actions.put(a, states);
        }
        
        states.put(ns, d);    
    }
}
