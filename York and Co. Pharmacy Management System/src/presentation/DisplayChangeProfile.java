package presentation;
/*
 * this class is for the next iteration. it is not used for now
 * */
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import databaseDAO.superDAO;
import middleLayer.Users.ListOfUsers;
import middleLayer.Users.Patient;

import javax.swing.JButton;

public class DisplayChangeProfile implements ActionListener {
	private static JTextField textFieldFirstName;
	private static JTextField textFieldLastName;
	private static JTextField textFieldPhoneNumber;
	private static JTextField textFieldAddress;
	private static JFrame frame;
	private static JFrame superFrame;
	public static void displayChangeProfile(JFrame previous, int ID, Patient patient) {
		superFrame = previous;
		superFrame.setEnabled(false);
		frame = new JFrame("Change Profile");
		frame.setSize(new Dimension(425, 400));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel totalGUI = new JPanel();
		totalGUI.setBounds(12, 10, 375, 325);
		totalGUI.setLayout(null);
		createContentPanel(totalGUI, ID, patient);
		frame.getContentPane().add(totalGUI);
	}
	
	private static void createContentPanel(JPanel totalGUI, int ID, Patient patient) {
		createLabels(totalGUI);
		createTextFields(totalGUI);
		setTexts(patient);
		createButtons(totalGUI);
	}
	
	private static void createLabels(JPanel totalGUI) {
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("굴림", Font.BOLD, 16));
		lblFirstName.setBounds(0, 0, 125, 35);
		totalGUI.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("굴림", Font.BOLD, 16));
		lblLastName.setBounds(0, 45, 125, 35);
		totalGUI.add(lblLastName);
		
		JLabel lblPhoneNumber = new JLabel("Phone#");
		lblPhoneNumber.setFont(new Font("굴림", Font.BOLD, 16));
		lblPhoneNumber.setBounds(0, 90, 125, 35);
		totalGUI.add(lblPhoneNumber);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("굴림", Font.BOLD, 16));
		lblAddress.setBounds(0, 135, 125, 35);
		totalGUI.add(lblAddress);
	}
	
	private static void createTextFields(JPanel totalGUI) {
		textFieldFirstName = new JTextField();
		textFieldFirstName.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldFirstName.setBounds(125, 0, 250, 35);
		totalGUI.add(textFieldFirstName);
		textFieldFirstName.setColumns(15);
		
		textFieldLastName = new JTextField();
		textFieldLastName.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldLastName.setBounds(125, 45, 250, 35);
		totalGUI.add(textFieldLastName);
		textFieldLastName.setColumns(15);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(125, 90, 250, 35);
		totalGUI.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(125, 135, 250, 35);
		totalGUI.add(textFieldAddress);
		textFieldAddress.setColumns(40);
	}
	
	private static void createButtons(JPanel totalGUI) {
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("굴림", Font.BOLD, 18));
		btnCancel.setBounds(250, 280, 125, 35);
		btnCancel.addActionListener(new DisplayChangeProfile());
		totalGUI.add(btnCancel);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("굴림", Font.BOLD, 18));
		btnConfirm.setBounds(113, 281, 125, 35);
		btnConfirm.addActionListener(new DisplayChangeProfile());
		totalGUI.add(btnConfirm);
	}
	
	private static void setTexts(Patient patient) {
		textFieldFirstName.setText(patient.getFirstName());
		textFieldLastName.setText(patient.getLastName());
		textFieldPhoneNumber.setText(String.valueOf(patient.getPhoneNum()));
		textFieldAddress.setText(patient.getAddress());
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Confirm")) {
			//invoke modify detail method
		}
		superFrame.setEnabled(true);
		superFrame.toFront();
		frame.dispose();
	}
	/*public static void main(String[] args) {
		try {
			superDAO.setPassword("Motp1104#");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DisplayChangeProfile.displayChangeProfile(new JFrame(), 1, ListOfUsers.getInstance().getAllPatientsList().get(0));
	}*/

}
