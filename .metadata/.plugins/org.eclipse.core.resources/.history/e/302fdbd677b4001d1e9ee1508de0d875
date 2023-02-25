package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

import middleLayer.ListOfUsers;
import middleLayer.Patient;
import middleLayer.User;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DisplayPatientManage {
	private static JFrame frame;
	private static JTextField textFieldSearchKeyword;
	private static JTextField textFieldFName;
	private static JTextField textFieldLName;
	private static JTextField textFieldHCNumber;
	private static JTextField textFieldPhoneNumber;
	private static JTextField textFieldAddress;
	private static JTextField textFieldDOB;
	private static JTextField textFieldAccount;
	private static JTextField textFieldPassword;
	private static JList<String> patientList;
	
	public static void displayPatientManage(JFrame superFrame) {
		superFrame.setEnabled(false);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Patient management");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(800, 500);
		frame.getContentPane().setLayout(null);
		patientsDisplay();
		inputFields();
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("굴림", Font.BOLD, 18));
		btnClose.setBounds(605, 420, 170, 35);
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				superFrame.toFront();
				superFrame.setEnabled(true);
			}
			
		});
		frame.getContentPane().add(btnClose);
		frame.setVisible(true);
		
		
		
	}
	
	private static void patientsDisplay() {
		JPanel panelPatientList = new JPanel();
		panelPatientList.setBounds(12, 10, 380, 400);
		frame.getContentPane().add(panelPatientList);
		panelPatientList.setLayout(null);
		
		textFieldSearchKeyword = new JTextField();
		textFieldSearchKeyword.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldSearchKeyword.setBounds(0, 0, 250, 35);
		panelPatientList.add(textFieldSearchKeyword);
		textFieldSearchKeyword.setColumns(10);
		
		patientListField(panelPatientList);

		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("굴림", Font.BOLD, 18));
		btnSearch.setBounds(250, 0, 130, 35);
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//call method for patient search
			}
			
		});
		panelPatientList.add(btnSearch);
		
		
	}
	
	private static void inputFields() {
		JPanel panelInputFields = new JPanel();
		panelInputFields.setBounds(404, 10, 370, 400);
		frame.getContentPane().add(panelInputFields);
		panelInputFields.setLayout(null);
		
		JLabel lblFName = new JLabel("First name");
		lblFName.setForeground(new Color(0, 0, 0));
		lblFName.setFont(new Font("굴림", Font.BOLD, 15));
		lblFName.setBounds(0, 35, 110, 35);
		panelInputFields.add(lblFName);
		
		textFieldFName = new JTextField();
		textFieldFName.setBounds(110, 35, 260, 35);
		panelInputFields.add(textFieldFName);
		textFieldFName.setColumns(10);
		
		JLabel lblLName = new JLabel("Last name");
		lblLName.setForeground(new Color(0, 0, 0));
		lblLName.setFont(new Font("굴림", Font.BOLD, 15));
		lblLName.setBounds(0, 75, 110, 35);
		panelInputFields.add(lblLName);
		
		textFieldLName = new JTextField();
		textFieldLName.setBounds(110, 75, 260, 35);
		panelInputFields.add(textFieldLName);
		textFieldLName.setColumns(10);
		
		JLabel lblNotice = new JLabel("");
		lblNotice.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNotice.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotice.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));
		lblNotice.setForeground(new Color(255, 0, 0));
		lblNotice.setBounds(0, 0, 370, 25);
		panelInputFields.add(lblNotice);
		
		JLabel lblHCNumber = new JLabel("Healthcard#");
		lblHCNumber.setFont(new Font("굴림", Font.BOLD, 15));
		lblHCNumber.setForeground(new Color(0, 0, 0));
		lblHCNumber.setBounds(0, 115, 110, 35);
		panelInputFields.add(lblHCNumber);
		
		textFieldHCNumber = new JTextField();
		textFieldHCNumber.setBounds(110, 115, 260, 35);
		panelInputFields.add(textFieldHCNumber);
		textFieldHCNumber.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone#");
		lblPhoneNumber.setFont(new Font("굴림", Font.BOLD, 15));
		lblPhoneNumber.setBounds(0, 155, 110, 35);
		panelInputFields.add(lblPhoneNumber);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(110, 155, 260, 35);
		panelInputFields.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("굴림", Font.BOLD, 15));
		lblAddress.setBounds(0, 195, 110, 35);
		panelInputFields.add(lblAddress);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(110, 195, 260, 35);
		panelInputFields.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		JLabel lblDOB = new JLabel("Date of Birth");
		lblDOB.setFont(new Font("굴림", Font.BOLD, 15));
		lblDOB.setBounds(0, 235, 110, 35);
		panelInputFields.add(lblDOB);
		
		textFieldDOB = new JTextField();
		textFieldDOB.setBounds(110, 235, 260, 35);
		panelInputFields.add(textFieldDOB);
		textFieldDOB.setColumns(10);
		
		JLabel lblAccount = new JLabel("Account");
		lblAccount.setFont(new Font("굴림", Font.BOLD, 15));
		lblAccount.setBounds(0, 275, 110, 35);
		panelInputFields.add(lblAccount);
		
		textFieldAccount = new JTextField();
		textFieldAccount.setBounds(110, 275, 260, 35);
		panelInputFields.add(textFieldAccount);
		textFieldAccount.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("굴림", Font.BOLD, 15));
		lblPassword.setBounds(0, 315, 110, 35);
		panelInputFields.add(lblPassword);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(110, 315, 260, 35);
		panelInputFields.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("굴림", Font.BOLD, 17));
		btnAdd.setBounds(200, 360, 170, 35);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//call method for add patient
				
				//displayList(patientList);		//by calling this as the last instruction, it refreshes the list
			}
			
		});
		panelInputFields.add(btnAdd);
		

	}
	private static void patientListField(JPanel panel) {
		patientList = new JList<String>();
		patientList.setBorder(new LineBorder(new Color(0, 0, 0)));
		patientList.setBounds(0, 62, 380, 338);
		displayList(patientList);
		panel.add(patientList);
	}
	
	private static void displayList(JList<String> list) {	
		ArrayList<User> userList= ListOfUsers.getInstance().getAllUsersList();
		ArrayList<Patient> patientList = new ArrayList<Patient>();
		//patientList.add(new Patient("Smith", "John", "5324 yonge St", 1112223333, 1111122222, 11111222)); //delete me
		DefaultListModel<String> model = new DefaultListModel<String>();
		Patient temp;
		list.removeAll();
		for(User u : userList) {
			if(u instanceof Patient) {
				temp = (Patient) u;
				model.addElement(temp.getFirstName() + ", " + temp.getLastName());
			}
			//System.out.println(p.toString() + " entry");	//delete me. test purpose
		}

		list.setModel(model);
	}
	
	
	//public static void main(String[] args) {	//for test purpose
	//	DisplayPatientManage.displayPatientManage(new JFrame());
	//}
}
