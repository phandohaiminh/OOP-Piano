package ApplicationController;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import SoundManager.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PianoController {
	 private SoundManager sm = new SoundManager();
	 private JFrame parentFrame;
	 private Scene scene;
	 
	 @FXML
	 private Label volumeLabel,octaveLabel;
	 
	 @FXML
	 private Button btnA, btnS, btnD, btnF, btnG,
	 	btnH, btnJ, btnW, btnE, btnT, btnY, btnU;
	 
	 public Button[] all_button;
	 
	 private String all_keys = "AWSEDFTGYHUJ", black_keys = "WETYU";
	 private boolean[] key_on = new boolean[12];
	
	public PianoController(JFrame parentFrame) {
		super();
		this.parentFrame = parentFrame;
	}
	
	@FXML
	public void initialize(){ 
		all_button = new Button[12];
		all_button[0] = btnA;
		all_button[1] = btnW;
		all_button[2] = btnS;
		all_button[3] = btnE;
		all_button[4] = btnD;
		all_button[5] = btnF;
		all_button[6] = btnT;
		all_button[7] = btnG;
		all_button[8] = btnY;
		all_button[9] = btnH;
		all_button[10] = btnU;
		all_button[11] = btnJ;
		for (Button b : all_button) {
			b.setOnMousePressed(e -> handleButtonPressed(e));
			b.setOnMouseReleased(e -> handleButtonReleased(e));
		}
	}
	
	public void sendParameterScene(Scene s) {
		scene = s;
		scene.setOnKeyPressed(e -> handleKeyPressed(e));
		scene.setOnKeyReleased(e -> handleKeyReleased(e));
	}
	
	private void handleButtonPressed(MouseEvent e) {
		Button tmp = ((Button) e.getSource());
		int index = all_keys.indexOf(tmp.getText().charAt(0));
		if (key_on[index] == false) {
			key_on[index] = true;
			sm.noteOn(index);
			tmp.setStyle("-fx-background-color: Green; -fx-border-color: Black");
		}
	}
	
	private void handleButtonReleased(MouseEvent e) {
		Button tmp = ((Button) e.getSource());
		int index = all_keys.indexOf(tmp.getText().charAt(0));
		if (key_on[index] == true) {
			key_on[index] = false;
			sm.noteOff(index);
			if (black_keys.indexOf(tmp.getText().charAt(0)) == -1)
				tmp.setStyle("-fx-background-color: White; -fx-border-color: Black");
			else 
				tmp.setStyle("-fx-background-color: Black; -fx-border-color: Black");
		}
	}
	
	private void handleKeyPressed(KeyEvent e) {
		String tmp_str = e.getCode().toString();
		if (tmp_str.length() == 1) {
			char tmp_chr = tmp_str.charAt(0);
			int index = all_keys.indexOf(tmp_chr);
			if (index != -1) 
				if (key_on[index] == false) {
					key_on[index] = true;
					sm.noteOn(index);
					all_button[index].setStyle("-fx-background-color: Green; -fx-border-color: Black");
				}
		}
	}
	
	private void handleKeyReleased(KeyEvent e) {
		String tmp_str = e.getCode().toString();
		if (tmp_str.length() == 1) {
			char tmp_chr = tmp_str.charAt(0);
			int index = all_keys.indexOf(tmp_chr);
			if (index != -1) {
				if (key_on[index] == true) {
					key_on[index] = false;
					sm.noteOff(index);
					if (black_keys.indexOf(tmp_chr) == -1)
						all_button[index].setStyle("-fx-background-color: White; -fx-border-color: Black");
					else 
						all_button[index].setStyle("-fx-background-color: Black; -fx-border-color: Black");
				}
			}
		}
	}
	
	@FXML
	private void showHelpDialog() {
        JOptionPane.showMessageDialog(parentFrame,
            "Virtual Piano Help:\n" +
            "Use the keyboard keys ASDFGHJ for white piano keys.\n" +
            "Use the keyboard keys WE TYU for black piano keys.\n" +
            "Press 0-8 to change octaves, +/- to adjust the current octave.\n" +
            "Press the keys to play notes.",
            "Help", JOptionPane.INFORMATION_MESSAGE);
    }
	
	@FXML
	void volumeUpPressed(ActionEvent e) {
		sm.increaseVolume(10);
		volumeLabel.setText(sm.getVolume() + "");
	}
	
	@FXML
	void volumeDownPressed(ActionEvent e) {
		sm.increaseVolume(-10);
		volumeLabel.setText(sm.getVolume() + "");
	}
	
	@FXML
	void octaveUpPressed(ActionEvent e) {
		sm.increaseOctave(1);
		octaveLabel.setText(sm.getOctave() + "");
	}
	
	@FXML
	void octaveDownPressed(ActionEvent e) {
		sm.increaseOctave(-1);
		octaveLabel.setText(sm.getOctave() + "");
	}
}
