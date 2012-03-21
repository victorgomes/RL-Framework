package rlf.tools;

import java.util.*;

/**
 *
 * @author Victor
 */
public class Model {
    protected TreeMap<StateActionPair, State> world;  
    protected Random random;
    
    public Model() {
        world = new TreeMap<StateActionPair, State>();
        random = new Random();
    }
    
    public State getNewState (State s, Action a) {
		StateActionPair key = new StateActionPair(s,a);
        if (world.containsKey(key))
			return world.get(key);
		return s;
    }
    
    public void update (State s, Action a, State newS) {
        world.put(new StateActionPair(s,a),newS);       
    }
    
    public StateActionPair getRandomStateAction() {
		int index = random.nextInt(world.size());
		int i = 0;
		for (StateActionPair pair : world.keySet()) {
			if (i == index)
				return pair;
			i++;
		}
		return null;
	}
}

