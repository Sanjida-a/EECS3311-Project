package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayLogin {

	public static void displayLogin() {
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("York and Co. Pharmacy Management System");
        DisplayLogin background = new DisplayLogin();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(background.createContentPanel());
        frame.setSize(600, 400);
        frame.setVisible(true);
	}
	
	public JPanel createContentPanel() {
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);
		
		return totalGUI;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		displayLogin();
	}

}
