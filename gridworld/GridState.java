package gridworld;

import rlf.tools.State;

/**
 *
 * @author Victor
 */
public class GridState extends State {
    protected int x , y ;
    public static final int WIDTHLIMIT = 1000;
    
    public GridState (int x , int y) {
        super(y*WIDTHLIMIT + x);
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof GridState) {
            GridState state = (GridState) other;
            return (( this.x == state.x) && (this.y == state.y));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        return hash;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
