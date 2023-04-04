package presentation;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import databaseDAO.superDAO;
import middleLayer.NegativeInputException;
import middleLayer.Users.*;


import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Cursor;
import java.awt.Component;
import javax.swing.SpringLayout;


public class DisplayPatientManage {
	private static JFrame frame;
	private static JFrame superFrame;
	private static JTextField textFieldSearchKeyword;
	private static JTextField textFieldFName;
	private static JTextField textFieldLName;
	private static JTextField textFieldHCNumber;
	private static JTextField textFieldPhoneNumber;
	private static JTextField textFieldAddress;
	private static JTextField textFieldDOB;
	private static JComboBox<String> comboBox;
	//private static JTextField textFieldAccount;
	//private static JTextField textFieldPassword;\
	private static JLabel lblNotice;
	//private static JTextArea textAreaOutput;
	private static ListOfUsers listOfUsersInstance;
	private static final String[] columns= {				
			String.format("%-20s ", "Name"),
			String.format("%-20s ", "Address"),
			String.format("%-10s ", "Phone Number"),
			String.format("%-10s ", "Healthcard"),
			String.format("%-10s\n", "DoB")};
	private static String[][] patients = {{"", "","","",""}};
	private static JTable table;
	private static JScrollPane scrollPane;
	private static UtilDateModel model; 
	private static JDatePickerImpl datePicker;



	
	public static void displayPatientManage(JFrame previous) {
		listOfUsersInstance = ListOfUsers.getInstance();
		superFrame = previous;
		superFrame.setEnabled(false);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Patient management");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(1200, 700);
		frame.getContentPane().setLayout(null);
		patientsDisplay();
		inputFields();
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("굴림", Font.BOLD, 18));
		btnClose.setBounds(1004, 545, 170, 35);
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
		panelPatientList.setBounds(12, 10, 780, 525);
		frame.getContentPane().add(panelPatientList);
		panelPatientList.setLayout(null);
		
