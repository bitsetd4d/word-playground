package d3bug.kidswing;

import java.applet.AudioClip;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import d3bug.audio.AudioClipAccess;

public class SpeakingButton extends JButton {
	
	private AudioClip audioClip;
	
	public SpeakingButton() {
		super();
		addMouseListener(new MyMouseListener());
	}

	public SpeakingButton(Action arg0) {
		super(arg0);
	}

	public SpeakingButton(Icon arg0) {
		super(arg0);
	}

	public SpeakingButton(String arg0, Icon arg1) {
		super(arg0, arg1);
	}

	public SpeakingButton(String arg0) {
		super(arg0);
	}
	
	public void setAudioClip(String audio) {
		audioClip = AudioClipAccess.getInstance().getAudioClip(audio);
	}

	private void startPlaying() {
		if (audioClip == null) return;
		audioClip.play();
	}
	
	private void stopPlaying() {
		if (audioClip == null) return;
		audioClip.stop();
	}
	
	
	private class MyMouseListener implements MouseListener {
		public void mouseEntered(MouseEvent arg0) {
			startPlaying();
		}
		public void mouseExited(MouseEvent arg0) {
			stopPlaying();
		}

		public void mouseClicked(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
}

