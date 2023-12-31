package presentation;

import middleLayer.Orders.ListOfOrders;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import middleLayer.NegativeInputException;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import javax.swing.JScrollPane;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;




public class DisplaySeeOrders implements ActionListener{
	
	private static JFrame frame;
	private static JFrame superFrame;
	private static JTextField textFieldHCN;
	private static JTextField textFieldTotalSpent;
	private static JTextArea textAreaOutput;
	private static JScrollPane scrollPaneOutput;
	private static JLabel lblTotalSpent;
	private static USER user;



	public static void displaySeeOrders(JFrame previous, USER userType) {
		superFrame = previous;
		superFrame.setEnabled(false);
		user = userType;
		frame = new JFrame("Orders");
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(new Dimension(650, 550));
		frame.setVisible(true);
		
		frame.getContentPane().setLayout(null);

		createLabels(user);
		createButtons();
		createTextArea();
		createTextFields(user);
		frame.revalidate();
	}
	
	private static void createLabels(USER userType) {
		lblTotalSpent = new JLabel("Total spent");
		lblTotalSpent.setFont(new Font("굴림", Font.BOLD, 15));
		lblTotalSpent.setBounds(12, 457, 102, 35);
		if(userType == USER.PHARMACIST) {
			lblTotalSpent.setVisible(false);
		}
		frame.getContentPane().add(lblTotalSpent);
		
		JLabel lblHCN = new JLabel("HealthCard#");
		lblHCN.setHorizontalAlignment(SwingConstants.LEFT);
		lblHCN.setFont(new Font("굴림", Font.BOLD, 15));
		lblHCN.setBounds(20, 20, 160, 35);
		frame.getContentPane().add(lblHCN);
		frame.revalidate();
	}
	private static void createButtons() {
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("굴림", Font.BOLD, 18));
		btnClose.setBounds(459, 457, 165, 35);
		btnClose.addActionListener(new DisplaySeeOrders());
		frame.getContentPane().add(btnClose);
		
		JButton btnSearch = new JButton("See All Orders");
		btnSearch.setFont(new Font("굴림", Font.BOLD, 18));
		btnSearch.setBounds(390, 19, 230, 35);
		btnSearch.addActionListener(new DisplaySeeOrders());
		frame.getContentPane().add(btnSearch);
		frame.revalidate();

		JButton btnSearch2 = new JButton("See Prescription");
		btnSearch2.setFont(new Font("굴림", Font.BOLD, 18));
		btnSearch2.setBounds(390, 60, 230, 35);
		btnSearch2.setActionCommand("See Prescription");
		btnSearch2.addActionListener(new DisplaySeeOrders());
		frame.getContentPane().add(btnSearch2);
		frame.revalidate();
	}
	private static void createTextArea() {
		textAreaOutput = new JTextArea();
		textAreaOutput.setFont(new Font("Monospaced", Font.PLAIN, 15));

		textAreaOutput.setEditable(false);

		
		scrollPaneOutput = new JScrollPane(textAreaOutput);
		scrollPaneOutput.setBounds(12, 100, 612, 350);
		frame.getContentPane().add(scrollPaneOutput);
		frame.revalidate();
	}
	
	private static void createTextFields(USER userType) {
		textFieldHCN = new JTextField();
		textFieldHCN.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldHCN.setBounds(120, 20, 263, 35);
		textFieldHCN.setVisible(true);
	
		frame.add(textFieldHCN);
		textFieldHCN.setColumns(10);
		
		textFieldTotalSpent = new JTextField();
		textFieldTotalSpent.setFont(new Font("굴림", Font.PLAIN, 15));
		textFieldTotalSpent.setBounds(126, 457, 165, 35);
		
		if(userType == USER.PHARMACIST) {
			textFieldTotalSpent.setVisible(false);
		}
		frame.add(textFieldTotalSpent);
		textFieldTotalSpent.setColumns(10);
		frame.repaint();
		frame.revalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if(action.compareToIgnoreCase("Close") == 0) {

			superFrame.setEnabled(true);
			superFrame.toFront();
			frame.dispose();
		}
		
		else {
			ListOfOrders listOfOrdersInstance = ListOfOrders.getInstance();
			
			try {
				// note health card number is same as patient ID
				String stringHCN = textFieldHCN.getText();
				
				if (stringHCN.isEmpty()) 
					throw new NullPointerException("Please enter a health card number!"); // ensures a medication name has been entered
					
				// throws a NumberFormatException if not an integer/long
				long healthCardID = Long.parseLong(stringHCN);
					
				if(action.compareTo("See All Orders") == 0) {
					if(user == USER.OWNER && !textFieldTotalSpent.isVisible() && !lblTotalSpent.isVisible()) {
						textFieldTotalSpent.setVisible(true);
						lblTotalSpent.setVisible(true);
					}
					textFieldTotalSpent.setText("");
        			textAreaOutput.setText("");
					
        			ArrayList<String> resultPurchaseHistory = listOfOrdersInstance.outputOrderHistoryDetails(healthCardID, USER.OWNER);
					
					for(String s : resultPurchaseHistory) {
						textAreaOutput.append(s);
					}

					double val = ListOfOrders.getInstance().specificPatientMoneySpent(healthCardID);

					textFieldTotalSpent.setText(Double.toString(val));
	        	}
		        else if(action.compareTo("See Prescription") == 0){
		        	if(textFieldTotalSpent.isVisible() && lblTotalSpent.isVisible()) {
		        		textFieldTotalSpent.setVisible(false);
		        		lblTotalSpent.setVisible(false);
		        	}
					textAreaOutput.setText("");
					
					ArrayList<String> resultPurchaseHistory = listOfOrdersInstance.outputPresRefill(healthCardID, USER.OWNER);
					
					for(String s : resultPurchaseHistory) {
						textAreaOutput.append(s);
					}
					
					
					textFieldTotalSpent.setText(""); // prescription forms have no correlation to money 
		        }
			} 
			catch (NullPointerException exception) {
				JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
			}
			catch (NegativeInputException exception) {
				JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
			}
			catch (Exception exception) {
				JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
	}

}



