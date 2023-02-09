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

//Minh's notes: TODO Parse input search from box ~~~ type/TyPe/.. -> Capitalized -> ENUM

public class DisplayInitialScreen {
	private static JTextField inputFieldName;
	private static JTextField inputFieldHCN;
	private static JTextField inputFieldType;
	private static JTextField inputFieldForm;
	private static JTextField inputFieldQty;
	private static JTextField inputFieldPrice;
	private static JTextField inputKeyword;
	
	private static String name;
	private static int username;
	private static String type;
	private static String form;
	private static int qty;
	private static double price;
	private static boolean isOTC;
	private static String searchKeyword;
	private static USER user;
	
	JTextArea textboxOutput;
	
	public void displayInitialScreen(USER user) {
		this.user = user;
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("York and Co. Pharmacy Management System");
        DisplayInitialScreen background = new DisplayInitialScreen();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(background.createContentPanel(user));
        frame.setSize(1400, 800);
        frame.getContentPane().setLayout(null);
        

        frame.setVisible(true);
	}
	public JPanel createContentPanel(USER user) {
		JPanel totalGUI = new JPanel();
		totalGUI.setFont(new Font("굴림", Font.BOLD, 18));
		totalGUI.setLayout(null);
		
		//if(user == USER.OWNER || user == USER.PHARMACIST) {
		//	this.createPanelVisibleToAdmin(totalGUI);
		//}
		this.createPanelVisibleToAdmin(totalGUI);
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
        //name = inputFieldName.getText();
        
        JLabel lblHCN = new JLabel("Health card#");
        lblHCN.setFont(new Font("굴림", Font.BOLD, 18));
        lblHCN.setBounds(0, 45, 125, 35);
        panelVisibleToAdmin.add(lblHCN);
        
        inputFieldHCN = new JTextField();
        inputFieldHCN.setBounds(125, 45, 350, 35);
        panelVisibleToAdmin.add(inputFieldHCN);
        inputFieldHCN.setColumns(20);
        //username = Integer.getInteger(inputFieldHCN.getText(), 0);	//assign 0 if the input is invalid
        
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
        //type = inputFieldType.getText();
        
        inputFieldForm = new JTextField();
        inputFieldForm.setColumns(10);
        inputFieldForm.setBounds(125, 135, 350, 35);
        panelVisibleToAdmin.add(inputFieldForm);
        //form = inputFieldForm.getText();
        
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
        //qty = Integer.getInteger(inputFieldQty.getText(), 0); 	//assign 0 if the input is invalid
        
        inputFieldPrice = new JTextField();
        inputFieldPrice.setFont(new Font("굴림", Font.BOLD, 18));
        inputFieldPrice.setBounds(625, 45, 90, 35);
        panelVisibleToAdmin.add(inputFieldPrice);
        inputFieldPrice.setColumns(10);
        //price = Double.parseDouble(inputFieldPrice.getText());
        
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
        
        //isOTC = rdbtnOTC.isEnabled();
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("굴림", Font.BOLD, 20));
        btnAdd.setBounds(500, 135, 125, 35);
        panelVisibleToAdmin.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				
					String _inputFieldName = inputFieldName.getText();
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					double _inputFieldPrice = Double.parseDouble(inputFieldPrice.getText());
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
				
					Inventory inv1 = Inventory.getInstance();
				
					Merchandise newMerchandise = new Merchandise(_inputFieldName, _inputFieldQty, _inputFieldPrice, _inputFieldType, _inputFieldForm, _isOTC);
					inv1.addToInventory(newMerchandise);
				
					String temp = "Add successful. See updated inventory below: \n\n";
					temp += inv1.display();
				
