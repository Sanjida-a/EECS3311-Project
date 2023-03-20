package presentation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;

//import javax.swin//////JTextAreaea;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ListCellRenderer;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;

import databaseDAO.superDAO;
import middleLayer.Users.*;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Orders.*;

import javax.swing.JList;
import javax.swing.JOptionPane;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.Rectangle;


public class DisplayInitialScreen{
	private static JTextField inputFieldName;
	private static JTextField inputFieldType;
	private static JTextField inputFieldForm;
	private static JTextField inputFieldQty;
	private static JTextField inputFieldPrice;
	private static JTextField inputKeyword;
	private static JFrame frame;
	private static JList<Merchandise> outputList;
	private static Inventory inv;
	private static JLabel lblOperationResult;
	private static JTextArea textAreaPurchaseHistory;
	
	private static String name;
	private static int username;
	private static String type;
	private static String form;
	private static int qty;
	private static double price;
	private static boolean isOTC;
	private static String searchKeyword;
	private static USER userType;
	
	private static int userLoggedIn;
	
	private static String operationResult;
	private static JTextField inputFieldID;
	
	private static ArrayList<Merchandise> currentList;
	
	private static long usernameLoggedIn;
	private static JTextField textField;
	

	
	public static void displayInitialScreen(USER user) {
		inv = Inventory.getInstance();
		userType = user;
		if(userType == USER.PATIENT || userType == USER.GUEST) {
			currentList = inv.getOnlyOTCMerchandise();
		}
		else {
			currentList = inv.getMerchandise();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("York and Co. Pharmacy Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(DisplayInitialScreen.createContentPanel(userType));
        frame.setSize(new Dimension(1400, 800));
        frame.getContentPane().setLayout(null);


        frame.setVisible(true);
	}
	
	private static JPanel createContentPanel(USER user) {
		String loginButton = "Login";
		JPanel totalGUI = new JPanel();
		totalGUI.setPreferredSize(new Dimension(1400, 800));
		totalGUI.setBounds(new Rectangle(0, 0, 1400, 800));
		totalGUI.setFont(new Font("굴림", Font.BOLD, 18));
		totalGUI.setLayout(null);
		
		if(user == USER.OWNER || user == USER.PHARMACIST) {	//to display contents allowed to OWNER/PHARMACIST only
			createPanelVisibleToAdmin(totalGUI);
		}
		else if(user == USER.DEVELOPER) {
			createPanelVisibleToAdmin(totalGUI); //for test purpose

		}
		if(user == USER.PATIENT) {
			createPanalVisibleToPatient(totalGUI);
		}

        createExtraContents(totalGUI);
        createPanelVisibleToAll(totalGUI);
        if(user != USER.GUEST) {
        	loginButton = "Logout";
        }
        JButton btnLogin = new JButton(loginButton);
        btnLogin.setActionCommand(loginButton);
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(e.getActionCommand().equals("Login")) {
        			
        			DisplayLogin login = new DisplayLogin();
        		
        			login.displayLogin(frame);
        			
        		}
        		else {
        			frame.dispose();
        			displayInitialScreen(USER.GUEST);
        		}


        	}
        });
        btnLogin.setFont(new Font("굴림", Font.BOLD, 20));
        btnLogin.setBounds(1125, 40, 120, 35);
        totalGUI.add(btnLogin);
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(1001, 40, 120, 35);
        totalGUI.add(btnRefresh);
        btnRefresh.setFont(new Font("굴림", Font.BOLD, 20));
        

        

        btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(userType == USER.OWNER || userType == USER.PHARMACIST || userType == USER.DEVELOPER) {
					currentList = inv.getMerchandise();
				}
				else {
					currentList = inv.getOnlyOTCMerchandise();
				}
				displayMercList(outputList, currentList);
			
			}
		});
        		
		return totalGUI;
		
	}
	
	private static void createPanelVisibleToAdmin(JPanel totalGUI) {
		JPanel panelVisibleToAdmin = new JPanel();
        panelVisibleToAdmin.setBounds(66, 537, 1308, 202);
        panelVisibleToAdmin.setLayout(null);  
        
        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("굴림", Font.BOLD, 18));
        lblName.setBounds(0, 0, 125, 35);
        panelVisibleToAdmin.add(lblName);
        
        inputFieldName = new JTextField();
        inputFieldName.setBounds(125, 0, 350, 35);
        panelVisibleToAdmin.add(inputFieldName);
        inputFieldName.setColumns(20);
        
        JLabel lblType = new JLabel("Type");
        lblType.setFont(new Font("굴림", Font.BOLD, 18));
        lblType.setBounds(0, 45, 125, 35);
        panelVisibleToAdmin.add(lblType);
        
        JLabel lblForm = new JLabel("Form");
        lblForm.setFont(new Font("굴림", Font.BOLD, 18));
        lblForm.setBounds(0, 90, 125, 35);
        panelVisibleToAdmin.add(lblForm);
        
        inputFieldType = new JTextField();
        inputFieldType.setBounds(125, 45, 350, 35);
        panelVisibleToAdmin.add(inputFieldType);
        inputFieldType.setColumns(30);
        
        inputFieldForm = new JTextField();
        inputFieldForm.setColumns(10);
        inputFieldForm.setBounds(125, 90, 350, 35);
        panelVisibleToAdmin.add(inputFieldForm);       
        
        JLabel lblQty = new JLabel("Qty");
        lblQty.setFont(new Font("굴림", Font.BOLD, 18));
        lblQty.setBounds(619, 10, 125, 35);
        panelVisibleToAdmin.add(lblQty);
        
        JLabel lblPrice = new JLabel("Price");
        lblPrice.setFont(new Font("굴림", Font.BOLD, 18));
        lblPrice.setBounds(260, 135, 125, 35);
        panelVisibleToAdmin.add(lblPrice);
        
        inputFieldQty = new JTextField();
        inputFieldQty.setFont(new Font("굴림", Font.BOLD, 18));
        inputFieldQty.setBounds(744, 10, 100, 35);
        panelVisibleToAdmin.add(inputFieldQty);
        inputFieldQty.setColumns(10);     
        
        inputFieldPrice = new JTextField();
        inputFieldPrice.setFont(new Font("굴림", Font.BOLD, 18));
        inputFieldPrice.setBounds(385, 135, 90, 35);
        panelVisibleToAdmin.add(inputFieldPrice);
        inputFieldPrice.setColumns(10);       
        
        JRadioButton rdbtnRx = new JRadioButton("Rx");
        rdbtnRx.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnRx.setSelected(true);
        rdbtnRx.setBounds(483, 6, 113, 23);
        panelVisibleToAdmin.add(rdbtnRx);
        
        JRadioButton rdbtnOTC = new JRadioButton("OTC");
        rdbtnOTC.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnOTC.setBounds(483, 35, 113, 23);
        panelVisibleToAdmin.add(rdbtnOTC);
        
        ButtonGroup groupRadio = new ButtonGroup();
        groupRadio.add(rdbtnOTC);
        groupRadio.add(rdbtnRx);         
        

        JPanel panelVisibleToAdminSub1 = new JPanel();
        panelVisibleToAdminSub1.setBounds(1001, 98, 170, 366);
        totalGUI.add(panelVisibleToAdminSub1);
        panelVisibleToAdminSub1.setLayout(null);
        
        JButton btnManagePatients = new JButton("<html>Manage<br>Patients</html> ");
        btnManagePatients.setFont(new Font("굴림", Font.BOLD, 18));
        btnManagePatients.setBounds(0, 0, 170, 60);
        btnManagePatients.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DisplayPatientManage.displayPatientManage(frame);
			}
        	
        });
        panelVisibleToAdminSub1.add(btnManagePatients);
        
        totalGUI.add(panelVisibleToAdmin);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(487, 134, 125, 35);
        btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//Exception is thrown when insufficient number of arguments is passed to Merchandise constructor. if all argument is fed, 
						//addToInventory is bound to success

					String _inputFieldName = inputFieldName.getText().toUpperCase();
					if (_inputFieldName.isEmpty()) throw new Exception(); // ensures a medication name has been entered
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					double _inputFieldPrice = Double.parseDouble(inputFieldPrice.getText());
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
					Boolean medicationAdded = false;					
					Merchandise newMerchandise = new Merchandise(_inputFieldName, _inputFieldQty, _inputFieldPrice, _inputFieldType, _inputFieldForm, _isOTC);
					
					try {
						medicationAdded = inv.addToInventory(newMerchandise);
						if (medicationAdded == true) {
							operationResult = "Add successful. See updated inventory";
						}
						else {
							operationResult = "Add unsuccessful. The medication (same name, type, form and OTC/Rx) already exists in the inventory. See current inventory";
						}
						currentList = inv.getMerchandise();
						displayMercList(outputList, currentList);
						lblOperationResult.setText(operationResult);
						inputFieldName.setText("");
						inputFieldQty.setText("");
						inputFieldPrice.setText("");
						inputFieldType.setText("");
						inputFieldForm.setText("");
					}
					catch (Exception e2){
						JOptionPane.showMessageDialog(frame,e2.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					

				}
				catch(Exception exception) { //catch any exceptions and show popup error
					JOptionPane.showMessageDialog(frame,"name, Qty, price, type, and form are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
			
			}
		});
        panelVisibleToAdmin.add(btnAdd);
        btnAdd.setFont(new Font("굴림", Font.BOLD, 20));
        
        JLabel lblNewLabel = new JLabel("ID");
        lblNewLabel.setBounds(900, 10, 125, 35);
        panelVisibleToAdmin.add(lblNewLabel);
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(1034, 134, 125, 35);
        btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//catches exceptions thrown from delete() caused by not passing enough number of arguments
				
					Boolean medicationRemoved = false;
					int _inputFieldID = Integer.parseInt(inputFieldID.getText());