		textFieldSearchKeyword = new JTextField();
		textFieldSearchKeyword.setBounds(0, 0, 396, 35);
		textFieldSearchKeyword.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPatientList.add(textFieldSearchKeyword);
		textFieldSearchKeyword.setColumns(10);
		
		

		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(508, 0, 130, 35);
		btnSearch.setFont(new Font("굴림", Font.BOLD, 18));
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//call method for patient search
				try {
					lblNotice.setText("");
					
					String _textFieldSearchKeyword = textFieldSearchKeyword.getText().toUpperCase();
				
					// throws an exception if search input box is empty
					if (_textFieldSearchKeyword.isEmpty()) {
						throw new NullPointerException(); // ensures something is entered 
					}
					
					
					ArrayList<Patient> searchResult = new ArrayList<Patient>();
					
					searchResult = listOfUsersInstance.searchPatientByName(_textFieldSearchKeyword, (String)comboBox.getSelectedItem());
					displayList(table, searchResult);
					
					if (searchResult.isEmpty()) {
						lblNotice.setText("No patients with that name have been found");
					}
					else {
						lblNotice.setText("See search results below");
					}
				}
				catch(NullPointerException exception) {
					JOptionPane.showMessageDialog(frame, "Search keyword is empty. Input required: type something in the search box", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				catch(Exception exception) { //catch any exceptions and show popup error
					//DisplayErrorPopup.displayErrorPopup("First name, Last name, Address, Phone Number, HealthCardNumber and Date of Birth are required", frame);
					JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
		});
		panelPatientList.add(btnSearch);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(397, 0, 110, 35);
		comboBox.setFont(new Font("굴림", Font.BOLD, 12));
		comboBox.setBorder(new LineBorder(new Color(0, 0, 0)));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"FirstName", "LastName", "FullName"}));
		panelPatientList.add(comboBox);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(650, 0, 130, 35);
		btnRefresh.setFont(new Font("굴림", Font.BOLD, 18));
		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lblNotice.setText("");
				
				textFieldHCNumber.setText("");
				textFieldFName.setText("");
				textFieldLName.setText("");
				textFieldPhoneNumber.setText("");
				textFieldAddress.setText("");
				
				displayList(table, listOfUsersInstance.getAllPatientsList());	//by invoking this method, the list is refreshed.
			//	lblNotice.setText("Patient is added successfully");
			}
			
		});
		panelPatientList.add(btnRefresh);
		
		lblNotice = new JLabel("");
		lblNotice.setBounds(0, 45, 1000, 45);
		panelPatientList.add(lblNotice);
		lblNotice.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNotice.setHorizontalAlignment(SwingConstants.LEFT);
		lblNotice.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));
		lblNotice.setForeground(new Color(255, 0, 0));
		

		patientListField(panelPatientList);
		displayList(table, listOfUsersInstance.getAllPatientsList());
		
	}
	
	private static void inputFields() {
		JPanel panelInputFields = new JPanel();
		panelInputFields.setBounds(804, 10, 370, 525);
		frame.getContentPane().add(panelInputFields);
		panelInputFields.setLayout(null);
		
		JLabel lblFName = new JLabel("First name");
		lblFName.setForeground(new Color(0, 0, 0));
		lblFName.setFont(new Font("굴림", Font.BOLD, 15));
		lblFName.setBounds(0, 295, 110, 35);
		panelInputFields.add(lblFName);
		
		textFieldFName = new JTextField();
		textFieldFName.setBounds(110, 295, 260, 35);
		panelInputFields.add(textFieldFName);
		textFieldFName.setColumns(10);
		
		JLabel lblLName = new JLabel("Last name");
		lblLName.setForeground(new Color(0, 0, 0));
		lblLName.setFont(new Font("굴림", Font.BOLD, 15));
		lblLName.setBounds(0, 343, 110, 35);
		panelInputFields.add(lblLName);
		
		textFieldLName = new JTextField();
		textFieldLName.setBounds(110, 343, 260, 35);
		panelInputFields.add(textFieldLName);
		textFieldLName.setColumns(10);
		
		JLabel lblHCNumber = new JLabel("Healthcard#");
		lblHCNumber.setFont(new Font("굴림", Font.BOLD, 15));
		lblHCNumber.setForeground(new Color(255, 0, 0));
		lblHCNumber.setBounds(0, 91, 110, 35);
		panelInputFields.add(lblHCNumber);
		
		textFieldHCNumber = new JTextField();
		textFieldHCNumber.setBounds(110, 91, 260, 35);
		panelInputFields.add(textFieldHCNumber);
		textFieldHCNumber.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone#");
		lblPhoneNumber.setFont(new Font("굴림", Font.BOLD, 15));
		lblPhoneNumber.setBounds(0, 390, 110, 35);
		panelInputFields.add(lblPhoneNumber);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(110, 390, 260, 35);
		panelInputFields.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("굴림", Font.BOLD, 15));
		lblAddress.setBounds(0, 435, 110, 35);
		panelInputFields.add(lblAddress);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(110, 435, 260, 35);
		panelInputFields.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		JLabel lblDOB = new JLabel("Date of Birth");
		lblDOB.setForeground(new Color(0, 0, 0));
		lblDOB.setFont(new Font("굴림", Font.BOLD, 15));
		lblDOB.setBounds(0, 136, 110, 35);
		panelInputFields.add(lblDOB);
		
		/*textFieldDOB = new JTextField();

		textFieldDOB.setBounds(110, 136, 260, 35);
		textFieldDOB.setText("yyyymmdd");
		textFieldDOB.setFont(new Font("굴림", Font.ITALIC, 14));
		textFieldDOB.setForeground(new Color(128, 128, 128));
		textFieldDOB.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if(textFieldDOB.getText().isBlank() || textFieldDOB.getText().compareTo("yyyymmdd") == 0) {
					textFieldDOB.setText("");
					textFieldDOB.setFont(new Font("굴림", Font.PLAIN, 14));
					textFieldDOB.setForeground(new Color(0, 0, 0));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(textFieldDOB.getText().isBlank()) {
					textFieldDOB.setText("yyyymmdd");
					textFieldDOB.setFont(new Font("굴림", Font.ITALIC, 14));
					textFieldDOB.setForeground(new Color(128, 128, 128));
				}
			}
			
		});
		panelInputFields.add(textFieldDOB);
		textFieldDOB.setColumns(10);*/
		model = new UtilDateModel();
		//LocalDateTime currentDate = LocalDateTime.now();
		model.setValue(new Date());
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());


		datePicker.getJFormattedTextField().setBackground(new Color(255, 255, 255));

		datePicker.setLocation(110, 136);
		datePicker.setSize(260, 35);
		panelInputFields.add(datePicker);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("굴림", Font.BOLD, 17));
		btnAdd.setBounds(200, 480, 170, 35);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//call method for add patient
				try {	
					lblNotice.setText("");
					
					String stringHCNumber = textFieldHCNumber.getText();
					String _textFieldFName = textFieldFName.getText().toUpperCase();
					String _textFieldLName = textFieldLName.getText().toUpperCase();
					String stringPhoneNumber = textFieldPhoneNumber.getText();
					String _textFieldAddress = textFieldAddress.getText().toUpperCase();
					
					if (stringHCNumber.isEmpty() || _textFieldFName.isEmpty() || _textFieldLName.isEmpty() || stringPhoneNumber.isEmpty() || _textFieldAddress.isEmpty()) {
						throw new NullPointerException(); // ensures a HCnumber, first name, last name, phone num and address have been entered
					}
					
					// turned below ints to LONGS
					// int _textFieldPhoneNumber = Integer.parseInt(textFieldPhoneNumber.getText()); // changed to Long
					// int _textFieldHCNumber = Integer.parseInt(textFieldHCNumber.getText()); // changed to Long
					
					// throws NumberFormatException if one of those are not an int/long
					long _textFieldHCNumber = Long.parseLong(stringHCNumber);
					long _textFieldPhoneNumber = Long.parseLong(stringPhoneNumber);
					
					// throws IllegalArgumentException if DOB chosen is after today's date
					int _textFieldDOB = convertDate(datePicker);
					
					listOfUsersInstance.addPatient(_textFieldFName, _textFieldLName, _textFieldAddress, _textFieldPhoneNumber, _textFieldHCNumber, _textFieldDOB);
					displayList(table, listOfUsersInstance.getAllPatientsList());	//by invoking this method, the list is refreshed.
					lblNotice.setText("Patient is added successfully");

					
				}
				catch(NullPointerException exception) {
					JOptionPane.showMessageDialog(frame, "HealthCardNumber, First name, Last name, Phone Number, and Address are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				catch(NumberFormatException exception) {
					JOptionPane.showMessageDialog(frame, "HealthCardNumber and Phone Number must be (whole) numbers", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				catch(IllegalArgumentException exception) { 
					JOptionPane.showMessageDialog(frame, exception.getMessage() + " Please select a valid date that is not in the future","Invalid input",  JOptionPane.WARNING_MESSAGE);
				}
				catch(NegativeInputException exception) {
					JOptionPane.showMessageDialog(frame,exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				catch(SQLException exception) {
					JOptionPane.showMessageDialog(frame,"Duplicated Health Card Number not allowed. A patient with this health card was already found in the system.", "SQL Error", JOptionPane.WARNING_MESSAGE);
				}
				catch(Exception exception) {
					JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				

			}
			
		});
		panelInputFields.add(btnAdd);
		
		JButton btnModify = new JButton("Modify");
		btnModify.setFont(new Font("굴림", Font.BOLD, 18));
		btnModify.setBounds(0, 480, 170, 35);
		btnModify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					lblNotice.setText("");
					
					String stringHCNumber = textFieldHCNumber.getText();
					
					if (stringHCNumber.isEmpty()) {
						throw new NullPointerException(); // ensures a healthcard number is entered
					}
					
					// throws NumberFormatException if not an int/long
					long _textFieldPatientID = Long.parseLong(stringHCNumber);
//					Boolean result;
//					try {
					listOfUsersInstance.modifyPatientDetails(_textFieldPatientID, textFieldFName, textFieldLName, textFieldPhoneNumber, textFieldAddress);
						
//					if (result == false) {
						// popup that says patient doesn't exist
						//System.out.println("patient doesn't exist. try again");
//						JOptionPane.showMessageDialog(frame,"The Patient does not exist", "Invalid input", JOptionPane.WARNING_MESSAGE);
//					}
						
					displayList(table, listOfUsersInstance.getAllPatientsList() );
					lblNotice.setText("Patient is modified successfully. See below");
//					}
//					catch (Exception e2) {
//						JOptionPane.showMessageDialog(frame,e2.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
//					}
										
				}
				catch(NullPointerException exception) {
					JOptionPane.showMessageDialog(frame, "HealthCardNumber is required. Please fill it in", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				catch(NumberFormatException exception) {
					JOptionPane.showMessageDialog(frame, "HealthCardNumber and phone number must be integers", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				catch(NegativeInputException exception) {
					JOptionPane.showMessageDialog(frame,exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				catch (Exception exception) {
					if (exception.getMessage().compareTo("Unsuccessful") == 0) {
						lblNotice.setText("Modification unsuccessful. No patient with such health card number exists in the system.");
					}
					else {
						JOptionPane.showMessageDialog(frame,exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
			
		});
		panelInputFields.add(btnModify);
		
		JLabel lblNewLabel = new JLabel("The fields below are modifiable");
		lblNewLabel.setForeground(new Color(128, 128, 128));
		lblNewLabel.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel.setBounds(0, 250, 370, 35);
		panelInputFields.add(lblNewLabel);
		

	}
	private static void patientListField(JPanel panel) {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 100, 780, 425);
		panel.add(scrollPane);
		table = new JTable();
		
		table.setFont(new Font("Monospaced", Font.PLAIN, 14));


		
	}
	
	private static void displayList(JTable output, ArrayList<Patient> pList){	//if this method is invoked at the end of every  button click operation,													

		output.removeAll();
		
		patients = new String[pList.size()][columns.length]; 
		for(int i = 0; i < pList.size(); i++) {
			
			patients[i][0] = String.format("%-20s", pList.get(i).getFirstName() + " " + pList.get(i).getLastName());
			patients[i][1] = String.format("%-20s", pList.get(i).getAddress());
			patients[i][2] = String.format("%-10s", pList.get(i).getPhoneNum());
			patients[i][3] = String.format("%-10s", pList.get(i).getHealthCardNum());
			patients[i][4] = String.format("%-10s", pList.get(i).getDateOfBirth());

		}

		table = new JTable(patients, columns);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		
	}
	
	public static int convertDate(JDatePickerImpl datePicker) throws Exception {
		Date currentDate = new Date();
		Date selectedDate = ((Date) datePicker.getModel().getValue());
		if(selectedDate.after(currentDate)) {
			throw new IllegalArgumentException("Invalid date is selected.");
		}
		//Date date = ((Date) datePicker.getModel().getValue());
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
		String stringDate = fm.format(selectedDate);

		return Integer.parseInt(stringDate);
	}
	
}