					textboxOutput.setText(temp);
				}
				catch(Exception exception) {
					DisplayErrorPopup.displayErrorPopup("name, Qty, price, type, and form are required");
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
				String _inputFieldName = inputFieldName.getText();
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
		});
        
        JButton btnIncrease = new JButton("Increase");
        btnIncrease.setFont(new Font("굴림", Font.BOLD, 20));
        btnIncrease.setBounds(774, 135, 125, 35);
        panelVisibleToAdmin.add(btnIncrease);
        btnIncrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String _inputFieldName = inputFieldName.getText();
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
		});
        
        JButton btnDecrease = new JButton("Decrease");
        btnDecrease.setFont(new Font("굴림", Font.BOLD, 18));
        btnDecrease.setBounds(911, 135, 125, 35);
        panelVisibleToAdmin.add(btnDecrease);
        btnDecrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String _inputFieldName = inputFieldName.getText();
				int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
				MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
				MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
				Boolean _isOTC = false;
				if(rdbtnOTC.isSelected()) {
					_isOTC = true;
				}
				
				Inventory inv1 = Inventory.getInstance();
				
				boolean[] medicationDecreasedANDEnoughQuantityToDecrease = {false, false};
				try {
					medicationDecreasedANDEnoughQuantityToDecrease = inv1.decreaseQuantity(_inputFieldName, _inputFieldQty, _inputFieldType, _inputFieldForm, _isOTC);
				}
				catch(Exception ex){
					DisplayErrorPopup.displayErrorPopup("name, Qty, price, type, and form are required");
				}
				String temp = "";
				if (medicationDecreasedANDEnoughQuantityToDecrease[0] == true && medicationDecreasedANDEnoughQuantityToDecrease[1] == true) {
					temp += "Decrease successful. See inventory below: \n\n";
				}
				//case not possible
//				else if (medicationDecreasedANDEnoughQuantityToDecrease[0] == true && medicationDecreasedANDEnoughQuantityToDecrease[1] == false) {
//					temp += "Decrease unsuccessful. No such medication currently exists in the inventory. See current inventory below: \n";
//				}
				else if (medicationDecreasedANDEnoughQuantityToDecrease[0] == false && medicationDecreasedANDEnoughQuantityToDecrease[1] == true) {
					temp += "Decrease unsuccessful. No such medication currently exists in the inventory. See inventory below: \n\n";
				}
				else {
					temp += "Decrease unsuccessful. There is not enough quantity of the medication to decrease by " + _inputFieldQty + ". See updated inventory below: \n\n";
				}

				temp += inv1.display();
				
				textboxOutput.setText(temp);
			
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
				String _inputKeyword = inputKeyword.getText();
				String searchBy = (String)comboBox.getSelectedItem();
				if(user == USER.OWNER || user == USER.PHARMACIST) {
					Owner owner1 = new Owner(1,1);
					ArrayList<Merchandise> methodResult = null;
					if(searchBy.compareTo("Name") == 0) {
						methodResult = owner1.searchOTCMedicineByName(_inputKeyword);
					}
					else if(searchBy.compareTo("Type") == 0) {
						methodResult = owner1.searchOTCMedicineByType(MERCHANDISE_TYPE.getValue(_inputKeyword));
					}
					else {
						
					}
					
					String temp = "";
					for (Merchandise i: methodResult) {
						temp += i.toString();
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
					for (Merchandise i: methodResult) {
						temp += i.toString();
					}
					textboxOutput.setText(temp);
				}
			}
		});
        
//        JList list = new JList();
//        list.setBorder(new LineBorder(new Color(0, 0, 0)));
//        list.setFont(new Font("굴림", Font.PLAIN, 15));
//        list.setBounds(0, 58, 944, 397);
//        panelVisibleToAll.add(list);
        
        textboxOutput = new JTextArea();
       // textboxOutput.setBorder
        textboxOutput.setBorder(new LineBorder(new Color(0, 0, 0)));
        textboxOutput.setFont(new Font("굴림", Font.PLAIN, 15));
        textboxOutput.setBounds(0, 58, 944, 397);
        panelVisibleToAll.add(textboxOutput);
        
		
        //totalGUI.add(panelVisibleToAdmin);
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
	
	
	
/*public static void main(String[] args) {
	// TODO Auto-generated method stub
		//displayInitialScreen(USER.PATIENT); 
	DisplayInitialScreen screen = new DisplayInitialScreen();
		//screen.displayInitialScreen(USER.PATIENT);
		screen.displayInitialScreen(USER.OWNER);
		//screen.displayInitialScreen(USER.PHARMACIST);
	}*/
}
