package utils;

import org.w3c.dom.*;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.SAXParseException;

import gridworld.*;
import rlf.main.*;
import rlf.tools.*;


public class Essay {
	
	protected int id, maxSteps, episodes, trials;
	protected Agent agent;
	protected GridWorld environment;
	protected State startState;
	protected State goalState;
	
	public int getId() { return id; }
	public int getMaxSteps() { return maxSteps; }
	public int getEpisodes() { return episodes; }
	public int getTrials() { return trials; }
	public Agent getAgent() { return agent; }
	public GridWorld getEnv() { return environment; }
	public State getStartState() { return startState; }
	public State getGoalState() { return goalState; }
	
	protected Essay() {
	}
	
	public static Essay readEssay(String filename) {
		try {
			// Create XML document
			Document doc = DocumentBuilderFactory
				.newInstance()
				.newDocumentBuilder()
				.parse ( new File(filename) );
			
			// Normalize text representation
			// Verify if root is not an essay element
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			if (!root.getNodeName().equals("essay"))
				throw new Exception("Invalid XML root");
			
			// Instantiate essay
			Essay essay = new Essay();
			
			// Set essay
			essay.id = Integer.parseInt(root.getAttribute("id"));
			
			essay.maxSteps = Integer.parseInt( root
				.getElementsByTagName("maxSteps").item(0)
				.getChildNodes().item(0)
				.getNodeValue().trim());
			
			essay.episodes = Integer.parseInt( root
				.getElementsByTagName("episodes").item(0)
				.getChildNodes().item(0)
				.getNodeValue().trim());
				
			essay.trials = Integer.parseInt( root
				.getElementsByTagName("trials").item(0)
				.getChildNodes().item(0)
				.getNodeValue().trim());
				
			Element startStateElement = (Element) root.
				getElementsByTagName("startState").item(0);			
			essay.startState = new GridState (
				Integer.parseInt(startStateElement.getAttribute("x")),
				Integer.parseInt(startStateElement.getAttribute("y")));
				
			Element goalStateElement = (Element) root.
				getElementsByTagName("goalState").item(0);			
			essay.goalState = new GridState (
				Integer.parseInt(goalStateElement.getAttribute("x")),
				Integer.parseInt(goalStateElement.getAttribute("y")));
			
			// Instantiate agent
			double gamma, alpha, epsilon, lambda = 0;
			
			Element agentElement = (Element)root
				.getElementsByTagName("agent").item(0);
			
			gamma = Double.parseDouble( agentElement
				.getElementsByTagName("gamma").item(0)
				.getChildNodes().item(0)
				.getNodeValue().trim());
				
			alpha = Double.parseDouble( agentElement
				.getElementsByTagName("alpha").item(0)
				.getChildNodes().item(0)
				.getNodeValue().trim());
				
			epsilon = Double.parseDouble( agentElement
				.getElementsByTagName("epsilon").item(0)
				.getChildNodes().item(0)
				.getNodeValue().trim());
				
			NodeList lambdaNodeList = agentElement.getElementsByTagName("lambda");
			if (lambdaNodeList.getLength() > 0)
				lambda = Double.parseDouble( lambdaNodeList.item(0)
					.getChildNodes().item(0)
					.getNodeValue().trim());
			
			String agentType = agentElement.getAttribute("type");
			
			if (agentType.equals("QLearning"))
				essay.agent = new QLearning(gamma, alpha, epsilon);
			else if (agentType.equals("Sarsa"))
				essay.agent = new Sarsa(gamma, alpha, epsilon, 0.5);
			else if (agentType.equals("QLearningLambda"))
				essay.agent = new QLearningLambda(gamma, alpha, epsilon, lambda);
			else
				throw new Exception("Unknown Agent type");
				
			essay.agent.setActionList(GridActions.getActions());
				
			// Instantiate environment
			int width, height;
			Element worldElement = (Element)root
				.getElementsByTagName("environment").item(0);
			
			width = Integer.parseInt( worldElement
				.getElementsByTagName("width").item(0)
				.getChildNodes().item(0)
				.getNodeValue().trim());
			
			height = Integer.parseInt( worldElement
				.getElementsByTagName("height").item(0)
				.getChildNodes().item(0)
				.getNodeValue().trim());
				
			String strMaze = worldElement
				.getElementsByTagName("maze").item(0)
				.getChildNodes().item(0)
				.getNodeValue().trim();
				
			Maze maze = new Maze (width, height, strMaze);	
			essay.environment = new GridWorld(essay.agent, maze);
			
			return essay;
		
		} catch (SAXParseException ex) {
			System.err.println("** Parsing error, line " + ex.getLineNumber()
				+ ", URI: " + ex.getSystemId());
			System.err.println("  " + ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
}
