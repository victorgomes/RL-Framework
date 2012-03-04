package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import utils.*;

public class SimulationFrame extends JFrame implements Runnable, ChangeListener {
	
	public static final long serialVersionUID = 64L;
	
	private SimulationPanel simPanel;
	private EssaySettings settings;
	
	public SimulationFrame(EssaySettings settings) {	
		super("Graphical representation");

		simPanel = new SimulationPanel(settings);
		this.settings = settings;
		this.add(simPanel, BorderLayout.CENTER);
		
		JPanel pSlider = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pSlider.add(new JLabel("Agent speed :"));
		JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 1, 100, 10);
		slider.addChangeListener(this);
		pSlider.add(slider);
		this.add(pSlider, BorderLayout.SOUTH);
		
		this.pack();
		this.setSize(400,400);
		this.setLocation(200,200);
	}
	
	public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting())
			settings.setSpeed(source.getValue());
    }
	
	public void run() {
		this.setVisible(true);
		simPanel.simulate();
	}

}
