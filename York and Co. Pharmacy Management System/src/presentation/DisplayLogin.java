package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
<<<<<<< Updated upstream

public class DisplayLogin {
=======
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import middleLayer.*;

public class DisplayLogin implements ActionListener {
	private JTextField textField;
	private JPasswordField passwordField;
	AuthenticateUser _authUser;
//	public DisplayLogin() {};
	
//	public DisplayLogin(AuthenticateUser authUser) {
//		_authUser = authUser;
//		displayLogin();
//	}
>>>>>>> Stashed changes

	public void displayLogin() {
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
		
<<<<<<< Updated upstream
=======
		JLabel lblUserAccount = new JLabel("Username");
		lblUserAccount.setFont(new Font("굴림", Font.BOLD, 20));
		lblUserAccount.setBounds(100, 100, 125, 35);
		totalGUI.add(lblUserAccount);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("굴림", Font.BOLD, 20));
		lblPassword.setBounds(100, 150, 125, 35);
		totalGUI.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(225, 100, 225, 35);
		totalGUI.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(225, 150, 225, 35);
		totalGUI.add(passwordField);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(this);
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 25));
		btnNewButton.setBounds(225, 244, 150, 50);
		totalGUI.add(btnNewButton);
		
		JLabel lblTitle = new JLabel("York and Co. Pharmacy Management System");
		lblTitle.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		lblTitle.setBounds(75, 30, 450, 50);
		totalGUI.add(lblTitle);
		
		
		totalGUI.setVisible(true);
>>>>>>> Stashed changes
		return totalGUI;
	}
	
	 public void actionPerformed(ActionEvent e) {
	    	
    	String username = textField.getText();
    	char[] pass = passwordField.getPassword();
//    	System.out.println("TEST");
//    	int usernameInt = Integer.parseInt(username);
//    	int passwordInt = Integer.parseInt(pass);
    	
    	//==_authUser.checkUserValid(username, pass);
    	AuthenticateUser.checkUserValid(username, pass);
    	
    	
	}
<<<<<<< Updated upstream

=======
	
	
>>>>>>> Stashed changes
}
