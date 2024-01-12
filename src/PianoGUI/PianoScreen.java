package PianoGUI;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ApplicationController.PianoController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PianoScreen extends JFrame {
	private JFrame jFrame = this;
	
	public PianoScreen() {
		super();
		
		JFXPanel fxPanel = new JFXPanel();
		this.add(fxPanel);
		
		this.setTitle("Piano");
		this.setVisible(true);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Confirm before exiting the application
                if (JOptionPane.showConfirmDialog(jFrame,
                    "Are you sure you want to exit?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
		});
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					FXMLLoader loader = new FXMLLoader(
							getClass().getResource("/Piano.fxml"));
					PianoController controller = new PianoController(jFrame);
					loader.setController(controller);
					Parent root = loader.load();
					
					Scene scene = new Scene(root);
					fxPanel.setScene(scene); 
					
					controller.sendParameterScene(scene);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new PianoScreen();
	}
}
