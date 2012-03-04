package rlf.tools;

import java.util.TreeMap;

/**
 * Implementation of a Value Function
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-02-24
 * 
 */
public class ValueFunction implements IValueFunction {
	
	/**
	 * Mapping of input and value
	 */
	protected TreeMap<Object, Double> f;
	
	/**
	 * Default constructor
	 */
	public ValueFunction() {
		f = new TreeMap<Object, Double>();
	}
	
	/**
	 * Return a value
	 * 
	 * @param	in	Input of the function
	 * @return	value
	 */
	public double get (Object in) {
		if (f.containsKey(in))
			return f.get(in);
		return 0;
	}
	
	/**
	 * Store a value
	 * 
	 * @param	in	Input of the function
	 * @param	r	Value (tipically reward)
	 */
	public void set (Object in, double r) {
		f.put(in, r);
	}
}
