package rlf.tools;

/**
 * Super class for State and Action unique identifier
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.1
 * @since 2012-02-20
 * 
 */
class UniqueIdentifier implements Comparable<UniqueIdentifier> {
	
	/**
	 * Unique identifier
	 */
    protected int i = 0;
    
    
    /**
     * @param i	unique identifier
     */
    public UniqueIdentifier (int i) {
        this.i = i;
    }
    
    /**
     * @return unique identifier
     */
    public int toUniqueId() {
        return i;
    }

	/**
	 * @return unique identifier in string format
	 **/
    @Override
    public String toString() {
        return Integer.toString(i);
    }
    
    /**
     * Compare two super classes
     * 
     * @param o 	identifier to compare
     * @return -1 if identifier is lower, 0 if equal and 1 if upper
     */ 
    @Override
    public int compareTo(UniqueIdentifier o) {
        if (i < o.i)
            return -1;
        if (i > o.i)
            return 1;
        return 0;
    }
	
	/**
     * Compare two objects
     * 
     * @param obj	object to compare
     * @return 	if obj is equal this object
     */ 
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        UniqueIdentifier other = (UniqueIdentifier) obj;
        if (this.i != other.i) {
            return false;
        }
        
        return true;
    }
	
	/**
     * @return unique hash code for the object
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.i;
        return hash;
    }

    

}
