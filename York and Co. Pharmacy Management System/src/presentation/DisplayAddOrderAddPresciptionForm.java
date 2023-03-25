package presentation;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import databaseDAO.superDAO;
import middleLayer.NegativeInputException;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Orders.*;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class DisplayAddOrderAddPresciptionForm implements ActionListener {
	private static JFrame frame;
	private static JFrame superFrame;
	private static JTextField textFieldPatientID;
	private static JTextField textFieldMercID;
	private static JTextField textFieldQty;
	private static JTextField textFieldRefill;
	private static Inventory inv = Inventory.getInstance();
	private static ListOfOrders listOfOrders = ListOfOrders.getInstance();
	private static JLabel lbNotice;

	public static void displayAddOrder(JFrame previous, String command) {
		superFrame = previous;
		superFrame.setEnabled(false);
		frame = new JFrame("Add order");
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(new Dimension(600,400));
		frame.setPreferredSize(new Dimension(600, 400));
		frame.getContentPane().setLayout(null);
		DisplayAddOrderAddPresciptionForm.createContentsPanel(superFrame, command);
		
		frame.setVisible(true);
	}
	
	public static void createContentsPanel(JFrame superFrame, String command) {
		JPanel panelAddOrder = new JPanel();
		panelAddOrder.setFont(new Font("굴림", Font.PLAIN, 15));
		panelAddOrder.setBounds(12, 10, 562, 343);
		frame.getContentPane().add(panelAddOrder);
		panelAddOrder.setLayout(null);
		createLabels(panelAddOrder, command);
		createButtons(panelAddOrder, command);
		createInputFields(panelAddOrder, command);
		
		
		lbNotice = new JLabel("");
        lbNotice.setHorizontalAlignment(SwingConstants.CENTER);
        lbNotice.setForeground(new Color(255, 0, 0));
        lbNotice.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));
        lbNotice.setBounds(0, 180, 562, 35);
        panelAddOrder.add(lbNotice);
	}
	
	public static void createLabels(JPanel panel, String command) {
		JLabel lblPatientID = new JLabel("HealthCard#");
        lblPatientID.setFont(new Font("굴림", Font.BOLD, 18));
        lblPatientID.setBounds(0, 0, 150, 35);
        panel.add(lblPatientID);
        
        JLabel lblMercID = new JLabel("Merchandise ID");
        lblMercID.setFont(new Font("굴림", Font.BOLD, 18));
        lblMercID.setBounds(0, 45, 150, 35);
        panel.add(lblMercID);
        if(command.equals("AddOrder")) {
	        JLabel lblQty = new JLabel("Qty Bought");
	        lblQty.setFont(new Font("굴림", Font.BOLD, 18));
	        lblQty.setBounds(0, 100, 180, 35);
	        panel.add(lblQty);
        }
        if(command.equals("AddPrescription")) {
	        JLabel lblRefill = new JLabel("Prescribed Refills");
	        lblRefill.setFont(new Font("굴림", Font.BOLD, 18));
	        lblRefill.setBounds(0, 100, 180, 35);
	        panel.add(lblRefill);
        }
	}
	
	public static void createButtons(JPanel panel, String command) {
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("굴림", Font.BOLD, 18));
		btnCancel.setBounds(437, 308, 125, 35);
		btnCancel.addActionListener(new DisplayAddOrderAddPresciptionForm());
		panel.add(btnCancel);
		if(command.equals("AddOrder")) {
			JButton btnAdjustRefill = new JButton("Give refill for a prescription");
	        btnAdjustRefill.setFont(new Font("굴림", Font.BOLD, 18));
	        btnAdjustRefill.setBounds(0, 308, 318, 35);
	        btnAdjustRefill.addActionListener(new DisplayAddOrderAddPresciptionForm());
	        panel.add(btnAdjustRefill);
	
	        JButton btnAddOTCOrder = new JButton("Add OTC order");
	        btnAddOTCOrder.setFont(new Font("굴림", Font.BOLD, 18));
	        btnAddOTCOrder.setBounds(0, 262, 318, 35);
	        btnAddOTCOrder.addActionListener(new DisplayAddOrderAddPresciptionForm());
	        panel.add(btnAddOTCOrder);
		}
        if(command.equals("AddPrescription")) {
			JButton btnAddPresOrder = new JButton("Add prescription form");
	        btnAddPresOrder.setFont(new Font("굴림", Font.BOLD, 18));
	        btnAddPresOrder.setBounds(0, 308, 318, 35);
	        btnAddPresOrder.addActionListener(new DisplayAddOrderAddPresciptionForm());
	        panel.add(btnAddPresOrder);
        }
	}
	
	public static void createInputFields(JPanel panel, String command) {
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
        if(command.equals("AddOrder")) {
	        textFieldQty = new JTextField();
	        textFieldQty.setFont(new Font("굴림", Font.PLAIN, 15));
	        textFieldQty.setBounds(0, 130, 150, 35);
	        panel.add(textFieldQty);
	        textFieldQty.setColumns(10);
        }

        if(command.equals("AddPrescription")) {
	        textFieldRefill = new JTextField();
	        textFieldRefill.setFont(new Font("굴림", Font.PLAIN, 15));
	        textFieldRefill.setBounds(0, 130, 150, 35);
	        panel.add(textFieldRefill);
	        textFieldRefill.setColumns(10);
        }
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
				int medID, qty, refills;
				long patientID;
				// note: patient ID is the same as patient healthcard number
				String stringPatID = textFieldPatientID.getText();
				String stringMedID = textFieldMercID.getText();
				
				if (stringPatID.isEmpty() || stringMedID.isEmpty()) {
					throw new NullPointerException("Patient Health Card Number and Medication ID are both required. Please fill them in.");
				}
				
				// throws numberFormatException if any of them are not long/integers
				patientID = Long.parseLong(stringPatID);
				medID = Integer.parseInt(stringMedID);

				if(e.getActionCommand().equals("Add prescription form")) { // for adding new prescription FORM
					String stringPresRefills = textFieldRefill.getText();
					
					// if any input field is left empty, throw exception
					if (stringPresRefills.isEmpty()) {
						throw new NullPointerException("Prescribed Refills is required. Please fill it in."); 
					}

					// throws numberFormatException if not an integer
					refills = Integer.parseInt(stringPresRefills);

					Prescription newPres = new Prescription(medID, patientID, refills);
					
					listOfOrders.addPresFormToDb( newPres);
					lbNotice.setText("Prescription form successfully added!");

				}
				else {
					String stringQty = textFieldQty.getText();
					
					if (stringQty.isEmpty()) {
						throw new NullPointerException("Qty Bought is required. Please fill it in.");
					}
					
					// throws numberFormatException if qty is not an integer
					qty = Integer.parseInt(stringQty);		
					
					Order newOrder = new Order(medID, patientID, qty);
					
					Boolean lowInStock = null;
					
					if(e.getActionCommand().equals("Add OTC order")) { // for OTC order 
						
						// call method for giving patient OTC order
						lowInStock = listOfOrders.addOrderToDatabase(newOrder);
						lbNotice.setText("OTC order successfully added!");
					}
					
					else if(e.getActionCommand().equals("Give refill for a prescription")) { // Rx ORDER aka refill of an existing prescription
						
						//call method for giving a patient refill order
						lowInStock = listOfOrders.addRefillToDatabase(newOrder);
						lbNotice.setText("Refill (Rx order) successfully added!");
						
					}
					
					if (lowInStock == true) {
						JOptionPane.showMessageDialog(frame,"Medication with ID: " + medID + " is low on stock, please reorder!", "Warning", JOptionPane.WARNING_MESSAGE);
					}
					
				}
				
				
			}
			catch (NullPointerException exception) {
				JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
			}
			catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(frame, "All input values must be integers.", "Invalid input",  JOptionPane.WARNING_MESSAGE);
			}
			catch (NegativeInputException exception) {
				JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input",  JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception exception) {
				JOptionPane.showMessageDialog(frame, exception.getMessage(), "Warning",  JOptionPane.WARNING_MESSAGE);
			}
		}
	}	

}
