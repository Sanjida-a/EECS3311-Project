package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;

import middleLayer.MerchandiseInventory.*;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayDescription {
	private static JFrame superFrame;
	private static JFrame frame;
	private static JPanel panel;
	
	public static void displayDescription(JFrame previous, Merchandise merc) {
		superFrame = previous;
		superFrame.setEnabled(false);
		frame = new JFrame();
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(new Dimension(600,400));

		
		frame.setVisible(true);
		
		panel = new JPanel();
		panel.setBounds(12, 10, 586, 361);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		createButton();
		createOutputArea(merc);
		

	}
	
	private static void createButton() {
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("굴림", Font.BOLD, 18));
		btnClose.setBounds(461, 316, 125, 35);
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				superFrame.setEnabled(true);
				superFrame.toFront();
				
			}
			
		});
		panel.add(btnClose);
		
	}
	
	private static void createOutputArea(Merchandise merc) {
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 18));
		textArea.setBounds(0, 0, 586, 293);
		textArea.setText(merc.getDescription());
		textArea.setEditable(false);;
		panel.add(textArea);
		
	}

}
