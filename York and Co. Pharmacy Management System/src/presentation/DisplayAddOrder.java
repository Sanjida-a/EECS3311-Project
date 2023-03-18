package presentation;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import middleLayer.MerchandiseInventory.*;
import middleLayer.Orders.*;

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
	private static JLabel lbNotice;

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
		
		
		lbNotice = new JLabel("");
        lbNotice.setHorizontalAlignment(SwingConstants.CENTER);
        lbNotice.setForeground(new Color(255, 0, 0));
        lbNotice.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));
        lbNotice.setBounds(0, 180, 562, 35);
        panelAddOrder.add(lbNotice);
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
        lblQty.setBounds(0, 100, 180, 35);
        panel.add(lblQty);
        
        JLabel lblRefill = new JLabel("Prescribed Refills");
        lblRefill.setFont(new Font("굴림", Font.BOLD, 18));
        lblRefill.setBounds(300, 100, 180, 35);
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
        btnAdjustRefill.setBounds(0, 308, 318, 35);
        btnAdjustRefill.addActionListener(new DisplayAddOrder());
        panel.add(btnAdjustRefill);

        JButton btnAddOTCOrder = new JButton("Add OTC order");
        btnAddOTCOrder.setFont(new Font("굴림", Font.BOLD, 18));
        btnAddOTCOrder.setBounds(0, 262, 318, 35);
        btnAddOTCOrder.addActionListener(new DisplayAddOrder());
        panel.add(btnAddOTCOrder);

		JButton btnAddPresOrder = new JButton("Add prescription order");
        btnAddPresOrder.setFont(new Font("굴림", Font.BOLD, 18));
        btnAddPresOrder.setBounds(0, 216, 318, 35);
        btnAddPresOrder.addActionListener(new DisplayAddOrder());
        panel.add(btnAddPresOrder);
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
        textFieldQty.setBounds(0, 130, 150, 35);
        panel.add(textFieldQty);
        textFieldQty.setColumns(10);

        
        textFieldRefill = new JTextField();
        textFieldRefill.setFont(new Font("굴림", Font.PLAIN, 15));
        textFieldRefill.setBounds(300, 130, 150, 35);
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
				
				Order newOrder = new Order(medID, patientID, qty);
				
				if(e.getActionCommand().equals("Give refill for a prescription")) { // refill of an existing prescription
//					qty = Integer.parseInt(textFieldQty.getText()) ;					
//					Order newOrder = new Order(medID, patientID, qty);
					String refillMess = "Number of refills needed!";
					if(textFieldRefill.getText().isBlank()){
						refills = 0;						
					}
					else{
//						refills = Integer.parseInt(textFieldRefill.getText());
						String errorMess = "Only enter quantity bought for refill order!";
						throw new Exception(errorMess);
					}
					
					//call method for giving a patient refill order
					listOfOrders.addRefillToDatabase(newOrder);
					lbNotice.setText("Refill is given successfully");
				
				}
				else if(e.getActionCommand().equals("Add OTC order")) { // for OTC order and new prescription
//					qty = Integer.parseInt(textFieldQty.getText()) ;					
//					Order newOrder = new Order(medID, patientID, qty);
					if(textFieldRefill.getText().isBlank()){
						refills = 0;
					}
					else{
						refills = Integer.parseInt(textFieldRefill.getText());
					}
					//call method for adding new order to a patient
				//	Prescription potentialPrescription = new Prescription(medID, patientID, refills);
					
					listOfOrders.addOrderToDatabase(newOrder);
					lbNotice.setText("Add OTC successfull");

				}
				else if(e.getActionCommand().equals("Add prescription order")) { // for OTC order and new prescription
					if(textFieldRefill.getText().isBlank()){
						refills = 0;
//						String errorRefill = "Please enter refill numbers!";
//						throw new Exception(errorRefill);
					}
					else{
						refills = Integer.parseInt(textFieldRefill.getText());
					}
					if ((refills == 0 && qty == 0) || (qty > refills)){
						String errPres = "Check quantity and refills!";
						throw new Exception (errPres);
					}

					//call method for adding new order to a patient
					Prescription newPres = new Prescription(medID, patientID, refills);
					
					listOfOrders.addPresOrderToDb( newPres);
					listOfOrders.addPresQuantToDb(newOrder);
					lbNotice.setText("Add prescription successfull");

				}
			}
			catch (NumberFormatException nFEx) {
				JOptionPane.showMessageDialog(frame, "One or more fields are blank. Please fill it.", "Warning",  JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, ex.getMessage(), "Warning",  JOptionPane.WARNING_MESSAGE);
			}
		}
	}	
	

}
