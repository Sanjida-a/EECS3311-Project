package presentation;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.Point;
import java.awt.Insets;
import java.awt.event.KeyEvent;

public class DisplayList {
	JPanel panelForTextFields;
	JTextField userInputField;
	private JButton btnAddToList;
	
	public static void displayList(USER userMode, ENTITY entityMode) {
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("List");
        DisplayList demo = new DisplayList();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(demo.createContentPanel());
        frame.setSize(1400, 800);
        frame.setVisible(true);
        return;
	}
	
	public JPanel createContentPanel() {
		JPanel totalGUI = new JPanel();
        totalGUI.setLayout(null);
		 // TextFields Panel Container
        panelForTextFields = new JPanel();
        panelForTextFields.setLayout(null);
        panelForTextFields.setLocation(60, 120);
        panelForTextFields.setSize(1000, 70);
        totalGUI.add(panelForTextFields);

        // Username Textfield
        userInputField = new JTextField(8);
        userInputField.setCaretColor(new Color(192, 192, 192));
        userInputField.setFont(new Font("Yu Gothic UI", Font.ITALIC, 28));
        userInputField.setText("Enter name of a medication");
        userInputField.setLocation(0, 10);
        userInputField.setSize(1000, 50);
        panelForTextFields.add(userInputField);
        
        JPanel panelForAdd = new JPanel();
        panelForAdd.setBounds(60, 38, 150, 55);
        totalGUI.add(panelForAdd);
        
        btnAddToList = new JButton("Add");
        btnAddToList.setLocation(new Point(60, 36));
        btnAddToList.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddToList.setPreferredSize(new Dimension(150, 40));
        btnAddToList.setMinimumSize(new Dimension(150, 50));
        btnAddToList.setMaximumSize(new Dimension(200, 50));
        btnAddToList.setFont(new Font("굴림", Font.PLAIN, 20));
        panelForAdd.add(btnAddToList);
        
        JPanel panelForModify = new JPanel();
        panelForModify.setBounds(350, 38, 150, 55);
        totalGUI.add(panelForModify);
        
        JButton btnModifyItem = new JButton("Modify");
        btnModifyItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelForModify.add(btnModifyItem);
        btnModifyItem.setPreferredSize(new Dimension(150, 40));
        btnModifyItem.setFont(new Font("굴림", Font.PLAIN, 20));
        
        JPanel panelForDelete = new JPanel();
        panelForDelete.setBounds(630, 38, 150, 55);
        totalGUI.add(panelForDelete);
        
        JButton btnDeleteItem = new JButton("Delete");
        panelForDelete.add(btnDeleteItem);
        btnDeleteItem.setFont(new Font("굴림", Font.PLAIN, 20));
        btnDeleteItem.setPreferredSize(new Dimension(150, 40));
        
        JPanel panelForCMD = new JPanel();
        panelForCMD.setBounds(860, 38, 200, 55);
        totalGUI.add(panelForCMD);
        
        JButton btnChangeDisplay = new JButton("Change display\r\n");
        panelForCMD.add(btnChangeDisplay);
        btnChangeDisplay.setPreferredSize(new Dimension(200, 40));
        btnChangeDisplay.setFont(new Font("굴림", Font.PLAIN, 20));
        
        JPanel panelForExit = new JPanel();
        panelForExit.setBounds(1115, 38, 222, 55);
        totalGUI.add(panelForExit);
        
        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("굴림", Font.PLAIN, 20));
        btnExit.setPreferredSize(new Dimension(150, 40));
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.setAlignmentY(Component.TOP_ALIGNMENT);
        panelForExit.add(btnExit);
        
        JPanel panelForSearch = new JPanel();
        panelForSearch.setBounds(1115, 120, 222, 60);
        totalGUI.add(panelForSearch);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setMargin(new Insets(2, 2, 2, 2));
        btnSearch.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSearch.setPreferredSize(new Dimension(150, 50));
        btnSearch.setFont(new Font("굴림", Font.PLAIN, 20));
        panelForSearch.add(btnSearch);
        
        JPanel panelForDisplayPrescription = new JPanel();
        panelForDisplayPrescription.setBounds(1115, 221, 220, 220);
        totalGUI.add(panelForDisplayPrescription);
        
        JButton btnDisplayPrescription = new JButton("<html>Display<br><br>prescription<br><br>history</html>");
        btnDisplayPrescription.setFont(new Font("굴림", Font.PLAIN, 20));
        btnDisplayPrescription.setPreferredSize(new Dimension(150, 200));
        panelForDisplayPrescription.add(btnDisplayPrescription);
        
        JPanel panelForChangeUser = new JPanel();
        panelForChangeUser.setBounds(1115, 491, 220, 220);
        totalGUI.add(panelForChangeUser);
        
        JButton btnChangeUser = new JButton("<html>Change<br><br>user</html>");
        btnChangeUser.setFont(new Font("굴림", Font.PLAIN, 20));
        btnChangeUser.setPreferredSize(new Dimension(150, 200));
        panelForChangeUser.add(btnChangeUser);
        
        JPanel panelForList = new JPanel();
        panelForList.setBounds(60, 221, 1000, 490);
        totalGUI.add(panelForList);
        
        JList list = new JList();
        list.setVisibleRowCount(15);
        list.setBorder(new LineBorder(new Color(0, 0, 0)));
        list.setPreferredSize(new Dimension(1000, 490));
        panelForList.add(list);
		return totalGUI;
		
	}
	
	
	
	
	public static void main(String[] args) {
		displayList(USER.OWNER, ENTITY.PRESCRIPTION);
		return;
	}

}
