package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Rectangle;

public class DisplayModify {
	JPanel panelForTextFields;
	JTextField userInputField;
	private JTextField inputFieldName;
	private JTextField inputFieldDOB;
	private JTextField inputFieldQty;
	private JTextField inputFieldPrice;
	private JTextField inputFieldType;
	private JTextField txtForm;
	
	public static void displayModify(USER userMode, ENTITY entityMode){
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("List");
        DisplayModify demo = new DisplayModify();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(demo.createContentPanel());
        frame.setSize(1400, 800);
        frame.setVisible(true);


        return;
	}
	
	public JPanel createContentPanel() {
		JPanel totalGUI = new JPanel();
		totalGUI.setFont(new Font("굴림", Font.BOLD, 18));
		totalGUI.setLayout(null);
        
        JLabel lblRequiredField = new JLabel("Red indicates required field");
        lblRequiredField.setFont(new Font("굴림", Font.PLAIN, 20));
        lblRequiredField.setForeground(new Color(255, 0, 0));
        //GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        
        JLabel lblNewLabel = new JLabel("Red indicates required fields");
        lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20));
        lblNewLabel.setForeground(new Color(255, 0, 0));
        lblNewLabel.setBounds(57, 45, 306, 40);
        totalGUI.add(lblNewLabel);
        
        JPanel panelRequiredForAll = new JPanel();
        panelRequiredForAll.setBounds(57, 129, 710, 40);
        totalGUI.add(panelRequiredForAll);
        panelRequiredForAll.setLayout(null);
        
        JLabel lblName = new JLabel("Name");
        lblName.setForeground(new Color(255, 0, 0));
        lblName.setFont(new Font("굴림", Font.BOLD, 18));
        lblName.setBounds(0, 0, 130, 40);
        panelRequiredForAll.add(lblName);
        
        inputFieldName = new JTextField();
        inputFieldName.setBounds(130, 0, 580, 40);
        panelRequiredForAll.add(inputFieldName);
        inputFieldName.setColumns(10);
        
        JPanel panelRequiredForAdmin = new JPanel();
        panelRequiredForAdmin.setBounds(57, 198, 710, 40);
        totalGUI.add(panelRequiredForAdmin);
        panelRequiredForAdmin.setLayout(null);
        
        JLabel lblDOB = new JLabel("Date of Birth");
        lblDOB.setForeground(new Color(255, 0, 0));
        lblDOB.setFont(new Font("굴림", Font.BOLD, 18));
        lblDOB.setBounds(0, 0, 130, 40);
        panelRequiredForAdmin.add(lblDOB);
        
        inputFieldDOB = new JTextField();
        inputFieldDOB.setBounds(130, 0, 580, 40);
        panelRequiredForAdmin.add(inputFieldDOB);
        inputFieldDOB.setColumns(10);
        
        JPanel panelRequiredForInv = new JPanel();
        panelRequiredForInv.setBounds(839, 129, 382, 109);
        totalGUI.add(panelRequiredForInv);
        panelRequiredForInv.setLayout(null);
        
        JLabel lblQuantity = new JLabel("Qty");
        lblQuantity.setFont(new Font("굴림", Font.BOLD, 18));
        lblQuantity.setForeground(new Color(255, 0, 0));
        lblQuantity.setBounds(new Rectangle(0, 0, 130, 40));
        lblQuantity.setBounds(0, 0, 100, 40);
        panelRequiredForInv.add(lblQuantity);
        
        JLabel lblPrice = new JLabel("Price");
        lblPrice.setForeground(new Color(255, 0, 0));
        lblPrice.setFont(new Font("굴림", Font.BOLD, 18));
        lblPrice.setBounds(new Rectangle(0, 0, 130, 40));
        lblPrice.setBounds(0, 69, 100, 40);
        panelRequiredForInv.add(lblPrice);
        
        inputFieldQty = new JTextField();
        inputFieldQty.setBounds(100, 0, 96, 40);
        panelRequiredForInv.add(inputFieldQty);
        inputFieldQty.setColumns(10);
        
        inputFieldPrice = new JTextField();
        inputFieldPrice.setBounds(100, 69, 96, 40);
        panelRequiredForInv.add(inputFieldPrice);
        inputFieldPrice.setColumns(10);
        
        JRadioButton rdbtnOTC = new JRadioButton("OTC");
        rdbtnOTC.setSelected(true);
        rdbtnOTC.setForeground(new Color(255, 0, 0));
        rdbtnOTC.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnOTC.setBounds(240, 10, 113, 23);
        
        
        
        JRadioButton rdbtnRx = new JRadioButton("Rx");
        rdbtnRx.setForeground(new Color(255, 0, 0));
        rdbtnRx.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnRx.setBounds(240, 69, 113, 23);
        
        /*rdbtnOTC.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e, )
        });*/
        
        ButtonGroup medGroup = new ButtonGroup();
        medGroup.add(rdbtnRx);
        medGroup.add(rdbtnOTC);
        
        panelRequiredForInv.add(rdbtnRx);
        panelRequiredForInv.add(rdbtnOTC);
        
        JPanel panelOptional = new JPanel();
        panelOptional.setBounds(57, 296, 710, 410);
        totalGUI.add(panelOptional);
        panelOptional.setLayout(null);
        
        JLabel lblType = new JLabel("Type");
        lblType.setForeground(new Color(0, 0, 0));
        lblType.setFont(new Font("굴림", Font.BOLD, 18));
        lblType.setBounds(0, 0, 130, 40);
        panelOptional.add(lblType);
        
        inputFieldType = new JTextField();
        inputFieldType.setBounds(130, 0, 580, 40);
        panelOptional.add(inputFieldType);
        inputFieldType.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Form");
        lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1.setForeground(new Color(0, 0, 0));
        lblNewLabel_1.setBounds(0, 70, 130, 40);
        panelOptional.add(lblNewLabel_1);
        
        txtForm = new JTextField();
        txtForm.setForeground(new Color(0, 0, 0));
        txtForm.setFont(new Font("굴림", Font.BOLD, 18));
        txtForm.setText("");
        txtForm.setBounds(130, 70, 580, 40);
        panelOptional.add(txtForm);
        txtForm.setColumns(10);
        
        JPanel panelLastAction = new JPanel();
        panelLastAction.setBounds(915, 629, 306, 77);
        totalGUI.add(panelLastAction);
        panelLastAction.setLayout(null);
        
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setFont(new Font("굴림", Font.BOLD, 18));
        btnConfirm.setBounds(12, 10, 130, 40);
        panelLastAction.add(btnConfirm);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setForeground(new Color(0, 0, 0));
        btnCancel.setFont(new Font("굴림", Font.BOLD, 18));
        btnCancel.setBounds(164, 11, 130, 40);
        panelLastAction.add(btnCancel);
		
		return totalGUI;
	}
	public static void main(String[] args) {
		displayModify(USER.OWNER, ENTITY.PRESCRIPTION);
		return;
	}
}
