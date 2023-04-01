package presentation;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import middleLayer.NegativeInputException;
import middleLayer.MerchandiseInventory.*;
import presentation.InitialScreen.InitialScreenPanelAll;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import databaseDAO.superDAO;

public class DisplayModifyMerchandise implements ActionListener{
	private static JFrame superFrame;
	private static JFrame frame;
	private static JTextField textFieldName;
	private static JTextField textFieldType;
	private static JTextField textFieldForm;
	private static JTextField textFieldPrice;
	private static JTextField textFieldMercID;
	private static JTextArea textAreaDescription;
	private static ArrayList<Merchandise> list;
	private static JList<Merchandise> output;
	private static Inventory inv;
	private static JLabel lblNotice;
	
	
	public class DisplayModifyMerchandise implements ActionListener{
	private static JFrame superFrame;
	private static JFrame frame;
	private static JTextField textFieldName;
	private static JTextField textFieldType;
	private static JTextField textFieldForm;
	private static JTextField textFieldPrice;
	private static JTextField textFieldMercID;
	private static JTextArea textAreaDescription;
	private static ArrayList<Merchandise> list;
	private static JList<Merchandise> output;
	private static Inventory inv;
	private static JLabel lblNotice;
	
	
	public static void displayModifyMerchandise(JFrame previous, JList<Merchandise> outputList, ArrayList<Merchandise> currentList) {
		superFrame = previous;
		inv = Inventory.getInstance();
		superFrame.setEnabled(false);
		list = currentList;
		output = outputList;
		frame = new JFrame();
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(new Dimension(700,400));
		DisplayModifyMerchandise.createContentsPanel(superFrame);
		
		frame.setVisible(true);
		
	}
	public static void createContentsPanel(JFrame superFrame) {
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 10, 662, 350);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		createLabels(panel);
		createInputFields(panel);
		createButtons(panel);
		
	}
	
