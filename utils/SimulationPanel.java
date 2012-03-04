package utils;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferStrategy;

import javax.swing.*;

import rlf.main.*;
import rlf.tools.*;
import gridworld.*;
import utils.*;

/**
 *
 * @author Victor
 */
public class SimulationPanel extends JPanel {
	public static final long serialVersionUID = 32L;
	
	private final static int gridX = 40;
    private final static int gridY = 40;
    
    private final static int gridCellSize = 40;
    private final static int gridTargetSize = 20;
    
    private final static int agentSize = 10;
    private final static int stateSize = 7;
    private final static int rewardSize = 5;
    
    private final static BasicStroke solid = new BasicStroke();
    
    private RenderingHints renderHints;
    
    private GridState agentState;
    private GridWorld world;
    
    protected EssaySettings settings;
    
    public SimulationPanel (EssaySettings settings) {
		renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
        renderHints.put(RenderingHints.KEY_RENDERING,
                     RenderingHints.VALUE_RENDER_QUALITY);
        
        this.settings = settings;
        
        world = settings.getEssay().getEnv();
        agentState = (GridState) settings.getEssay().getStartState();
	}
	
	public void simulate() {
		while (true) {
			try {
				agentState = (GridState) world.getCurrentState();
				if (agentState == null)
					agentState = (GridState) settings.getCurrentStartState();
				this.repaint();
				Thread.sleep(1000/settings.getSpeed());
			}
			catch (InterruptedException ex) {
				//ex.printTraceRoute();
				return;
			}
		}
		
	}
	
	@Override
    public void paint (Graphics g) {      
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHints(renderHints);
        
        g2.setPaint(Color.WHITE);
        g2.fill(new Rectangle2D.Double(0,0,this.getWidth(),this.getHeight()));
        
        drawGridworld(g2);
        drawExpectedRewards(g2);
        drawAgent(g2);
        drawTarget(g2);
    }
    
    protected void drawGridworld(Graphics2D g2) {        
        g2.setPaint(Color.BLACK);
        g2.setStroke(solid);
        
        if (world != null) {
            int[][] m = world.getMaze().getMazeInfo();

            for (int i = 0; i < world.getWidth(); i++) {
                for (int j = 0; j < world.getHeight(); j++) {
                    if ((m[i][j] & GridActions.E) > 0) {
                        g2.draw(new Line2D.Double(
                            gridX + gridCellSize*(i+1), gridY + gridCellSize*j, 
                            gridX + gridCellSize*(i+1), gridY + gridCellSize*(j+1)));
                    }
                    if ((m[i][j] & GridActions.N) > 0) {
                        g2.draw(new Line2D.Double(
                            gridX + gridCellSize*i, gridY + gridCellSize*j, 
                            gridX + gridCellSize*(i+1), gridY + gridCellSize*j));
                    }
                    if ((m[i][j] & GridActions.W) > 0) {
                        g2.draw(new Line2D.Double(
                            gridX + gridCellSize*i, gridY + gridCellSize*j, 
                            gridX + gridCellSize*i, gridY + gridCellSize*(j+1)));
                    }
                    if ((m[i][j] & GridActions.S) > 0) {
                        g2.draw(new Line2D.Double(
                            gridX + gridCellSize*i, gridY + gridCellSize*(j+1), 
                            gridX + gridCellSize*(i+1), gridY + gridCellSize*(j+1)));
                    }
                }
            }
        }
    }
    
    protected void drawAgent(Graphics2D g2) {
        int x = agentState.getX();
        int y = agentState.getY();
        g2.setStroke(solid);
        g2.setPaint(Color.BLACK);
        g2.fill(new Ellipse2D.Double(
                gridX + gridCellSize*x + (gridCellSize-agentSize)/2, 
                gridY + gridCellSize*y + (gridCellSize-agentSize)/2, 
                agentSize+1, agentSize+1)
        );
    }
    
    protected void drawTarget(Graphics2D g2) {
		GridState goalState = (GridState) settings.getCurrentGoalState(); 
		int x = goalState.getX();
        int y = goalState.getY();
        g2.setStroke(solid);
        g2.setPaint(Color.RED);
        g2.fill(new Rectangle2D.Double(
                gridX + gridCellSize*x + (gridCellSize-gridTargetSize)/2, 
                gridY + gridCellSize*y + (gridCellSize-gridTargetSize)/2, 
                gridTargetSize+1, gridTargetSize+1)
        );
        
    }
    
    protected void drawExpectedRewards(Graphics2D g2) {  
		Q q = (Q) settings.getEssay().getAgent().getValueFunction(); 
		if (q == null)
			return;
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                drawExpectedReward(g2, q, x, y);
            }
        }
    }
    
    protected void drawExpectedReward(Graphics2D g2, Q q, int x, int y) {  
        double r = 0;
        boolean flagState = false;
        
        State state = new GridState(x, y);
        
        g2.setStroke(solid);
        g2.setPaint(Color.BLUE);
        /*
        r = q.get(state, GridActions.Left);
        if (r != 0) {
            g2.fill(new Rectangle2D.Double(
                    gridX + gridCellSize*x + (gridCellSize-rewardSize)/2*(1-normalizeReward(r)), 
                    gridY + gridCellSize*y + (gridCellSize-rewardSize)/2, 
                    rewardSize+1, rewardSize+1)
            );
            flagState = true;
        }
        */
        r = q.get(state, GridActions.Left);
        if (r != 0) {
            g2.fill(new Rectangle2D.Double(
                    gridX + gridCellSize*x + (gridCellSize-rewardSize)/2*(1-normalizeReward(r)), 
                    gridY + gridCellSize*y + (gridCellSize-rewardSize)/2, 
                    rewardSize+(gridCellSize-rewardSize)/2*normalizeReward(r)+1, rewardSize+1)
            );
            flagState = true;
        }
        
        r = q.get(state, GridActions.Up);
        if (r != 0) {
            g2.fill(new Rectangle2D.Double(
                    gridX + gridCellSize*x + (gridCellSize-rewardSize)/2, 
                    gridY + gridCellSize*y + (gridCellSize-rewardSize)/2*(1-normalizeReward(r)), 
                    rewardSize+1, rewardSize+1)
            );
            flagState = true;
        }
        
        r = q.get(state, GridActions.Right);
        if (r != 0) {
            g2.fill(new Rectangle2D.Double(
                    gridX + gridCellSize*x + (gridCellSize-rewardSize)/2, 
                    gridY + gridCellSize*y + (gridCellSize-rewardSize)/2, 
                    rewardSize+(gridCellSize-rewardSize)/2*normalizeReward(r)+1, rewardSize+1)
            );
            flagState = true;
        }
        
        r = q.get(state, GridActions.Down);
        if (r != 0) {
            g2.fill(new Rectangle2D.Double(
                    gridX + gridCellSize*x + (gridCellSize-rewardSize)/2, 
                    gridY + gridCellSize*y + (gridCellSize-rewardSize)/2*(1+normalizeReward(r)), 
                    rewardSize+1, rewardSize+1)
            );
            flagState = true;
        }
        
        if (flagState) {
            g2.setPaint(Color.GREEN);
            g2.fill(new Rectangle2D.Double(
                    gridX + gridCellSize*x + (gridCellSize-stateSize)/2, 
                    gridY + gridCellSize*y + (gridCellSize-stateSize)/2, 
                    stateSize+1, stateSize+1)
            );
        }
    }
    
    protected double normalizeReward (double r) {
        if (r < 0)
            return 0;
        
        if (r < 90)
            return 0.0 + 0.01*r;
        
        return 0.9;
	}
}
