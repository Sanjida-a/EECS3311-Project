package presentation;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import middleLayer.AuthenticateUser;
import middleLayer.Inventory;
import middleLayer.ListOfOrders;
import middleLayer.Merchandise;
import middleLayer.Order;
import middleLayer.Prescription;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class DisplayAddOrder implements ActionListener {
	private static JFrame frame;
	private static JFrame superFrame;
	private static JTextField textFieldPatientID;
	private static JTextField textFieldMercID;
	private static JTextField textFieldQty;
	private static JTextField textFieldRefill;
	private static Inventory inv = Inventory.getInstance();
	private static ListOfOrders listOfOrders = ListOfOrders.getInstance();


	public static void displayAddOrder(JFrame previous) {
		superFrame = previous;
		superFrame.setEnabled(false);
		frame = new JFrame("Add order");
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(new Dimension(600,400));
		frame.setPreferredSize(new Dimension(600, 400));
		frame.getContentPane().setLayout(null);
		DisplayAddOrder.createContentsPanel(superFrame);
		
		frame.setVisible(true);
	}
	
	public static void createContentsPanel(JFrame superFrame) {
		JPanel panelAddOrder = new JPanel();
		panelAddOrder.setFont(new Font("굴림", Font.PLAIN, 15));
		panelAddOrder.setBounds(12, 10, 562, 343);
		frame.getContentPane().add(panelAddOrder);
		panelAddOrder.setLayout(null);
		createLabels(panelAddOrder);
		createButtons(panelAddOrder);
		createInputFields(panelAddOrder);
		
		textFieldRefill = new JTextField();
		textFieldRefill.setBounds(400, 130, 150, 35);
		panelAddOrder.add(textFieldRefill);
		textFieldRefill.setColumns(10);
		

	}
	
	public static void createLabels(JPanel panel) {
		JLabel lblPatientID = new JLabel("Patient ID");
		lblPatientID.setFont(new Font("굴림", Font.BOLD, 18));
		lblPatientID.setBounds(0, 0, 150, 35);
		panel.add(lblPatientID);
		
		JLabel lblMercID = new JLabel("Merchandise ID");
		lblMercID.setFont(new Font("굴림", Font.BOLD, 18));
		lblMercID.setBounds(0, 45, 150, 35);
		panel.add(lblMercID);
		
		JLabel lblQty = new JLabel("Qty Bought");
		lblQty.setFont(new Font("굴림", Font.BOLD, 18));
		lblQty.setBounds(0, 130, 100, 35);
		panel.add(lblQty);
		
		JLabel lblRefill = new JLabel("Prescribed Refills");
		lblRefill.setFont(new Font("굴림", Font.BOLD, 18));
		lblRefill.setBounds(300, 130, 100, 35);
		panel.add(lblRefill);
	}
	
	public static void createButtons(JPanel panel) {
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("굴림", Font.BOLD, 18));
		btnCancel.setBounds(437, 308, 125, 35);
		btnCancel.addActionListener(new DisplayAddOrder());
		panel.add(btnCancel);
		
		JButton btnAdjustRefill = new JButton("Give refill for a prescription");
		btnAdjustRefill.setFont(new Font("굴림", Font.BOLD, 18));
		btnAdjustRefill.setBounds(300, 308, 125, 35);
		btnAdjustRefill.addActionListener(new DisplayAddOrder());
		panel.add(btnAdjustRefill);
		
		JButton btnAddOrder = new JButton("Add order/prescription");
		btnAddOrder.setFont(new Font("굴림", Font.BOLD, 18));
		btnAddOrder.setBounds(163, 309, 125, 35);
		btnAddOrder.addActionListener(new DisplayAddOrder());
		panel.add(btnAddOrder);
	}
	
	public static void createInputFields(JPanel panel) {
		textFieldPatientID = new JTextField();
		textFieldPatientID.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldPatientID.setBounds(150, 0, 412, 35);
		panel.add(textFieldPatientID);
		textFieldPatientID.setColumns(10);
		
		textFieldMercID = new JTextField();
		textFieldMercID.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldMercID.setBounds(150, 45, 412, 35);
		panel.add(textFieldMercID);
		textFieldMercID.setColumns(10);
		
		textFieldQty = new JTextField();
		textFieldQty.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldQty.setBounds(100, 130, 150, 35);
		panel.add(textFieldQty);
		textFieldQty.setColumns(10);

		
		textFieldRefill = new JTextField();
		textFieldRefill.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldRefill.setBounds(100, 130, 150, 35);
		panel.add(textFieldRefill);
		textFieldRefill.setColumns(10);
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Cancel")) {
			frame.dispose();
			superFrame.setEnabled(true);
			superFrame.toFront();
		}
		else {

			try {
				int patientID, medID, qty, refills;
				
				patientID = Integer.parseInt(textFieldPatientID.getText());
				medID = Integer.parseInt(textFieldMercID.getText());
				qty = Integer.parseInt(textFieldQty.getText()) ;
				
//				if(textFieldRefill.getText().isEmpty()) {
//					refills = 0;
//					System.out.println("IN HERE: refills = " + refills);
//				}
//				else {
//					refills = Integer.parseInt(textFieldRefill.getText());
//				}
//				
				Order newOrder = new Order(medID, patientID, qty);
				
				if(e.getActionCommand().equals("Give refill for a prescription")) { // refill of an existing prescription
					//call method for giving a patient refill order
					listOfOrders.addRefillToDatabase(newOrder);
//					orderToAdd.refillOrderPatient(patientID, medID, qty);
//					orderToAdd.refillAdd();

				
				}
				else if(e.getActionCommand().equals("Add order/prescription")) { // for OTC order and new prescription
					refills = Integer.parseInt(textFieldRefill.getText());
					//call method for adding new order to a patient
					Prescription potentialPrescription = new Prescription(medID, patientID, refills);
					
					listOfOrders.addOrderToDatabase(newOrder, potentialPrescription);
//					Order newOrder = new Order(medID, patientID, qty);
//					Prescription newPres;
//					Merchandise m = inv.searchMerchandiseWithID(medID);
					
//					if (m.getisOTC() == true) {
////						newOrder = ;
//					}
//					else {
////						newOrder = new Order(medID, patientID, qty);
//						newPres = new Prescription(medID, patientID, refills);
//					}
//					
//					orderToAdd.addOrderToPatient(patientID, medID, qty, refills);
//					orderToAdd.Save();



				
					// TODO Auto-generated catch block
//					e1.printStackTrace();
					//System.out.println("no patient or med found");    // DANIEL PLS HANDLE THIS
					//DisplayErrorPopup.displayErrorPopup("No medicine or Patient Found", frame);

				}
			}
			catch (NumberFormatException nFEx) {
				JOptionPane.showMessageDialog(frame, "One or more fields are blank. Please fill it.", "Warning",  JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception ex) {
//				JOptionPane.showMessageDialog(frame, "PatientID, MedID, and Qty must be provided", "Invalid input",  JOptionPane.WARNING_MESSAGE);
				JOptionPane.showMessageDialog(frame, ex.getMessage(), "Warning",  JOptionPane.WARNING_MESSAGE);
			}
		}
	}	
	



	//public static void main(String[] args) {		//for test purpose
		 //TODO Auto-generated method stub
	//	DisplayAddOrder.displayAddOrder(new JFrame());	
	//}

}
