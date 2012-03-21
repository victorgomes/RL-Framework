package gui;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.concurrent.*;
import java.awt.image.BufferStrategy;

import rlf.main.*;
import utils.*;

/**
 * 
 * @author Victor BF Gomes <vborgesferreiragomes1@sheffield.ac.uk>
 * @version 1.0
 * @since 2012-03-16
 * 
 */
public class GraphFrame extends JFrame implements ActionListener {
	
	public static final long serialVersionUID = 10L;
	
	private GraphPanel panel;
	
	private final JFileChooser fc = new JFileChooser();

	public GraphFrame(double data[]) {			
		super("Graph");		
        
        // Setting layout
        this.setLayout (new BorderLayout());
        this.add(panel = new GraphPanel(data));
		
		// Setting buttons
		JPanel pButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		String[] strButtons = {"Save image ..."};
		for (int i = 0; i < strButtons.length; i++) {
			JButton button = new JButton(strButtons[i]);
			button.addActionListener(this);
			pButtons.add(button);
		}
		this.add(pButtons, BorderLayout.SOUTH);
		
		// Setting frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600,400);
		setLocation(200,200);
		setVisible(true);
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("save image ...")) {
            int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filename = fc.getName();
				
				// Create an image to save
				RenderedImage rendImage = panel.getImage();

				// Write generated image to a file
				try {
					// Save as PNG
					File file = fc.getSelectedFile();
					ImageIO.write(rendImage, "png", file);
				} catch (IOException ex) {
					
				}
			}
            
		}
    }
 }
