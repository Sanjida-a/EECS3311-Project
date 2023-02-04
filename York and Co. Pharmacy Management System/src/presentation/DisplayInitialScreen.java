package presentation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.JProgressBar;
import javax.swing.Box;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.JFormattedTextField;

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
	
	public void displayInitialScreen(USER user) {
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
		
		if(user == USER.OWNER || user == USER.PHARMACIST) {
			this.createPanelVisibleToAdmin(totalGUI);
		}
		//this.createPanelVisibleToAdmin(totalGUI);
        this.createExtraContents(totalGUI);
        this.createPanelVisibleToAll(totalGUI);
        		
		return totalGUI;
		
	}
	
	private void createPanelVisibleToAdmin(JPanel totalGUI) {
		JPanel panelVisibleToAdmin = new JPanel();
        panelVisibleToAdmin.setBounds(45, 536, 944, 194);
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
        
        //isOTC = rdbtnOTC.isEnabled();
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("굴림", Font.BOLD, 20));
        btnAdd.setBounds(500, 135, 125, 35);
        panelVisibleToAdmin.add(btnAdd);
        
        JButton btnModify = new JButton("Modify");
        btnModify.setFont(new Font("굴림", Font.BOLD, 20));
        btnModify.setBounds(637, 135, 125, 35);
        panelVisibleToAdmin.add(btnModify);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("굴림", Font.BOLD, 20));
        btnDelete.setBounds(774, 135, 125, 35);
        panelVisibleToAdmin.add(btnDelete);
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
        inputKeyword.setBounds(0, 0, 807, 35);
        panelVisibleToAll.add(inputKeyword);
        inputKeyword.setColumns(40);
        //searchKeyword = inputKeyword.getText();
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("굴림", Font.BOLD, 20));
        btnSearch.setBounds(819, 0, 125, 35);
        panelVisibleToAll.add(btnSearch);
        
        JList list = new JList();
        list.setBorder(new LineBorder(new Color(0, 0, 0)));
        list.setFont(new Font("굴림", Font.PLAIN, 15));
        list.setBounds(0, 58, 944, 397);
        panelVisibleToAll.add(list);
		
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
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//displayInitialScreen(USER.PATIENT); 
		DisplayInitialScreen screen = new DisplayInitialScreen();
		screen.displayInitialScreen(USER.OWNER);
	}
}
