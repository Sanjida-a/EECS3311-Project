package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.SwingConstants;

import databaseDAO.superDAO;
import presentation.InitialScreen.DisplayInitialScreen;

import javax.swing.JButton;

public class DisplaySQLLogin implements ActionListener {
	private static JPasswordField passwordField;
	private static JLabel lblNotice;
	private static JFrame frame;
	
	public void displaySQLLogin() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(new Rectangle(0, 0, 400, 300));
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		this.createInputFields();
		this.createButtons();
		this.createLabels();
		frame.setVisible(true);
		
	}
		

	
	public void createInputFields() {
		passwordField = new JPasswordField();
		passwordField.setBounds(149, 47, 225, 35);
		frame.getContentPane().add(passwordField);
	}
	
	public void createLabels() {
		JLabel lblPassword = new JLabel("SQL password");
		lblPassword.setFont(new Font("굴림", Font.BOLD, 15));
		lblPassword.setBounds(12, 47, 125, 35);
		frame.getContentPane().add(lblPassword);
		
		lblNotice = new JLabel();
		lblNotice.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotice.setFont(new Font("굴림", Font.ITALIC, 13));
		lblNotice.setForeground(new Color(255, 0, 0));
		lblNotice.setBounds(12, 118, 362, 35);
		frame.getContentPane().add(lblNotice);
	}
	
	public void createButtons() {
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("굴림", Font.BOLD, 15));
		btnLogin.setBounds(50, 189, 120, 35);
		btnLogin.addActionListener(new DisplaySQLLogin());
		frame.getContentPane().add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("굴림", Font.BOLD, 16));
		btnExit.setBounds(210, 189, 120, 35);
		btnExit.addActionListener(new DisplaySQLLogin());
		frame.getContentPane().add(btnExit);

	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getActionCommand().equals("Exit")) {
				System.exit(0);
			}
			else {
				//invoke method for password validation
				String _passwordField = new String(passwordField.getPassword());
				if (_passwordField.isEmpty()) throw new Exception(); // ensures a medication name has been entered
				

				superDAO.setPassword(_passwordField);
				frame.dispose();
				//executed after validation succeed
				//DisplayInitialScreen.createPanels(null);
				DisplayInitialScreen.displayInitialScreen(USER.GUEST);
			}
		}catch(Exception ex) {
			lblNotice.setText("Password is invalid");
		}
	}

	/*public static void main(String[] args) {
	
		DisplaySQLLogin screen = new DisplaySQLLogin();
		screen.displaySQLLogin();
	}*/


}
