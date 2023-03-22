package presentation;

import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.Orders.ListOfOrders;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class DisplaySeeOrders implements ActionListener{
	
	private static JFrame frame;
	private static JFrame superFrame;
	private static JTextField textFieldHCN;
	private static JTextField textFieldTotalSpent;
	private static JTextArea textAreaOutput;

	public static void displaySeeOrders(JFrame previous) {
		superFrame = previous;
		superFrame.setEnabled(false);
		frame = new JFrame("Orders");
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(new Dimension(650, 550));
		frame.setVisible(true);
		
		frame.getContentPane().setLayout(null);
		
		createLabels();
		createButtons();
		createTextArea();
		createTextFields();





	}
	
	public static void createLabels() {
		JLabel lblTotalSpent = new JLabel("Total spent");
		lblTotalSpent.setFont(new Font("굴림", Font.BOLD, 15));
		lblTotalSpent.setBounds(12, 457, 102, 35);
		frame.getContentPane().add(lblTotalSpent);
		
		JLabel lblHCN = new JLabel("HealthCard#");
		lblHCN.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHCN.setFont(new Font("굴림", Font.BOLD, 15));
		lblHCN.setBounds(122, 19, 160, 35);
		frame.getContentPane().add(lblHCN);
	}
	public static void createButtons() {
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("굴림", Font.BOLD, 18));
		btnClose.setBounds(459, 457, 165, 35);
		frame.getContentPane().add(btnClose);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("굴림", Font.BOLD, 18));
		btnSearch.setBounds(459, 19, 165, 35);
		frame.getContentPane().add(btnSearch);
	}
	public static void createTextArea() {
		textAreaOutput = new JTextArea();
		textAreaOutput.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textAreaOutput.setBounds(12, 64, 612, 383);
		//invoke method to retrieve entire list of orders here and
		//textAreaOutput.setText(result);
		frame.getContentPane().add(textAreaOutput);
	}
	
	public static void createTextFields() {
		textFieldHCN = new JTextField();
		textFieldHCN.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldHCN.setBounds(294, 19, 165, 35);
		frame.getContentPane().add(textFieldHCN);
		textFieldHCN.setColumns(10);
		
		textFieldTotalSpent = new JTextField();
		textFieldTotalSpent.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldTotalSpent.setBounds(122, 457, 165, 35);
		frame.getContentPane().add(textFieldTotalSpent);
		textFieldTotalSpent.setColumns(10);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String action = e.getActionCommand();
		if(action.compareToIgnoreCase("Close") == 0) {
			superFrame.setEnabled(true);
			superFrame.toFront();
			frame.dispose();
		}
		else if(action.compareTo("Search") == 0) {
			//invoke searchOrderByPatientID here

			int patientID = Integer.parseInt(textFieldHCN.getText());
			double val = 0;
			try {
				val = ListOfOrders.getInstance().specificPatientMoneySpent(patientID);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			textFieldTotalSpent.setText(Double.toString(val));
		}
	}




	//textFieldTotalSpent.setText(String.format("%.2f", val));
	//LO.specificPatientMoneySpent();


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		displaySeeOrders(new JFrame());

	}


}
