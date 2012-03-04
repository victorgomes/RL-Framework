import javax.swing.*;
import gui.*;

public class Main {
	
	public static void main (String arg []) {
		try {
            // Set system look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			// Get essay file name
			EssayFileDialog dialog = new EssayFileDialog();
			if (!dialog.isOk()) {
				dialog.dispose();
				return;
			}
			
			// Run log and simulation
			LogWindow logWindow = new LogWindow();
			logWindow.simulate(dialog.getEssaySettings());
						
			dialog.dispose();
		} 
		catch (Exception ex) {
		   ex.printStackTrace();
		}		
	}
}
