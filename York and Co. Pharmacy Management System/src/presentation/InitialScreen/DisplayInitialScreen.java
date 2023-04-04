package presentation.InitialScreen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

import java.awt.Dimension;

import javax.swing.ListCellRenderer;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;

import databaseDAO.superDAO;
import middleLayer.Users.*;
import presentation.DisplayLogin;
import presentation.USER;
import middleLayer.NegativeInputException;
import middleLayer.MerchandiseInventory.*;
import middleLayer.Orders.*;

import javax.swing.JList;
import javax.swing.JOptionPane;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;

import javax.swing.UIManager;

import javax.swing.JTextArea;
import java.awt.Rectangle;


public class DisplayInitialScreen{


	private static JFrame frame;

	private static Inventory inv;

	private static USER userType;
	
	private static ArrayList<Merchandise> currentList;
	



	

	
	public static void displayInitialScreen(USER user) {
		inv = Inventory.getInstance();
		userType = user;
		setCurrentList();

		JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("York and Co. Pharmacy Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(createContentPanel(userType));
        frame.setSize(new Dimension(1400, 800));
        frame.getContentPane().setLayout(null);


        frame.setVisible(true);
	}
	
	private static JPanel createContentPanel(USER user) {
		String loginButtonText = "Login";
		JPanel totalGUI = new JPanel();
		totalGUI.setPreferredSize(new Dimension(1400, 800));
		totalGUI.setBounds(new Rectangle(0, 0, 1400, 800));
		totalGUI.setFont(new Font("굴림", Font.BOLD, 18));
		totalGUI.setLayout(null);
		
		createPanels(totalGUI);
        if(user != USER.GUEST) {
        	loginButtonText = "Logout";
        }
        JButton btnLogin = new JButton(loginButtonText);
        btnLogin.setActionCommand(loginButtonText);
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(e.getActionCommand().equals("Login")) {
        			
        			DisplayLogin login = new DisplayLogin();
        		
        			login.displayLogin(frame);
        			
        		}
        		else { // some one logged in; if they click "log out"
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
				setCurrentList();
				InitialScreenPanelAll.displayMercList(currentList);
			
			}
		});

        		
		return totalGUI;
		
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
	

	
	
	
	
	
	public static void createPanels(JPanel totalGUI) {
		InitialScreenPanelAdmin adminPanel = new InitialScreenPanelAdmin(frame, currentList);
		InitialScreenPanelAll allPanel = new InitialScreenPanelAll(frame, inv, userType);
		InitialScreenPanelPatient patientPanel = new InitialScreenPanelPatient(frame );
		if(userType == USER.OWNER || userType == USER.PHARMACIST) {	//to display contents allowed to OWNER/PHARMACIST only
			adminPanel.createPanelVisibleToAdmin(totalGUI, inv, userType);
		}
		else if(userType == USER.DEVELOPER) {
			adminPanel.createPanelVisibleToAdmin(totalGUI, inv, userType); //for test purpose

		}
		if(userType == USER.PATIENT) {
			patientPanel.createPanalVisibleToPatient(totalGUI);
		}

		allPanel.createPanelVisibleToAll(totalGUI);
		
        createExtraContents(totalGUI);
	}
	

	
	public JFrame getFrame() {
		return frame;
	}
	
	public static void setCurrentList() {
		if(userType == USER.OWNER || userType == USER.PHARMACIST || userType == USER.DEVELOPER) {
			currentList = inv.getMerchandise();
		}
		else {
			currentList = inv.getOnlyOTCMerchandise();
		}
	}
	

	


	
	public static void main(String[] args) {
		try {
			superDAO.setPassword("Motp1104#");
		} catch (Exception e) {
			e.printStackTrace();
		}
		DisplayInitialScreen.displayInitialScreen(USER.GUEST);
	}
}
