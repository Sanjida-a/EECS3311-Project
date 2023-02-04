package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class DisplayLogin {
	private JTextField textField;
	private JPasswordField passwordField;

	public static void displayLogin() {
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frmLogin = new JFrame("York and Co. Pharmacy Management System");
        frmLogin.setTitle("Login");
        DisplayLogin background = new DisplayLogin();
        frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmLogin.setContentPane(background.createContentPanel());
        frmLogin.setSize(600, 400);
        frmLogin.setVisible(true);
	}
	
	public JPanel createContentPanel() {
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);
		
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
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 25));
		btnNewButton.setBounds(225, 244, 150, 50);
		totalGUI.add(btnNewButton);
		
		JLabel lblTitle = new JLabel("York and Co. Pharmacy Management System");
		lblTitle.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		lblTitle.setBounds(75, 30, 450, 50);
		totalGUI.add(lblTitle);
		
		
		totalGUI.setVisible(true);
		return totalGUI;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		displayLogin();
	}
}
