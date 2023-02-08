package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import middleLayer.*;
import javax.swing.SwingConstants;

public class DisplayLogin {
	private JTextField userNameField;
	private JPasswordField passwordField;
	AuthenticateUser _authUser;

    private static int username;
	private static int password;
//	public DisplayLogin() {};
	
//	public DisplayLogin(AuthenticateUser authUser) {
//		_authUser = authUser;
//		displayLogin();
//	}
//>>>>>>> Stashed changes

	public void displayLogin() {
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frmLogin = new JFrame("York and Co. Pharmacy Management System");
        frmLogin.setTitle("Login");
        DisplayLogin background = new DisplayLogin();
        frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmLogin.setContentPane(background.createContentPanel(frmLogin));
        frmLogin.setSize(600, 400);
        frmLogin.setVisible(true);
	}
	
	public JPanel createContentPanel(JFrame frame) {
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
		
		// textField = new JTextField();
		// textField.setBounds(225, 100, 225, 35);
		// totalGUI.add(textField);
		// textField.setColumns(10);
		userNameField = new JTextField();
		userNameField.setBounds(225, 100, 225, 35);
		totalGUI.add(userNameField);
		userNameField.setColumns(20);
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(225, 150, 225, 35);
		totalGUI.add(passwordField);
		
		// JButton btnNewButton = new JButton("Login");
		// btnNewButton.addActionListener(this);
		// btnNewButton.setFont(new Font("굴림", Font.BOLD, 25));
		// btnNewButton.setBounds(225, 244, 150, 50);
		// totalGUI.add(btnNewButton);
		passwordField.setColumns(20);
		

		
		JLabel lblTitle = new JLabel("York and Co. Pharmacy Management System");
		lblTitle.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		lblTitle.setBounds(75, 30, 450, 50);
		totalGUI.add(lblTitle);
		
		
	
	
// 	 public void actionPerformed(ActionEvent e) {
	    	
//     	String username = textField.getText();
//     	char[] pass = passwordField.getPassword();
// //    	System.out.println("TEST");
// //    	int usernameInt = Integer.parseInt(username);
// //    	int passwordInt = Integer.parseInt(pass);
    	
//     	//==_authUser.checkUserValid(username, pass);
//     	AuthenticateUser.checkUserValid(username, pass);
    	
    	
// 	}
//<<<<<<< Updated upstream

//=======
	
	
//>>>>>>> Stashed changes
		
		
//		JButton btnNewButton = new JButton("Login");
//		btnNewButton.addActionListener(this);
//		btnNewButton.setFont(new Font("굴림", Font.BOLD, 25));
//		btnNewButton.setBounds(225, 244, 150, 50);
//		totalGUI.add(btnNewButton);

		
		
		this.loginButton(totalGUI, frame);
		
		
		totalGUI.setVisible(true);
		return totalGUI;
	}
	
	private void loginButton(JPanel totalGUI, JFrame frame) {
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("굴림", Font.BOLD, 25));
		btnLogin.setBounds(225, 244, 150, 50);
		totalGUI.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(validateInput(totalGUI, userNameField, passwordField)) {
					//Minh changed AuthenticateUser to singleton class					
					USER userType = AuthenticateUser.getInstance().checkUserValid(username, password);
					if (userType != null) {
						frame.dispose();
						DisplayInitialScreen screen = new DisplayInitialScreen();
						screen.displayInitialScreen(userType);
						
					}
					else {
						//System.out.println("INCORRECT MATCH");
						// have to add label "username or password is not found in system/ or is invalid here"
						DisplayLogin.authenticationFailed(totalGUI);
					}
				}
			}
		});

	}
	
	private boolean validateInput(JPanel totalGUI, JTextField inputUsername, JPasswordField inputPassword) {
		try {
			username = Integer.parseInt(inputUsername.getText());
			password = Integer.parseInt(new String(inputPassword.getPassword()));
			return true;
		}
		catch(Exception e) {
			DisplayLogin.authenticationFailed(totalGUI);
			return false;
		}
	}
	
	private static void authenticationFailed(JPanel totalGUI) {
		JPanel panelInvalidInput = new JPanel();
		panelInvalidInput.setBounds(100, 195, 350, 35);
		totalGUI.add(panelInvalidInput);
		panelInvalidInput.setLayout(null);
	
		JLabel lblInvalidInput = new JLabel("username or password is invalid");
		lblInvalidInput.setBounds(0, 0, 350, 35);
		panelInvalidInput.add(lblInvalidInput);
	
		lblInvalidInput.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalidInput.setForeground(new Color(255, 0, 0));
		lblInvalidInput.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		totalGUI.add(panelInvalidInput);
	}
	
	
	
	public int getUsername() {
		return username;
	}
	
	public int getPassword() {
		return password;
	}
	

// 	 public void actionPerformed(ActionEvent e) {
//	    
//     	String username = userNameField.getText();
//     	char[] pass = passwordField.getPassword();
//     	System.out.println("TEST");
//     	
//     	if (username!= "1111") {
//     		System.out.println("Incorrect User or Pass");
//     	}
//     	else {
//     		//HOW TO CLOSE FRAME HERE??
////     		dispose();
//     	}
// //    	int usernameInt = Integer.parseInt(username);
// //    	int passwordInt = Integer.parseInt(pass);
//    	
//     	//==_authUser.checkUserValid(username, pass);
// 		
////     	AuthenticateUser.checkUserValid(username, pass);
//    	
//    	
// 	}

//	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		DisplayLogin login = new DisplayLogin();
//		login.displayLogin();
//	}
}

