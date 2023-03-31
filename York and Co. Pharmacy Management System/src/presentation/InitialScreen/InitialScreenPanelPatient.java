package presentation.InitialScreen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import middleLayer.Orders.ListOfOrders;
import middleLayer.Users.ListOfUsers;
import presentation.USER;

public class InitialScreenPanelPatient implements ActionListener {
	private JTextArea textAreaPatientInfo;
	private JTextField textFieldTotalSpent;
	private ListOfUsers listOfUsersInstance;
	private ListOfOrders listOfOrdersInstance;
	private static long usernameLoggedIn;
	private static USER userType;
	private static JFrame frame;
	
	
	private InitialScreenPanelPatient() {
		//to disable the default constructor
	}
	
	public InitialScreenPanelPatient(JFrame currentFrame) {
		frame = currentFrame;
		this.listOfUsersInstance = ListOfUsers.getInstance();
		this.listOfOrdersInstance = ListOfOrders.getInstance();
	}
	public void createPanalVisibleToPatient(JPanel totalGUI) {
		
		JPanel panelVisibleToPatient = new JPanel();
        panelVisibleToPatient.setBounds(1183, 98, 170, 366);
        //totalGUI.add(panelVisibleToPatient);
        panelVisibleToPatient.setLayout(null);
        this.createButtons(panelVisibleToPatient);
        this.createOutputFields(totalGUI);
        


        totalGUI.add(panelVisibleToPatient);
	}
	
	private void createButtons(JPanel panel) {
        JButton btnPurchaseHistory = new JButton("<html>Purchase<br>History</html>");
        btnPurchaseHistory.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPurchaseHistory.setActionCommand("PurchaseHistory");
        btnPurchaseHistory.addActionListener(this);
        btnPurchaseHistory.setFont(new Font("굴림", Font.BOLD, 17));
        btnPurchaseHistory.setBounds(0, 0, 170, 60);
        panel.add(btnPurchaseHistory);
        
        
        
        JButton btnPrescription = new JButton("<html>See prescription<br>Refills</html>");
        btnPrescription.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPrescription.setFont(new Font("굴림", Font.BOLD, 17));
        btnPrescription.setActionCommand("Prescription");
        btnPrescription.setBounds(0, 70, 170, 60);
        btnPrescription.addActionListener(this);
        panel.add(btnPrescription);
        
        JButton btnSeeProfile = new JButton("<html>See<br>profile</html>");
        btnSeeProfile.setFont(new Font("굴림", Font.BOLD, 18));
        btnSeeProfile.setActionCommand("SeeProfile");
        btnSeeProfile.setBounds(0, 140, 170, 60);
        btnSeeProfile.addActionListener(this);
        panel.add(btnSeeProfile);
	}
	
	private void createOutputFields(JPanel panel) {
        JPanel panelOutputAreaForPatient = new JPanel();
        panelOutputAreaForPatient.setBounds(45, 527, 1308, 200);
        panelOutputAreaForPatient.setLayout(null);
        
        
        textAreaPatientInfo = new JTextArea();
        textAreaPatientInfo.setBounds(0, 0, 944, 263);
        textAreaPatientInfo.setEditable(false);

        JScrollPane scrollPanePatientInfo = new JScrollPane(textAreaPatientInfo);
        scrollPanePatientInfo.setBounds(0,0, 944,200);
        panelOutputAreaForPatient.add(scrollPanePatientInfo);
        
        JLabel lblTotalSpent = new JLabel("Total Spent");
        lblTotalSpent.setFont(new Font("굴림", Font.BOLD, 18));
        lblTotalSpent.setBounds(958, 5, 160, 35);
        panelOutputAreaForPatient.add(lblTotalSpent);
        
        textFieldTotalSpent = new JTextField();
        textFieldTotalSpent.setBounds(956, 49, 160, 35);
        panelOutputAreaForPatient.add(textFieldTotalSpent);
        textFieldTotalSpent.setColumns(10);
        panelOutputAreaForPatient.setVisible(true);
        panel.add(panelOutputAreaForPatient);
	}
	
	public static void setUserLoggedIn(long ID) {
		usernameLoggedIn = ID;
	}
	
	
	private void purchaseHistory() {
		
		
		textAreaPatientInfo.setText("");
		textFieldTotalSpent.setText("");
		try {
			ArrayList<String> resultPurchaseHistory = listOfOrdersInstance.outputOrderHistoryDetails(usernameLoggedIn, userType);
			
			for(String s : resultPurchaseHistory) {
				textAreaPatientInfo.append(s);
			}
			
			double resultTotalSpent = listOfOrdersInstance.specificPatientMoneySpent(usernameLoggedIn);
			textFieldTotalSpent.setText(Double.toString(resultTotalSpent));
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void prescription() {
		
		
		textAreaPatientInfo.setText("");
		textFieldTotalSpent.setText("");
		try {
			ArrayList<String> resultPresRefills = listOfOrdersInstance.outputPresRefill(usernameLoggedIn, userType);
			
			for(String s : resultPresRefills) {
				textAreaPatientInfo.append(s);
			}
			
		//	double resultTotalSpent = listOfOrdersInstance.specificPatientMoneySpent(usernameLoggedIn);
		//	textFieldTotalSpent.setText(Double.toString(resultTotalSpent));
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void seeProfile() {
		
		try {
			textAreaPatientInfo.setText("");
			textFieldTotalSpent.setText("");
			ArrayList<String> resultProfile = listOfUsersInstance.specificPatientDetails(usernameLoggedIn, userType);
			
			for(String s : resultProfile) {
				textAreaPatientInfo.append(s);
			}
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String actionCommand = e.getActionCommand();
		if(actionCommand.equalsIgnoreCase("PurchaseHistory")) {
			this.purchaseHistory();
		}
		else if(actionCommand.equalsIgnoreCase("Prescription")) {
			this.prescription();
		}
		else if(actionCommand.equalsIgnoreCase("SeeProfile")) {
			this.seeProfile();
		}
		else {
			//left empty for further expansion
		}

	}

}
