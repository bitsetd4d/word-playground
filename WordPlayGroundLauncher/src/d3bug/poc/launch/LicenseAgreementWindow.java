package d3bug.poc.launch;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class LicenseAgreementWindow extends JFrame {
	
	private JLabel label;
	private JTextArea textArea;

	public static void main(String[] args) {
		new LicenseAgreementWindow().open();
	}
	
	public void open() {
		setSize(800, 650);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("License Agreement");
		createControls();
		setVisible(true);
	}

	private void createControls() {
		setLayout(new GridBagLayout());
		label = new JLabel();
		label.setText("LICENSE AGREEMENT");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 10;
		gbc.gridheight = 2;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(10,20,10,20);
		add(label,gbc);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText("asdkjadksljlk");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		//gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 10;
		gbc.gridheight = 9;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0,20,20,20);
		add(textArea,gbc);
	}

}
