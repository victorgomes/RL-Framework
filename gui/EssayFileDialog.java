package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import utils.*;

/**
 * Dialog configuration to setup essay
 * */
public class EssayFileDialog extends JDialog implements ActionListener {
	
	public static final long serialVersionUID = 2L;
	
	private JTextField textField = new JTextField();
	private JCheckBox checkBox = new JCheckBox();
	private final JFileChooser fc = new JFileChooser();
	private boolean okClicked = false;
	
	public EssayFileDialog() {			
		super();		
        
        // Setting layout
        JPanel vertPanel = new JPanel();
        vertPanel.setLayout (new BoxLayout(vertPanel, BoxLayout.PAGE_AXIS));

		// Easy panel
        JPanel pEasyFile = new JPanel(new BorderLayout(5, 5));
        pEasyFile.add(new JLabel("Choose Essay XML file: "), BorderLayout.WEST);
        textField.setText("/mnt/Data/Documents/Estudo/University of Sheffield/Research Project/Framework/essays/exp1.xml");
        pEasyFile.add(textField, BorderLayout.CENTER);
        JButton dotButton = new JButton("...");
        dotButton.addActionListener(this);
        pEasyFile.add(dotButton, BorderLayout.EAST);
        vertPanel.add(pEasyFile, BorderLayout.NORTH);
		vertPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
		
		// Check box
		JPanel pCheckBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		checkBox = new JCheckBox("Show graphical representation");
		checkBox.setMnemonic(KeyEvent.VK_S);
		pCheckBox.add(checkBox);
		vertPanel.add(pCheckBox);
		
		// Setting buttons
		JPanel pButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(this);
		okButton.setMnemonic(KeyEvent.VK_K);
		pButtons.add(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		pButtons.add(cancelButton);
		cancelButton.setMnemonic(KeyEvent.VK_C);
		vertPanel.add(pButtons, BorderLayout.SOUTH);
		
		this.getContentPane().add(vertPanel);
		this.pack();

		// Setting dialog
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setTitle("Setting essay...");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500,140);
        this.setResizable(false);
		this.setVisible(true);
	}
	
	public String getEssayFileName() {
		return textField.getText();
	}
	
	public Essay getEssay() {
		return Essay.readEssay(textField.getText());
	}
	
	public boolean isGraphicalRepresented() {
		return checkBox.isSelected();
	}
	
	public boolean isOk() {
		return okClicked;
	}
	
	public EssaySettings getEssaySettings() {
		return new EssaySettings(this.getEssay(), checkBox.isSelected());
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("cancel")) {
            this.setVisible(false);
        } else if(e.getActionCommand().equalsIgnoreCase("ok")) {
            this.setVisible(false);
            okClicked = true;
        } else if(e.getActionCommand().equalsIgnoreCase("...")) {
            int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				textField.setText(fc.getSelectedFile().getPath());
			}
        }
    }	
}
