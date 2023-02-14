package presentation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.JProgressBar;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;

import middleLayer.AuthenticateUser;
import middleLayer.Merchandise;
import middleLayer.Owner;
import middleLayer.Patient;
import middleLayer.Inventory;
import middleLayer.MERCHANDISE_FORM;
import middleLayer.MERCHANDISE_TYPE;

import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;



public class DisplayInitialScreen {
	private static JTextField inputFieldName;
	private static JTextField inputFieldHCN;
	private static JTextField inputFieldType;
	private static JTextField inputFieldForm;
	private static JTextField inputFieldQty;
	private static JTextField inputFieldPrice;
	private static JTextField inputKeyword;
	private static JFrame frame;
	
	private static String name;
	private static int username;
	private static String type;
	private static String form;
	private static int qty;
	private static double price;
	private static boolean isOTC;
	private static String searchKeyword;
	private static USER userType;
	
	JTextArea textboxOutput;
	
	public void displayInitialScreen(USER user) {
		userType = user;
		JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("York and Co. Pharmacy Management System");
        DisplayInitialScreen background = new DisplayInitialScreen();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(background.createContentPanel(userType));
        frame.setSize(1400, 800);
        frame.getContentPane().setLayout(null);
        //frame.setEnabled(false);

        frame.setVisible(true);
	}
	
	public JPanel createContentPanel(USER user) {
		JPanel totalGUI = new JPanel();
		totalGUI.setFont(new Font("굴림", Font.BOLD, 18));
		totalGUI.setLayout(null);
		
		if(user == USER.OWNER || user == USER.PHARMACIST) {	//to display contents allowed to OWNER/PHARMACIST only
			this.createPanelVisibleToAdmin(totalGUI);
		}

        this.createExtraContents(totalGUI);
        this.createPanelVisibleToAll(totalGUI);
        		
		return totalGUI;
		
	}
	
	private void createPanelVisibleToAdmin(JPanel totalGUI) {
		JPanel panelVisibleToAdmin = new JPanel();
        panelVisibleToAdmin.setBounds(45, 536, 1200, 194);
        panelVisibleToAdmin.setLayout(null);  
        
        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("굴림", Font.BOLD, 18));
        lblName.setBounds(0, 0, 125, 35);
        panelVisibleToAdmin.add(lblName);
        
        inputFieldName = new JTextField();
        inputFieldName.setBounds(125, 0, 350, 35);
        panelVisibleToAdmin.add(inputFieldName);
        inputFieldName.setColumns(20);
        
        JLabel lblHCN = new JLabel("Health card#");
        lblHCN.setFont(new Font("굴림", Font.BOLD, 18));
        lblHCN.setBounds(0, 45, 125, 35);
        panelVisibleToAdmin.add(lblHCN);
        
        inputFieldHCN = new JTextField();
        inputFieldHCN.setBounds(125, 45, 350, 35);
        panelVisibleToAdmin.add(inputFieldHCN);
        inputFieldHCN.setColumns(20);      
        
        JLabel lblType = new JLabel("Type");
        lblType.setFont(new Font("굴림", Font.BOLD, 18));
        lblType.setBounds(0, 90, 125, 35);
        panelVisibleToAdmin.add(lblType);
        
        JLabel lblForm = new JLabel("Form");
        lblForm.setFont(new Font("굴림", Font.BOLD, 18));
        lblForm.setBounds(0, 135, 125, 35);
        panelVisibleToAdmin.add(lblForm);
        
        inputFieldType = new JTextField();
        inputFieldType.setBounds(125, 90, 350, 35);
        panelVisibleToAdmin.add(inputFieldType);
        inputFieldType.setColumns(30);
        
        inputFieldForm = new JTextField();
        inputFieldForm.setColumns(10);
        inputFieldForm.setBounds(125, 135, 350, 35);
        panelVisibleToAdmin.add(inputFieldForm);       
        
