package rlf.tools;

/**
 * Interface for a Value Function
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-02-24
 * 
 */
public interface IValueFunction {
	
	/**
	 * Return a value
	 * 
	 * @param	in	Input of the function
	 * @return	value
	 */
	public double get (Object in);
	
	/**
	 * Store a value
	 * 
	 * @param	in	Input of the function
	 * @param	r	Value (tipically reward)
	 */
	public void set (Object in, double r);
}
