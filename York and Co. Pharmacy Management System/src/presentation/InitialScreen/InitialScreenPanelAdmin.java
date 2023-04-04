package presentation.InitialScreen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import middleLayer.NegativeInputException;
import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.MerchandiseInventory.MERCHANDISE_FORM;
import middleLayer.MerchandiseInventory.MERCHANDISE_TYPE;
import middleLayer.MerchandiseInventory.Merchandise;
import presentation.DisplayAddOrderAddPresciptionForm;
import presentation.DisplayModifyMerchandise;
import presentation.DisplayPatientManage;
import presentation.DisplayReport;
import presentation.DisplaySeeOrders;
import presentation.USER;

public class InitialScreenPanelAdmin implements ActionListener{
	private JTextField inputFieldName;
	private JTextField inputFieldType;
	private JTextField inputFieldForm;
	private JTextField inputFieldQty;
	private JTextField inputFieldPrice;
	private JTextField inputFieldID;
	private String operationResult;
	//private JLabel lblOperationResult;
	private Inventory inv;
	private ArrayList<Merchandise> currentList;
	private JList<Merchandise> outputList;
	private static USER userType;
	private JFrame frame;
	private ButtonGroup groupRadio;
	
	private InitialScreenPanelAdmin() {
		//disables default constructor
	}
	public InitialScreenPanelAdmin(JFrame currentFrame, List<Merchandise> currentList  ) {
		this.frame = currentFrame;

		this.currentList = (ArrayList<Merchandise>)currentList;
	}
	protected void createPanelVisibleToAdmin(JPanel totalGUI, Inventory inventoryInstance, USER user) {
		userType = user;
		this.inv = inventoryInstance;
		JPanel panelVisibleToAdmin = new JPanel();
        panelVisibleToAdmin.setBounds(66, 537, 1308, 202);
        panelVisibleToAdmin.setLayout(null);  

        JPanel panelVisibleToAdminSub1 = new JPanel();
        panelVisibleToAdminSub1.setBounds(1001, 98, 170, 407);

        panelVisibleToAdminSub1.setLayout(null);
        
        createLabels(panelVisibleToAdmin);
        createInputFields(panelVisibleToAdmin);
        createButtonsOnMainPanel(panelVisibleToAdmin);
        createButtonsOnSubPanel(panelVisibleToAdminSub1, userType);
        
        totalGUI.add(panelVisibleToAdmin);
        totalGUI.add(panelVisibleToAdminSub1);
        
       
	}
	
	public  void createLabels(JPanel panel) {
        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("굴림", Font.BOLD, 18));
        lblName.setBounds(0, 0, 125, 35);
        panel.add(lblName);
        
        JLabel lblType = new JLabel("Type");
        lblType.setFont(new Font("굴림", Font.BOLD, 18));
        lblType.setBounds(0, 45, 125, 35);
        panel.add(lblType);
        
        JLabel lblForm = new JLabel("Form");
        lblForm.setFont(new Font("굴림", Font.BOLD, 18));
        lblForm.setBounds(0, 90, 125, 35);
        panel.add(lblForm);
        
        JLabel lblQty = new JLabel("Qty");
        lblQty.setFont(new Font("굴림", Font.BOLD, 18));
        lblQty.setBounds(619, 10, 125, 35);
        panel.add(lblQty);
        
        JLabel lblPrice = new JLabel("Price");
        lblPrice.setFont(new Font("굴림", Font.BOLD, 18));
        lblPrice.setBounds(260, 135, 125, 35);
        panel.add(lblPrice);
        
