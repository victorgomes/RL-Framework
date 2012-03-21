package gui;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.*;
import java.awt.image.BufferStrategy;

import rlf.main.*;
import utils.*;

/**
 * Log window showing step x reward per trial
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.1
 * @since 2012-03-04
 * 
 */
public class LogWindow extends JFrame implements ActionListener {
	
	public static final long serialVersionUID = 1L;
	public static final int QUEUE_CAPACITY = 5;
		
	private JTextArea textArea = new JTextArea();
	private BlockingQueue<LogMsg> queue = new ArrayBlockingQueue<LogMsg>(QUEUE_CAPACITY);
	
	private Simulator simulator;
	private double steps [];
	private double rewards [];
	private int trial;

	public LogWindow() {			
		super("Log Window");		
		
		// Setting menubar
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenuItem quitItem = new JMenuItem("Exit");
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);
        menuBar.add(fileMenu);
        
        // Setting layout
        this.setLayout (new BorderLayout());
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		// Setting buttons
		JPanel pButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		String[] strButtons = {"Show step graph", "Show reward graph"};
		for (int i = 0; i < strButtons.length; i++) {
			JButton button = new JButton(strButtons[i]);
			button.addActionListener(this);
			pButtons.add(button);
		}
		this.add(pButtons, BorderLayout.SOUTH);
		
		// Setting frame properties
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(700, 200);
		this.setSize(350, 400);
		this.setVisible(true);
	}
	
	public void simulate(EssaySettings settings) {
		//if (simulator != null)
			// Stop and Kill simulators
			
		try {
			settings.setLogQueue(queue);
			
			Essay essay = settings.getEssay();
			steps = new double [essay.getEpisodes()];
			rewards = new double [essay.getEpisodes()];
			
			if (settings.isGraphical())
				new Thread(new SimulationFrame(settings)).start();
			
			simulator = new Simulator (settings);
			simulator.start();
			
			showInfo("   Essay ID #" + essay.getId());
			
			trial = 0;			
			while(true) {
				LogMsg msg = queue.take();
				
				int episode = msg.getEpisode();
				int step = msg.getStep();
				double reward = msg.getReward();
				
				if (episode < 0) {
					showInfo("\n\n######## TRIAL " + ++trial + " ########\n");
					continue;
				}
				
				steps[episode-1] += step;
				rewards[episode-1] += reward;
				
				showInfo("   Episode " + episode + "\n"
					+ "         Steps: " + step + "\n"
					+ "         Reward: " + reward + "\n");
			}
			
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}	
		
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("exit"))
            System.exit(0);
        else if(e.getActionCommand().equalsIgnoreCase("show step graph")) {
			new GraphFrame(normalize(steps));
		} else if(e.getActionCommand().equalsIgnoreCase("show reward graph")) {
			JFrame f = new JFrame();
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.add(new GraphPanel(normalize(rewards)));
			f.setSize(400,400);
			f.setLocation(200,200);
			f.setVisible(true);
		}
    }
    
    public double[] normalize (double[] array) {
		double[] normalizedArray = new double[array.length];
		for (int i = 0; i < array.length; i++)
			normalizedArray[i] = array[i]/trial;
		return normalizedArray;
	}	

	public void showInfo(String data) {
		textArea.append(data);
		textArea.setCaretPosition(textArea.getDocument().getLength());
		this.validate();
	}
}