	public static void createLabels(JPanel panel) {
		
		JLabel lblMercID = new JLabel("MerchandiseID");
		lblMercID.setFont(new Font("굴림", Font.BOLD, 18));
		lblMercID.setBounds(0, 0, 145, 35);
		panel.add(lblMercID);
		
		lblNotice = new JLabel("");
		lblNotice.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));
		lblNotice.setForeground(new Color(255, 0, 0));
		lblNotice.setBounds(300,0,350,35);
		panel.add(lblNotice);
	}
	
	public static void createInputFields(JPanel panel) {
		textFieldName = new JTextField();
		textFieldName.setBounds(145, 45, 505, 35);
		panel.add(textFieldName);
		textFieldName.setColumns(10);

		
		textFieldPrice = new JTextField();
		textFieldPrice.setBounds(500, 91, 150, 35);
		panel.add(textFieldPrice);
		textFieldPrice.setColumns(10);
		
		textFieldMercID = new JTextField();
		textFieldMercID.setBounds(145, 0, 150, 35);
		panel.add(textFieldMercID);
		textFieldMercID.setColumns(10);
		
		textAreaDescription = new JTextArea();
		textAreaDescription.setBounds(0, 135, 429, 193);
		panel.add(textAreaDescription);
	}
	
	public static void createButtons(JPanel panel) {

		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("굴림", Font.BOLD, 20));
		btnExit.setBounds(460, 293, 190, 35);
		btnExit.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnExit);
		
		JButton btnChangeName = new JButton("Change name");
		btnChangeName.setHorizontalTextPosition(SwingConstants.LEFT);
		btnChangeName.setFont(new Font("굴림", Font.BOLD, 14));
		btnChangeName.setBounds(0, 45, 140, 35);
		btnChangeName.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnChangeName);
		
		JButton btnChangePrice = new JButton("Change Price");
		btnChangePrice.setFont(new Font("굴림", Font.BOLD, 14));
		btnChangePrice.setBounds(348, 90, 145, 35);
		btnChangePrice.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnChangePrice);
		
		JButton btnChangeDescription = new JButton("Change Description");
		btnChangeDescription.setFont(new Font("굴림", Font.BOLD, 14));
		btnChangeDescription.setBounds(0, 90, 189, 35);
		btnChangeDescription.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnChangeDescription);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
					
		String actionCommand = e.getActionCommand();
		
		String errorMessage = "";
		inv.updateFromDatabase();

			
			if(actionCommand.equals("Exit")) {
				frame.dispose();
				superFrame.setEnabled(true);
				superFrame.toFront();
				
				//list = DisplayInitialScreen.refreshList(Inventory.getInstance(), list);
				InitialScreenPanelAll.displayMercList( list);
			}
			
			else{
				//				errorMessage = "MediciationID is required";
				int _textFieldMercID = 0;
				try {
					String stringMercID = textFieldMercID.getText();
					
					if (stringMercID.isEmpty()) {
						throw new NullPointerException("MediciationID is required. Please enter one");
					}
					_textFieldMercID = Integer.parseInt(stringMercID);
				}
				catch (NullPointerException exception) {
					JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(frame,"Merchandise ID must be an integer", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				
//				Boolean result = true;
				
				if (actionCommand.equals("Change name")) {
					try {
						String _textFieldName = textFieldName.getText().toUpperCase();
						
						if (_textFieldName.isEmpty()) { // ensures a medication name has been entered
							throw new NullPointerException("Name is required. Please enter a name."); 
						}
//						try {
						inv.modifyMedicationName(_textFieldMercID, _textFieldName);
						setNotice("Name changed successfully");
//						}
					}
					catch (NullPointerException exception) {
						setNotice("");
						JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					catch (Exception exception) {
						setNotice("");
						JOptionPane.showMessageDialog(frame,exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					
				}
				
				else if(actionCommand.equals("Change Price")) {
//					errorMessage = "MedicationID and/or Price are needed"; // if exception is thrown because no price has been entered, this is the message printed
					
					try {
						String stringPrice = textFieldPrice.getText();
						
						if (stringPrice.isEmpty()) {
							throw new NullPointerException("Price is required. Please enter a price."); 
						}
						
						double _textFieldPrice = Double.parseDouble(stringPrice); //throws NumberFormatException if not an int/double
//						try {
						inv.modifyMedicationPrice(_textFieldMercID, _textFieldPrice); //just changing price for now, will do name+description once buttons present
//						}
//							catch (Exception e2) {
//								JOptionPane.showMessageDialog(frame,e2.getMessage(),"Invalid input", JOptionPane.WARNING_MESSAGE);
//							}
						setNotice("Price changed successfully");
					} 
					catch (NullPointerException exception) {
						setNotice("");
						JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					catch (NumberFormatException exception) {
						setNotice("");
						JOptionPane.showMessageDialog(frame,"Price must be an integer or double", "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					catch (NegativeInputException exception) {
						setNotice("");
						JOptionPane.showMessageDialog(frame,exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					catch (Exception exception) {
						setNotice("");
						JOptionPane.showMessageDialog(frame,exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					//invoke method(s) for modifying Merchandise here
				/*if (textFieldName.is && textFieldPrice.isEmpty() && textFieldMercID.) {
					throw new Exception(); // ensures a first name, last name and address have been entered				
				}*/
					
				}
				else if(actionCommand.equals("Change Description")) {
					try {
						String _textAreaDescription = textAreaDescription.getText();
						
						if (_textAreaDescription.isEmpty()) { // ensures a description has been entered
							throw new NullPointerException("Description is required. Please enter a description."); 
						}

						inv.modifyMedicationDescription(_textFieldMercID, _textAreaDescription);

						setNotice("Description changed successfully");
					}
					catch (NullPointerException exception) {
						setNotice("");
						JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					catch (Exception exception) {
						setNotice("");
						JOptionPane.showMessageDialog(frame,exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					
				}
				

			}
			
			


		
	}	
	private static void setNotice(String string) {
		lblNotice.setText(string);
	}
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			superDAO.setPassword("Motp1104#");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DisplayModifyMerchandise.displayModifyMerchandise(new JFrame(), output, new ArrayList<Merchandise>());

	}*/
}