        JLabel lblQty = new JLabel("Qty");
        lblQty.setFont(new Font("굴림", Font.BOLD, 18));
        lblQty.setBounds(500, 0, 125, 35);
        panelVisibleToAdmin.add(lblQty);
        
        JLabel lblPrice = new JLabel("Price");
        lblPrice.setFont(new Font("굴림", Font.BOLD, 18));
        lblPrice.setBounds(500, 45, 125, 35);
        panelVisibleToAdmin.add(lblPrice);
        
        inputFieldQty = new JTextField();
        inputFieldQty.setBounds(625, 0, 90, 35);
        panelVisibleToAdmin.add(inputFieldQty);
        inputFieldQty.setColumns(10);     
        
        inputFieldPrice = new JTextField();
        inputFieldPrice.setFont(new Font("굴림", Font.BOLD, 18));
        inputFieldPrice.setBounds(625, 45, 90, 35);
        panelVisibleToAdmin.add(inputFieldPrice);
        inputFieldPrice.setColumns(10);       
        
        JRadioButton rdbtnRx = new JRadioButton("Rx");
        rdbtnRx.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnRx.setSelected(true);
        rdbtnRx.setBounds(831, 7, 113, 23);
        panelVisibleToAdmin.add(rdbtnRx);
        
        JRadioButton rdbtnOTC = new JRadioButton("OTC");
        rdbtnOTC.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnOTC.setBounds(831, 39, 113, 23);
        panelVisibleToAdmin.add(rdbtnOTC);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rdbtnOTC);
        group.add(rdbtnRx);             
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("굴림", Font.BOLD, 20));
        btnAdd.setBounds(500, 135, 125, 35);
        panelVisibleToAdmin.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//Exception is thrown when insufficient number of arguments is passed to Merchandise constructor. if all argument is fed, 
						//addToInventory is bound to success
				
					String _inputFieldName = inputFieldName.getText().toUpperCase();
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					double _inputFieldPrice = Double.parseDouble(inputFieldPrice.getText());
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
				
					Inventory inv1 = Inventory.getInstance();
					
					Boolean medicationAdded = false;
					
					Merchandise newMerchandise = new Merchandise(_inputFieldName, _inputFieldQty, _inputFieldPrice, _inputFieldType, _inputFieldForm, _isOTC);
					medicationAdded = inv1.addToInventory(newMerchandise);
					
					String temp = "";
					if (medicationAdded == true) {
						temp += "Add successful. See updated inventory below: \n\n";
					}
					else {
						temp += "Add unsuccessful. The medication (same name, type, form and OTC/Rx) already exists in the inventory. See current inventory below: \n\n";
					}
					
					temp += inv1.display();
				
					textboxOutput.setText(temp);
				}
				catch(Exception exception) { //catch any exceptions and show popup error
					DisplayErrorPopup.displayErrorPopup("name, Qty, price, type, and form are required", frame);
				}
			
			}
		});
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("굴림", Font.BOLD, 20));
        btnDelete.setBounds(637, 135, 125, 35);
        panelVisibleToAdmin.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//catches exceptions thrown from delete() caused by not passing enough number of arguments
					String _inputFieldName = inputFieldName.getText().toUpperCase();
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
				
					Inventory inv1 = Inventory.getInstance();
				
					Boolean medicationRemoved = false;
				
					medicationRemoved = inv1.delete(_inputFieldName, _inputFieldType, _inputFieldForm, _isOTC);
				
					String temp = "";
					if (medicationRemoved == false) {
						temp += "Remove unsuccessful. No such medication currently exists in the inventory. See current inventory below: \n\n";
					}
					else {
						temp += "Remove successful. See updated inventory below: \n\n";
					}

					temp += inv1.display();
				
					textboxOutput.setText(temp);
				}
				catch (Exception ex) {	//display error popup
					DisplayErrorPopup.displayErrorPopup("name, type, and form are required", frame);
				}
			
			}
		});
        
        JButton btnIncrease = new JButton("Increase");
        btnIncrease.setFont(new Font("굴림", Font.BOLD, 20));
        btnIncrease.setBounds(774, 135, 125, 35);
        panelVisibleToAdmin.add(btnIncrease);
        btnIncrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//catches exception thrown by increaseQuantity() caused by passing insufficient number of arguments
					String _inputFieldName = inputFieldName.getText().toUpperCase();
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
				
					Inventory inv1 = Inventory.getInstance();
				
					Boolean medicationIncreased = false;
			
					medicationIncreased = inv1.increaseQuantity(_inputFieldName, _inputFieldQty, _inputFieldType, _inputFieldForm, _isOTC);
				
					String temp = "";
					if (medicationIncreased == false) {
						temp += "Increase unsuccessful. No such medication currently exists in the inventory. See current inventory below: \n\n";
					}
					else {
						temp += "Increase successful. See updated inventory below: \n";
					}

					temp += inv1.display();
				
					textboxOutput.setText(temp);
				}
				catch(Exception ex) {
					DisplayErrorPopup.displayErrorPopup("name, Qty, type, and form are required", frame);
				}
			
			}
		});
        
        JButton btnDecrease = new JButton("Decrease");
        btnDecrease.setFont(new Font("굴림", Font.BOLD, 18));
        btnDecrease.setBounds(911, 135, 125, 35);
        panelVisibleToAdmin.add(btnDecrease);
        btnDecrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//catches exception thrown from decreseQuantity() caused by passing insufficient number of arguments
					String _inputFieldName = inputFieldName.getText().toUpperCase();
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
				
					Inventory inv1 = Inventory.getInstance();
				
					boolean[] medicationDecreasedANDEnoughQuantityToDecrease = {false, false, false};
				
					medicationDecreasedANDEnoughQuantityToDecrease = inv1.decreaseQuantity(_inputFieldName, _inputFieldQty, _inputFieldType, _inputFieldForm, _isOTC);
					
					String temp = "";
					if (medicationDecreasedANDEnoughQuantityToDecrease[0] == true && medicationDecreasedANDEnoughQuantityToDecrease[1] == true) {
						//entry to decrease its quantity is found in the list and the current_quantity >= quantity_to_decrease 
						temp += "Decrease successful. See inventory below: \n\n";
					}
					else if (medicationDecreasedANDEnoughQuantityToDecrease[0] == false && medicationDecreasedANDEnoughQuantityToDecrease[1] == true) {
						//entry to decrease its quantity is not found in the list
						temp += "Decrease unsuccessful. No such medication currently exists in the inventory. See inventory below: \n\n";
					}
					else {
						//entry is found but current_quantity < quantity_to_decrease
						temp += "Decrease unsuccessful. There is not enough quantity of the medication to decrease by " + _inputFieldQty + ". See updated inventory below: \n\n";
					}
				
					if (medicationDecreasedANDEnoughQuantityToDecrease[2] == true) {
						//notify popup for medication low in stock
						DisplayErrorPopup.displayErrorPopup(_inputFieldName + " is low on stock, please order.", frame);
					}

					temp += inv1.display();
				
					textboxOutput.setText(temp);
				}
				catch(Exception ex) {	//display popup when the number of arguments passed to decreaseQuantity() is insufficient
					DisplayErrorPopup.displayErrorPopup("name, Qty, type, and form are required", frame);
				}
			
			}
		});
        
        JButton btnDisplay = new JButton("Display");
        btnDisplay.setFont(new Font("굴림", Font.BOLD, 20));
        btnDisplay.setBounds(1048, 134, 125, 35);
        panelVisibleToAdmin.add(btnDisplay);
        btnDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Inventory inv1 = Inventory.getInstance();
				
				String temp = "";
				temp += inv1.display();
				
				textboxOutput.setText(temp);
			
			}
		});
        
        totalGUI.add(panelVisibleToAdmin);
	}
	
	private void createExtraContents(JPanel totalGUI) {
        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("굴림", Font.BOLD, 20));
        btnExit.setBounds(1213, 40, 125, 35);
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setBorder(new LineBorder(new Color(192, 192, 192)));
        horizontalBox.setBackground(new Color(0, 0, 0));
        horizontalBox.setBounds(12, 515, 1362, 2);
        
        totalGUI.add(horizontalBox);
        totalGUI.add(btnExit);
	}
	
	private void createPanelVisibleToAll(JPanel totalGUI) {
		JPanel panelVisibleToAll = new JPanel();
        panelVisibleToAll.setBounds(45, 40, 944, 455);
 
        panelVisibleToAll.setLayout(null);
        
        inputKeyword = new JTextField();
        inputKeyword.setBounds(0, 0, 650, 35);
        panelVisibleToAll.add(inputKeyword);
        inputKeyword.setColumns(40);
        //searchKeyword = inputKeyword.getText();
        JComboBox comboBox = new JComboBox();
        comboBox.setBorder(new LineBorder(new Color(0, 0, 0)));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Name", "Type"}));
        comboBox.setSelectedIndex(0);
        comboBox.setFont(new Font("굴림", Font.BOLD, 18));
        comboBox.setBackground(new Color(255, 255, 255));
        comboBox.setBounds(650, 0, 145, 35);
        panelVisibleToAll.add(comboBox);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("굴림", Font.BOLD, 20));
        btnSearch.setBounds(819, 0, 125, 35);
        panelVisibleToAll.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String _inputKeyword = inputKeyword.getText().toUpperCase();
				String searchBy = (String)comboBox.getSelectedItem();
				if(userType == USER.OWNER || userType == USER.PHARMACIST) {
					Owner owner1 = new Owner(1,1);
					ArrayList<Merchandise> methodResult = null;
					if(searchBy.compareTo("Name") == 0) {
						methodResult = owner1.searchOTCMedicineByName(_inputKeyword);
					}
					else if(searchBy.compareTo("Type") == 0) {
						methodResult = owner1.searchOTCMedicineByType(MERCHANDISE_TYPE.getValue(_inputKeyword));
					}
					else {	//left empty intentionally for further expansion of feature in the future
						
					}
					
					String temp = "";
					if (methodResult.isEmpty() == true) {
						temp += "No results for " + _inputKeyword + " as a " + searchBy + " have been found in the inventory.";
					}
					else {
						for (Merchandise i: methodResult) {
							temp += i.toString();
						}
					}
					
					textboxOutput.setText(temp);
				}
				else {
					Patient patient1 = new Patient(1,1);
					ArrayList<Merchandise> methodResult = null;
					
					if(searchBy.compareTo("Name") == 0) {
						methodResult = patient1.searchOTCMedicineByName(_inputKeyword);
					}
					else if(searchBy.compareTo("Type") == 0) {
						methodResult = patient1.searchOTCMedicineByType(MERCHANDISE_TYPE.getValue(_inputKeyword));
					}
					else {
						
					}
					
					String temp = "";
					if (methodResult.isEmpty() == true) {
						temp += "No results for " + _inputKeyword + " as a " + searchBy + " have been found in the inventory.";
					}
					else {
						for (Merchandise i: methodResult) {
							temp += i.toString();
						}
					}
					
					textboxOutput.setText(temp);
				}
			}
		});
        

        
        textboxOutput = new JTextArea();
        textboxOutput.setBorder(new LineBorder(new Color(0, 0, 0)));
        textboxOutput.setFont(new Font("굴림", Font.PLAIN, 15));
        textboxOutput.setBounds(0, 58, 944, 397);
        panelVisibleToAll.add(textboxOutput);
            
        totalGUI.add(panelVisibleToAll);
        

	}
	
	public String getName() {
		return new String(name);
	}
	public int getUsername() {
		return username;
	}
	public String getType() {
		return new String(type);
	}
	public String getForm() {
		return new String(form);
	}
	public int getQty() {
		return qty;
	}
	public double getPrice() {
		return price;
	}
	public boolean getIsOTC() {
		return isOTC;
	}
	public String getSearchKeyword() {
		return new String(searchKeyword);
	}

}
