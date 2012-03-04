package rlf.tools;

/**
 * Wrapper state action pair class
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.1
 * @since 2012-01-20
 * 
 */
public class StateActionPair implements Comparable<StateActionPair> {
	
	/**
	 * Given state
	 */
    private State state;
    
    /**
     * Given action
     */
    private Action action;

	/**
	 * @param state
	 * @param action
	 */
    public StateActionPair(State state, Action action) {
        super();
        this.state = state;
        this.action = action;
    }
    
    /**
     * @return state
     */
    public State getState() {
        return state;
    }

	/**
	 * @return action
	 */
    public Action getAction() {
        return action;
    }

	/**
	 * Compare two state action pairs
	 * 
	 * @param o	pair to compare
	 * @return -1, 0 or 1
	 */
    @Override
    public int compareTo(StateActionPair o) {
        if (this.equals(o))
            return 0;
            
        if (this.state.toUniqueId() == o.state.toUniqueId())
			return this.action.compareTo(o.action);
		
		return this.state.compareTo(o.state);
    }
    
    /**
     * @param other	object to compare
     * @return if object and this are equals
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof StateActionPair) {
			StateActionPair otherPair = (StateActionPair) other;
			return 
			((  this.state == otherPair.state ||
				( this.state != null && otherPair.state != null &&
				  this.state.equals(otherPair.state))) &&
			(   this.action == otherPair.action ||
				( this.action != null && otherPair.action != null &&
				  this.action.equals(otherPair.action))) );
        }

        return false;
    }
    
    /**
     * @return string representaion of the pair
     */
    @Override
    public String toString()
    { 
		return "(" + state + ", " + action + ")"; 
    }
    
    /**
     * @return unique hash code identifier
     */
    @Override
    public int hashCode() {
        int hashFirst = state != null ? state.hashCode() : 0;
        int hashSecond = action != null ? action.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }
    
}
