package utils;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import rlf.main.*;
import rlf.tools.*;

/**
 * Make a siluation of the experiment
 **/
 
public class Simulator extends Thread {
	
	private EssaySettings settings;
	
	public Simulator (EssaySettings settings) {
		this.settings = settings;
	}
	
	public void setEssaySettings(EssaySettings settings) {
		this.settings = settings;
	}
	
	public void run() {
		Essay essay = settings.getEssay();
		BlockingQueue<LogMsg> logQueue = settings.getLogQueue();
		
		Agent agent = essay.getAgent();
		IEnvironment world = essay.getEnv();
		
		try {
			for (int i = 0; i < essay.getTrials(); i++) {
				
				// New trial
				if (logQueue != null)
					logQueue.put(new LogMsg(-1, -1, -1));
				
				agent.newTrial();
				
				for (int j = 0; j < essay.getEpisodes(); j++) {
					
					Action a = agent.init(world.init());

					while(!world.isEpisodeOver()) {
						double r = world.doAction(a);
						a = agent.chooseAction(world.getCurrentState(), r);
						
						if (settings.isGraphical()) {
							Thread.sleep (1000/settings.getSpeed());
						}
					}
					
					if (logQueue != null)
						logQueue.put(new LogMsg(j+1, 
							agent.getStep(), agent.getReward()));
						
					agent.end(world.getCurrentState(), world.end());
				}
							
			}
		} catch (InterruptedException ex) {
			// Stop
			return;
		}
	}
}
