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
import middleLayer.Order;

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
	
    private static int patientID;
	private static int medID;

	private static JTextField textFieldRefill;
	private static JTextField textFieldIsOTC;

	private static int qty;
	private static int refills;


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
		
		JLabel lblIsOTC = new JLabel("isOTC");
		lblIsOTC.setFont(new Font("굴림", Font.BOLD, 18));
		lblIsOTC.setBounds(0, 200, 100, 35);
		panelAddOrder.add(lblIsOTC);
		
		textFieldIsOTC = new JTextField();
		textFieldIsOTC.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldIsOTC.setBounds(100, 200, 150, 35);
		panelAddOrder.add(textFieldIsOTC);
		textFieldIsOTC.setColumns(10);

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
		
		JLabel lblQty = new JLabel("Qty");
		lblQty.setFont(new Font("굴림", Font.BOLD, 18));
		lblQty.setBounds(0, 130, 100, 35);
		panel.add(lblQty);
		
		JLabel lblRefill = new JLabel("Refill");
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
		
		JButton btnAdjustRefill = new JButton("Give refill");
		btnAdjustRefill.setFont(new Font("굴림", Font.BOLD, 18));
		btnAdjustRefill.setBounds(300, 308, 125, 35);
		btnAdjustRefill.addActionListener(new DisplayAddOrder());
		panel.add(btnAdjustRefill);
		
		JButton btnAddOrder = new JButton("Add order");
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
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Cancel")) {
			frame.dispose();
			superFrame.setEnabled(true);
			superFrame.toFront();
		}
		else if(e.getActionCommand().equals("Give refill")) {
			//call method for giving a patient refill order
			//System.out.println("invoking give_refill()");	//delete me
		}
		else if(e.getActionCommand().equals("Add order")) {
			//call method for adding new order to a patient
			//System.out.println("invoking add_order()");	//delete me
			patientID = Integer.parseInt(textFieldPatientID.getText());
			medID = Integer.parseInt(textFieldMercID.getText());
			qty = Integer.parseInt(textFieldQty.getText()) ;
			refills = Integer.parseInt(textFieldQty.getText()) ;
			
			Order orderToAdd = new Order();
			try {
//				orderToAdd.addOrderToPatient(patientID, medID, qty, price, refills, true);
				orderToAdd.addOrderToPatient(patientID, medID, qty, true, 5);
				orderToAdd.Save();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//System.out.println("no patient or med found");    // DANIEL PLS HANDLE THIS
				//DisplayErrorPopup.displayErrorPopup("No medicine or Patient Found", frame);
				JOptionPane.showMessageDialog(frame,"No medicine or Patient Found", "Invalid input", JOptionPane.WARNING_MESSAGE);
				
			}
		}
	}	
	
//	private static boolean validateInput(JTextField textFieldPatientID, JTextField textFieldMercID) {
//		try {
//			patientID = Integer.parseInt(textFieldPatientID.getText());
//			medID = Integer.parseInt(textFieldMercID.getText());
//			
//			return true;
//		}
//		catch(Exception e) {
//			System.out.println("no patient or med found");           // daniel please output warning for this
//			return false;
//		}
//	}


	//public static void main(String[] args) {		//for test purpose
		 //TODO Auto-generated method stub
	//	DisplayAddOrder.displayAddOrder(new JFrame());	
	//}

}