//					System.out.println("med ID initialized");
					medicationRemoved = inv.delete(_inputFieldID);
//					System.out.println("med deleted");
							
					if (medicationRemoved == false) {
						operationResult = "Remove unsuccessful. No such medication currently exists in the inventory. See current inventory";
					}
					else {
						operationResult = "Remove successful. See updated inventory";
					}
					currentList = inv.getMerchandise();
					displayMercList(outputList, currentList);
					lblOperationResult.setText(operationResult);

				}
				catch (Exception ex) {	//display error popup
					JOptionPane.showMessageDialog(frame,"ID required", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
			
			}
		});
        panelVisibleToAdmin.add(btnDelete);
        btnDelete.setFont(new Font("굴림", Font.BOLD, 20));
        
        JButton btnIncrease = new JButton("Increase");
        btnIncrease.setBounds(744, 134, 125, 35);
        btnIncrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//catches exception thrown by increaseQuantity() caused by passing insufficient number of arguments
					
					int _inputFieldID = Integer.parseInt(inputFieldID.getText());
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					
					Boolean medicationIncreased = false;
					
					try {
						medicationIncreased = inv.increaseQuantity(_inputFieldID, _inputFieldQty);
						if (medicationIncreased == false) {
							operationResult = "Increase unsuccessful. No such medication currently exists in the inventory. See current inventory";
						}
						else {
							operationResult = "Increase successful. See updated inventory";
						}
	
						currentList = inv.getMerchandise();
						displayMercList(outputList, currentList);
						lblOperationResult.setText(operationResult);
					}
					catch (Exception e2) {
						JOptionPane.showMessageDialog(frame,e2.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
				
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(frame,"ID and Quantity are required","Invalid input", JOptionPane.WARNING_MESSAGE);
				}
			
			}
		});
        panelVisibleToAdmin.add(btnIncrease);
        btnIncrease.setFont(new Font("굴림", Font.BOLD, 20));
        
        JButton btnDecrease = new JButton("Decrease");
        btnDecrease.setBounds(888, 135, 125, 35);
        btnDecrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//catches exception thrown from decreseQuantity() caused by passing insufficient number of arguments
				
					int _inputFieldID = Integer.parseInt(inputFieldID.getText());
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					
					boolean[] medicationDecreasedANDEnoughQuantityToDecrease = {false, false, false};
					
					try {
						medicationDecreasedANDEnoughQuantityToDecrease = inv.decreaseQuantity(_inputFieldID, _inputFieldQty);
						
						if (medicationDecreasedANDEnoughQuantityToDecrease[0] == true && medicationDecreasedANDEnoughQuantityToDecrease[1] == true) {
							//entry to decrease its quantity is found in the list and the current_quantity >= quantity_to_decrease 
							operationResult = "Decrease successful. See inventory";
						}
						else if (medicationDecreasedANDEnoughQuantityToDecrease[0] == false && medicationDecreasedANDEnoughQuantityToDecrease[1] == true) {
							//entry to decrease its quantity is not found in the list
							operationResult = "Decrease unsuccessful. No such medication currently exists in the inventory. See inventory";
						}
						else {
							//entry is found but current_quantity < quantity_to_decrease
							operationResult = "Decrease unsuccessful. There is not enough quantity of the medication to decrease by " + _inputFieldQty + ". See inventory";
						}
					
						if (medicationDecreasedANDEnoughQuantityToDecrease[2] == true) {
							//notify popup for medication low in stock
							//DisplayErrorPopup.displayErrorPopup(_inputFieldName + " is low on stock, please order.", frame);
							JOptionPane.showMessageDialog(frame,"Medication with ID: " + _inputFieldID + " is low on stock, please order.", "Warning", JOptionPane.WARNING_MESSAGE);
						}
						lblOperationResult.setText(operationResult);
						currentList = inv.getMerchandise();
						displayMercList(outputList, currentList);
					}
					catch (Exception e2) {
						JOptionPane.showMessageDialog(frame,e2.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					
				}
				catch(Exception ex) {	//display popup when the number of arguments passed to decreaseQuantity() is insufficient
					//DisplayErrorPopup.displayErrorPopup("name, Qty, type, and form are required", frame);
					JOptionPane.showMessageDialog(frame,"ID and Quantity are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
					
				}
			
			}
		});
        panelVisibleToAdmin.add(btnDecrease);
        btnDecrease.setFont(new Font("굴림", Font.BOLD, 18));
        
        inputFieldID = new JTextField();
        inputFieldID.setBounds(1025, 10, 134, 35);
        panelVisibleToAdmin.add(inputFieldID);
        inputFieldID.setColumns(10);
        
        totalGUI.add(panelVisibleToAdminSub1);
        
        JButton btnAddOrder = new JButton("<html>Add<br>Order</html>");
        btnAddOrder.setFont(new Font("굴림", Font.BOLD, 18));
        btnAddOrder.setBounds(0, 70, 170, 60);
        btnAddOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DisplayAddOrder.displayAddOrder(frame);

			}
        	
        });
        panelVisibleToAdminSub1.add(btnAddOrder);
        
        JButton btnModifyItem = new JButton("<html>Modify<br>Item</html>");
        btnModifyItem.setFont(new Font("굴림", Font.BOLD, 18));
        btnModifyItem.setBounds(0, 140, 170, 60);
        btnModifyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//invoke method for modify item detail
				DisplayModifyMerchandise.displayModifyMerchandise(frame, outputList, currentList);
			}
        	
        });
        panelVisibleToAdminSub1.add(btnModifyItem);
        if(userType == USER.OWNER || userType == USER.DEVELOPER) {
        	JButton btnSeeReport = new JButton("See Report");
        	btnSeeReport.setFont(new Font("굴림", Font.BOLD, 18));
	        btnSeeReport.setBounds(0, 210, 170, 60);
	        btnSeeReport.addActionListener(new ActionListener() {
	
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                DisplayReport screen = new DisplayReport() ;
	                screen.displayReport(frame);
	
	            }

        });
        	panelVisibleToAdminSub1.add(btnSeeReport);
        }
	}
	
	private static void createExtraContents(JPanel totalGUI) {
        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("굴림", Font.BOLD, 20));
        btnExit.setBounds(1249, 40, 120, 35);
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
	
	private static void createPanelVisibleToAll(JPanel totalGUI) {
		JPanel panelVisibleToAll = new JPanel();
        panelVisibleToAll.setBounds(45, 40, 944, 465);
        panelVisibleToAll.setLayout(null);
        
        inputKeyword = new JTextField();
        inputKeyword.setBounds(0, 0, 650, 35);
        panelVisibleToAll.add(inputKeyword);
        inputKeyword.setColumns(40);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(650, 0, 145, 35);
        comboBox.setBorder(new LineBorder(new Color(0, 0, 0)));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Name", "Type"}));
        comboBox.setSelectedIndex(0);
        comboBox.setFont(new Font("굴림", Font.BOLD, 18));
        comboBox.setBackground(new Color(255, 255, 255));
        panelVisibleToAll.add(comboBox);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(819, 0, 125, 35);
        btnSearch.setFont(new Font("굴림", Font.BOLD, 20));
        panelVisibleToAll.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String _inputKeyword = inputKeyword.getText().toUpperCase();
					String searchBy = (String)comboBox.getSelectedItem();
					ArrayList<Merchandise> methodResult = new ArrayList<Merchandise>();
					if(_inputKeyword.isEmpty()) {
						throw new Exception();
					}
					
					Owner owner1 = new Owner(1,1);
					
					if(searchBy.compareTo("Name") == 0) { //checking drop down list value
						methodResult = owner1.searchMedicineByName(_inputKeyword, userType);
					}
					else if(searchBy.compareTo("Type") == 0) {
						methodResult = owner1.searchMedicineByType(MERCHANDISE_TYPE.getValue(_inputKeyword), userType);
					}
					
					if(methodResult.isEmpty()) {
						operationResult = _inputKeyword + " is not in the inventory";
						lblOperationResult.setText(operationResult);
					}
					else {
						operationResult = "";
						lblOperationResult.setText(operationResult);
					}
					currentList = methodResult;
					displayMercList(outputList, currentList);
				}	
				catch(Exception ex) {
					JOptionPane.showMessageDialog(frame,"search keyword is empty", "input required", JOptionPane.WARNING_MESSAGE);
				}
			

			}
		});
        
        

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 75, 944, 350);
        outputList = new JList<Merchandise>();
        outputList.setFont(new Font("Monospaced", Font.PLAIN, 15));
        outputList.setLocation(0, 45);
        outputList.setSize(944, 410);

        outputList.setLayoutOrientation(JList.VERTICAL);
        outputList.setCellRenderer(createListRenderer());
        outputList.addMouseListener(new MouseAdapter() {
        	 public void mouseClicked(MouseEvent evt) {
        		 
        		 if(evt.getClickCount() == 2) {
        			 
        			 DisplayDescription.displayDescription(frame, outputList.getSelectedValue());
        		 }
        	 }
        });
        scrollPane.setViewportView(outputList);

        panelVisibleToAll.add(scrollPane);
        displayMercList(outputList, currentList);
        totalGUI.add(panelVisibleToAll);
        
        JPanel panelColumn = new JPanel();
        panelColumn.setBounds(0, 56, 944, 20);
        panelVisibleToAll.add(panelColumn);
        panelColumn.setLayout(null);
        
        JButton btnColumnName = new JButton("Name");
        btnColumnName.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnName.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnName.setHorizontalTextPosition(SwingConstants.LEFT);
        btnColumnName.setBounds(75, 0, 290, 20);
        btnColumnName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				displayMercList(outputList, inv.displayAlphabetically(currentList));
			}
        	
        });
        panelColumn.add(btnColumnName);
        
        JButton btnColumnQty = new JButton("Qty");
        btnColumnQty.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnQty.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnQty.setBounds(364, 0, 80, 20);
        btnColumnQty.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				displayMercList(outputList, inv.displayByQuantity(currentList));
			}
        	
        });
        panelColumn.add(btnColumnQty);
        
        JButton btnColumnPrice = new JButton("Price");
        btnColumnPrice.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnPrice.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnPrice.setBounds(443, 0, 100, 20);
        btnColumnPrice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				displayMercList(outputList,inv.displayByPrice(currentList));
			}
        	
        });
        panelColumn.add(btnColumnPrice);
        
        JButton btnColumnType = new JButton("Type");
        btnColumnType.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnType.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnType.setBounds(542, 0, 150, 20);
        panelColumn.add(btnColumnType);
        
        JButton btnColumnForm = new JButton("Form");
        btnColumnForm.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnForm.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnForm.setBounds(691, 0, 150, 20);
        panelColumn.add(btnColumnForm);
        
        JButton btnColumnOTC = new JButton("isOTC");
        btnColumnOTC.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnOTC.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnOTC.setBounds(840, 0, 104, 20);
        panelColumn.add(btnColumnOTC);
        
        JButton btnColumnID = new JButton("ID");
        btnColumnID.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnID.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnID.setBounds(0, 0, 75, 20);
        panelColumn.add(btnColumnID);
        
        ButtonGroup groupColumn = new ButtonGroup();
        groupColumn.add(btnColumnName);
        groupColumn.add(btnColumnQty);
        groupColumn.add(btnColumnPrice);
        groupColumn.add(btnColumnType);
        groupColumn.add(btnColumnForm);
        groupColumn.add(btnColumnOTC);
        groupColumn.add(btnColumnID);
        
        
        lblOperationResult = new JLabel();
        lblOperationResult.setForeground(new Color(255, 0, 0));
        lblOperationResult.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 18));
        lblOperationResult.setBounds(0, 425, 944, 40);
        panelVisibleToAll.add(lblOperationResult);
        


	}
	
	private static void createPanalVisibleToPatient(JPanel totalGUI) {
		JPanel panelVisibleToPatient = new JPanel();
        panelVisibleToPatient.setBounds(1183, 98, 170, 366);
        totalGUI.add(panelVisibleToPatient);
        panelVisibleToPatient.setLayout(null);
        
        JButton btnPurchaseHistory = new JButton("<html>Purchase<br>History</html>");
        btnPurchaseHistory.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPurchaseHistory.setActionCommand("PurchaseHistory");
        btnPurchaseHistory.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//method for purchase history is called here
        	}
        });
        btnPurchaseHistory.setFont(new Font("굴림", Font.BOLD, 17));
        btnPurchaseHistory.setBounds(0, 0, 170, 60);
        panelVisibleToPatient.add(btnPurchaseHistory);
        
        totalGUI.add(panelVisibleToPatient);
        
        JButton btnPrescription = new JButton("<html>See prescription<br>Refills</html>");
        btnPrescription.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPrescription.setFont(new Font("굴림", Font.BOLD, 17));
        btnPrescription.setActionCommand("Prescription");
        btnPrescription.setBounds(0, 80, 170, 60);
        btnPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//method to see prescription refills is called here
			}
        });
        panelVisibleToPatient.add(btnPrescription);
        
        JPanel panelOutputAreaForPatient = new JPanel();
        panelOutputAreaForPatient.setBounds(45, 527, 1308, 263);
        totalGUI.add(panelOutputAreaForPatient);
        panelOutputAreaForPatient.setLayout(null);
        
        textAreaPurchaseHistory = new JTextArea();
        textAreaPurchaseHistory.setBounds(0, 0, 944, 263);
        textAreaPurchaseHistory.setEditable(false);
        panelOutputAreaForPatient.add(textAreaPurchaseHistory);
        
        JLabel lblTotalSpent = new JLabel("Total Spent");
        lblTotalSpent.setFont(new Font("굴림", Font.BOLD, 18));
        lblTotalSpent.setBounds(958, 5, 160, 35);
        panelOutputAreaForPatient.add(lblTotalSpent);
        
        textField = new JTextField();
        textField.setBounds(956, 49, 160, 35);
        panelOutputAreaForPatient.add(textField);
        textField.setColumns(10);
	}
	
	protected static void displayMercList(JList<Merchandise> list, ArrayList<Merchandise> merchandises) {
		merchandises = refreshList(Inventory.getInstance(),   merchandises);
		DefaultListModel<Merchandise> model = new DefaultListModel<Merchandise>();	//the list will automatically be refreshed
		list.removeAll();
			for(Merchandise m : merchandises) {
				model.addElement(m);
			}
		list.setModel(model);
	}
	
	private static ListCellRenderer<? super Merchandise> createListRenderer(){
		 return new DefaultListCellRenderer() {
	          private Color background = new Color(0, 100, 255, 15);
	          private Color defaultBackground = (Color) UIManager.get("List.background");

	          @Override
	          public Component getListCellRendererComponent(JList<?> list, Object value, int index,
	                                                        boolean isSelected, boolean cellHasFocus) {
	              Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	              if (c instanceof JLabel) {
	                  JLabel label = (JLabel) c;
	                  Merchandise merc = (Merchandise) value;
	                  String isOTC = "";
	                  if(merc.getisOTC()) {
	                	  isOTC = "OTC";
	                  }
	                  else {
	                	  isOTC = "Rx";
	                  }
	                  
	                  label.setText(formatString(String.valueOf(merc.getMedicationID()), 9) +
	                		  formatString(merc.getName(), 33) + 
	                		  formatString(String.valueOf(merc.getQuantity()), 9) +
	                		  formatString(String.valueOf(merc.getPrice()), 11) + 
	                		  formatString(merc.getType().name(), 17) +
	                		  formatString(merc.getForm().name(), 17) +
	                		  formatString(isOTC, 8));
	                  if (!isSelected) {
	                      label.setBackground(index % 2 == 0 ? background : defaultBackground);
	                  }
	              }
	              return c;
	          }
	      };
	}
	
	private static String formatString(String input, int maxLength) {
		char[] temp = new char[maxLength - input.length()];
		for(int i = 0; i < maxLength - input.length(); i++) {
			temp[i] = ' ';
		}
		return input + new String(temp);
	}
	
	/*private static USER userToUsertype(User u) {	//this method is for the next iteration
		if(u == null) {
			return USER.GUEST;
		}
		
		USER result;
		String className = u.getClass().toString();
		if(className.equals("Patient")) {
			result = USER.PATIENT;
		}
		else if(className.equals("Owner")) {
			result = USER.OWNER;
		}
		else if(className.equals("Pharmacist")) {
			result = USER.PHARMACIST;
		}
		else {
			result = USER.DEVELOPER;
		}
		
		return result;
	}*/
	
	
	protected static ArrayList<Merchandise> refreshList(Inventory invInstance, ArrayList<Merchandise> oldList) {
		ArrayList<Merchandise> result = new ArrayList<Merchandise>();
		invInstance.updateFromDatabase();
		ArrayList<Merchandise> fresh = invInstance.getMerchandise();
		for(Merchandise i : oldList) {
			for(Merchandise j : fresh) {
				if(i.getMedicationID() == j.getMedicationID()) {
					result.add(j);
				}
			}
		}
		return result;
	}
	
	public static void setUserLoggedIn(long username) {
		usernameLoggedIn = username;
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
	public JFrame getFrame() {
		return frame;
	}
	
	public static void LoggedInAs(int ID) {
		userLoggedIn = ID;
	}
	public static void setCurrentList(ArrayList<Merchandise> newList) {
		currentList = newList;
	}
	
	public static void main(String[] args) {
		try {
			superDAO.setPassword("Motp1104#");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DisplayInitialScreen.displayInitialScreen(USER.PATIENT);
	}
}
