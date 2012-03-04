/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gridworld;

import java.util.ArrayList;
import java.util.Random;
import rlf.tools.Action;

/**
 *
 * @author Victor
 */
public final class GridActions {
    public static final int E = 1;
    public static final int N = 2;
    public static final int W = 4;
    public static final int S = 8;
    
    public static final Action Left = new Action(W);
    public static final Action Up = new Action(N);
    public static final Action Right = new Action(E);
    public static final Action Down = new Action(S);
    
    public static ArrayList<Action> getActions() {
        ArrayList<Action> actions = new ArrayList<Action>();
        actions.add(Left);
        actions.add(Up);
        actions.add(Right);
        actions.add(Down);        
        return actions;
    }
}
