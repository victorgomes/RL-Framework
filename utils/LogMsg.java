package utils;

public class LogMsg {
	protected int episode;
	protected int step;
	protected double reward;

	public int getEpisode() { return episode; }
	public int getStep() { return step; }
	public double getReward() { return reward; }
	
	public LogMsg(int episode, int step, double reward) {
		this.episode = episode;
		this.step = step;
		this.reward = reward;
	}
}
