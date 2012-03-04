/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gridworld;

/**
 *
 * @author Victor
 */
public class GridRewards {
    public static final double NullMove    = -1;
    public static final double ValidMove   = -1;
    public static final double OutOfBounds = -1;
    public static final double HitWall     = -1;
    public static final double GoalReached = 100;
    public static final double LastReward  = -1;
}