        JLabel lblID = new JLabel("ID");
        lblID.setBounds(900, 10, 125, 35);
        lblID.setFont(new Font("굴림", Font.BOLD, 18));
        panel.add(lblID);
	}
	
	public  void createInputFields(JPanel panel) {
		inputFieldName = new JTextField();
		inputFieldName.setBounds(125, 0, 350, 35);
		inputFieldName.setColumns(20);
		panel.add(inputFieldName);
		        	        
		inputFieldType = new JTextField();
		inputFieldType.setBounds(125, 45, 350, 35);
		inputFieldType.setColumns(30);
		panel.add(inputFieldType);
		        
		inputFieldForm = new JTextField();
		inputFieldForm.setColumns(10);
		inputFieldForm.setBounds(125, 90, 350, 35);
		panel.add(inputFieldForm);       
		                
		inputFieldQty = new JTextField();
		inputFieldQty.setFont(new Font("굴림", Font.BOLD, 18));
		inputFieldQty.setBounds(744, 10, 100, 35);
		inputFieldQty.setColumns(10);  
		panel.add(inputFieldQty);
		        
		inputFieldPrice = new JTextField();
		inputFieldPrice.setFont(new Font("굴림", Font.BOLD, 18));
		inputFieldPrice.setBounds(385, 135, 90, 35);
		inputFieldPrice.setColumns(10);  
		panel.add(inputFieldPrice);
		        
		inputFieldID = new JTextField();
		inputFieldID.setBounds(1025, 10, 134, 35);
		inputFieldID.setColumns(10);
		panel.add(inputFieldID);
	}
	
	public  void createButtonsOnMainPanel(JPanel panel) {
		JRadioButton rdbtnRx = new JRadioButton("Rx");
        rdbtnRx.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnRx.setSelected(true);
        rdbtnRx.setActionCommand("Rx");
        rdbtnRx.setBounds(483, 6, 113, 23);
        panel.add(rdbtnRx);
        
        JRadioButton rdbtnOTC = new JRadioButton("OTC");
        rdbtnOTC.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnOTC.setBounds(483, 35, 113, 23);
        rdbtnOTC.setActionCommand("OTC");
        panel.add(rdbtnOTC);
        
        groupRadio = new ButtonGroup();
        groupRadio.add(rdbtnOTC);
        groupRadio.add(rdbtnRx);   
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(487, 134, 125, 35);
        btnAdd.addActionListener(this);
        panel.add(btnAdd);
        btnAdd.setFont(new Font("굴림", Font.BOLD, 20));
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(1034, 134, 125, 35);
        btnDelete.addActionListener(this);
        panel.add(btnDelete);
        btnDelete.setFont(new Font("굴림", Font.BOLD, 20));
        
        JButton btnIncrease = new JButton("Increase");
        btnIncrease.setBounds(744, 134, 125, 35);
        btnIncrease.addActionListener(this);
        panel.add(btnIncrease);
        btnIncrease.setFont(new Font("굴림", Font.BOLD, 20));
        
        JButton btnDecrease = new JButton("Decrease");
        btnDecrease.setBounds(888, 135, 125, 35);
        btnDecrease.addActionListener(this);
        panel.add(btnDecrease);
        btnDecrease.setFont(new Font("굴림", Font.BOLD, 18));
	}
	
	public  void createButtonsOnSubPanel(JPanel panel, USER userType) {
		JButton btnManagePatients = new JButton("<html>Manage<br>Patients</html> ");
        btnManagePatients.setFont(new Font("굴림", Font.BOLD, 18));
        btnManagePatients.setBounds(0, 0, 170, 60);
        btnManagePatients.setActionCommand("ManagePatient");
        btnManagePatients.addActionListener(this);
        panel.add(btnManagePatients);
        
        JButton btnAddOrder = new JButton("<html>Add<br>Order/Refill</html>");
        btnAddOrder.setActionCommand("AddOrder");
        btnAddOrder.setFont(new Font("굴림", Font.BOLD, 18));
        btnAddOrder.setBounds(0, 140, 170, 60);
        btnAddOrder.addActionListener(this);
        panel.add(btnAddOrder);
        
        JButton btnModifyItem = new JButton("<html>Modify<br>Item</html>");
        btnModifyItem.setFont(new Font("굴림", Font.BOLD, 18));
        btnModifyItem.setBounds(0, 70, 170, 60);
        btnModifyItem.setActionCommand("ModifyItem");
        btnModifyItem.addActionListener(this);
        panel.add(btnModifyItem);
        
    	JButton btnSeeOrders = new JButton("<html>See<br>Orders</html>");
    	btnSeeOrders.setActionCommand("SeeOrders");
    	btnSeeOrders.setFont(new Font("굴림", Font.BOLD, 18));
    	btnSeeOrders.setBounds(0, 277, 170, 60);
    	btnSeeOrders.addActionListener(this);
    	panel.add(btnSeeOrders);
    	
    	JButton btnAddPrescription = new JButton("<html>Add<br>Rx Form</html>");
    	btnAddPrescription.setActionCommand("AddPrescription");
    	btnAddPrescription.setHorizontalTextPosition(SwingConstants.CENTER);
    	btnAddPrescription.setFont(new Font("굴림", Font.BOLD, 18));
    	btnAddPrescription.setBounds(0, 210, 170, 57);
    	btnAddPrescription.addActionListener(this);
    	panel.add(btnAddPrescription);

        if(userType == USER.OWNER || userType == USER.DEVELOPER) {
        	JButton btnSeeReport = new JButton("See Report");
        	btnSeeReport.setFont(new Font("굴림", Font.BOLD, 18));
	        btnSeeReport.setBounds(0, 347, 170, 60);
	        btnSeeReport.setActionCommand("SeeReport");
	        btnSeeReport.addActionListener(this);
	        panel.add(btnSeeReport);
        	
       }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equalsIgnoreCase("Add")) {
			this.addMedication();
		}
		else if(actionCommand.equalsIgnoreCase("Delete")) {
			this.deleteMedication();
		}
		else if(actionCommand.equalsIgnoreCase("AddOrder")) {
			DisplayAddOrderAddPresciptionForm.displayAddOrder(frame, actionCommand);
		}
		else if(actionCommand.equalsIgnoreCase("Increase")) {
			this.increaseQty();
		}
		else if(actionCommand.equalsIgnoreCase("Decrease")) {
			this.decreaseQty();
		}
		else if(actionCommand.equalsIgnoreCase("ManagePatient")) {
			DisplayPatientManage.displayPatientManage(frame);
		}
		else if(actionCommand.equalsIgnoreCase("ModifyItem")) {
			DisplayModifyMerchandise.displayModifyMerchandise(frame, outputList, currentList);
		}
		else if(actionCommand.equalsIgnoreCase("SeeOrders")) {
			DisplaySeeOrders.displaySeeOrders(frame, userType);
		}
		else if(actionCommand.equalsIgnoreCase("AddPrescription")) {
			DisplayAddOrderAddPresciptionForm.displayAddOrder(frame, actionCommand);
		}
		else if(actionCommand.equalsIgnoreCase("SeeReport")) {
            DisplayReport screen = new DisplayReport() ;
            screen.displayReport(frame);
		}
		else {
			//left empty for further expansion
		}
		InitialScreenPanelAll.displayMercList(currentList);
		
	}
	private void addMedication() {
		try {	
			InitialScreenPanelAll.setOperationResult("");
			
			String _inputFieldName = inputFieldName.getText().toUpperCase();
			String stringQty = inputFieldQty.getText();
			String stringPrice = inputFieldPrice.getText();
			String stringType = inputFieldType.getText().toUpperCase();
			String stringForm = inputFieldForm.getText().toUpperCase();
			
			// if any input field is left empty, throw exception
			if (_inputFieldName.isEmpty() || stringQty.isEmpty() || stringPrice.isEmpty() || stringType.isEmpty() || stringForm.isEmpty()) {
				throw new NullPointerException(); 
			}
			
			int _inputFieldQty = Integer.parseInt(stringQty); // throws NumberFormatException if stringQty not an int
			double _inputFieldPrice = Double.parseDouble(stringPrice); // throws NumberFormatException if stringPrice not an int/double
			MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(stringType);
			MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(stringForm);
			
			Boolean _isOTC = false;
			if(groupRadio.getSelection().getActionCommand().equals("OTC")) {
				_isOTC = true;
			}

			Merchandise newMerchandise = new Merchandise(_inputFieldName, _inputFieldQty, _inputFieldPrice, _inputFieldType, _inputFieldForm, _isOTC);
			
			inv.addToInventory(newMerchandise);
			
			operationResult = "Add successful. See updated inventory";
			//DisplayInitialScreen.setLblOperationResult(operationResult);
			InitialScreenPanelAll.setOperationResult(operationResult);
			
			currentList = inv.getMerchandise();
			InitialScreenPanelAll.displayMercList( currentList);
			
			inputFieldName.setText("");
			inputFieldQty.setText("");
			inputFieldPrice.setText("");
			inputFieldType.setText("");
			inputFieldForm.setText("");
			
		}
		catch (NullPointerException exception) {
			JOptionPane.showMessageDialog(frame,"name, Qty, price, type, and form are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(frame,"Qty must be an integer, Price must be an integer or double", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (IllegalArgumentException exception) {
			JOptionPane.showMessageDialog(frame,"Merchandise Type or Form invalid. Please enter a valid Merchandise Type and Form", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (NegativeInputException exception) { // thrown by method
			JOptionPane.showMessageDialog(frame, exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch(Exception exception) { //catch any exceptions/errors that the method call produces -> means method unsuccessful
			operationResult = exception.getMessage();
			//DisplayInitialScreen.setLblOperationResult(operationResult);
			InitialScreenPanelAll.setOperationResult(operationResult);
		}
	}
	
	private void deleteMedication() {
		try {
			InitialScreenPanelAll.setOperationResult("");
			
			String stringMedID = inputFieldID.getText();
			
			// if input field is left empty, throw exception
			if (stringMedID.isEmpty()) {
				throw new NullPointerException(); 
			}			
	
			int _inputFieldID = Integer.parseInt(stringMedID); // throws NumberFormatException if stringMedID not an int
			
			inv.delete(_inputFieldID);
			
			operationResult = "Remove successful. See updated inventory";
			//DisplayInitialScreen.setLblOperationResult(operationResult);
			InitialScreenPanelAll.setOperationResult(operationResult);
			
			currentList = inv.getMerchandise();
			InitialScreenPanelAll.displayMercList( currentList);

		}
		catch (NullPointerException exception) {
			JOptionPane.showMessageDialog(frame,"ID is required. Please enter a medication ID", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(frame,"ID must be an integer", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (Exception exception) { //thrown by method if it is unsuccessful
			operationResult = exception.getMessage();
			//DisplayInitialScreen.setLblOperationResult(operationResult);
			InitialScreenPanelAll.setOperationResult(operationResult);
		}
	}
	
	private void increaseQty() {
		try {
			InitialScreenPanelAll.setOperationResult("");
			
			String stringMedID = inputFieldID.getText();
			String stringMedQty = inputFieldQty.getText();
			
			// if any input field is left empty, throw exception
			if (stringMedID.isEmpty() || stringMedQty.isEmpty()) {
				throw new NullPointerException(); 
			}	
			
			// throws NumberFormatException if one of those are not an int
			int _inputFieldID = Integer.parseInt(stringMedID);
			int _inputFieldQty = Integer.parseInt(stringMedQty);
			
			inv.increaseQuantity(_inputFieldID, _inputFieldQty);
			
			operationResult = "Increase successful. See updated inventory";
			//DisplayInitialScreen.setLblOperationResult(operationResult);
			InitialScreenPanelAll.setOperationResult(operationResult);
			
			currentList = inv.getMerchandise();
			InitialScreenPanelAll.displayMercList( currentList);
		
		}
		catch (NullPointerException exception) {
			JOptionPane.showMessageDialog(frame,"ID and Qty are required. Please enter both fields", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(frame,"ID and Qty must be integers", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (NegativeInputException exception) {
			JOptionPane.showMessageDialog(frame,exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch(Exception exception) { //thrown by method if it is unsuccessful
			operationResult = exception.getMessage();
			//DisplayInitialScreen.setLblOperationResult(operationResult);
			InitialScreenPanelAll.setOperationResult(operationResult);
		}
	}
	
	private void decreaseQty() {
		try {
			InitialScreenPanelAll.setOperationResult("");
			
			String stringMedID = inputFieldID.getText();
			String stringMedQty = inputFieldQty.getText();
			
			// if any input field is left empty, throw exception
			if (stringMedID.isEmpty() || stringMedQty.isEmpty()) {
				throw new NullPointerException(); 
			}	
			
			// throws NumberFormatException if one of those are not an int
			int _inputFieldID = Integer.parseInt(stringMedID);
			int _inputFieldQty = Integer.parseInt(stringMedQty);
			
			boolean lowInStock = inv.decreaseQuantity(_inputFieldID, _inputFieldQty);
			
			operationResult = "Decrease successful. See updated inventory";
			//DisplayInitialScreen.setLblOperationResult(operationResult);
			InitialScreenPanelAll.setOperationResult(operationResult);
			
			if (lowInStock == true) {
				JOptionPane.showMessageDialog(frame,"Medication with ID: " + _inputFieldID + " is low on stock, please reorder!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			
			currentList = inv.getMerchandise();
			InitialScreenPanelAll.displayMercList( currentList);
			
		}
		catch (NullPointerException exception) {
			JOptionPane.showMessageDialog(frame,"ID and Qty are required. Please enter both fields", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(frame,"ID and Qty must be integers", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (NegativeInputException exception) {
			JOptionPane.showMessageDialog(frame,exception.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch(Exception exception) { //thrown by method if it is unsuccessful
			operationResult = exception.getMessage();
			//DisplayInitialScreen.setLblOperationResult(operationResult);
			InitialScreenPanelAll.setOperationResult(operationResult);
		}
	}
}
