package presentation.InitialScreen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import middleLayer.MerchandiseInventory.Inventory;
import middleLayer.MerchandiseInventory.MERCHANDISE_TYPE;
import middleLayer.MerchandiseInventory.Merchandise;
import middleLayer.Users.Owner;
import presentation.DisplayDescription;
import presentation.USER;

public class InitialScreenPanelAll implements ActionListener {
	private JTextField inputKeyword;
	private static USER userType;
	private String operationResult;
	private static JLabel lblOperationResult;
	private static ArrayList<Merchandise> currentList;
	private JFrame frame;
	private static JList<Merchandise> outputList;
	private Inventory inv;
	private JComboBox comboBox;
	
	private InitialScreenPanelAll() {
		//left empty to disable default constructor
	}
	public InitialScreenPanelAll(JFrame currentFrame, Inventory invInstance, USER user) {
		this.frame = currentFrame;
		userType = user;
		this.inv = invInstance;
		if(userType == USER.PATIENT || userType == USER.GUEST) {
			currentList = this.inv.getOnlyOTCMerchandise();
		}
		else {
			currentList = this.inv.getMerchandise();
		}
	}
	public void createPanelVisibleToAll(JPanel totalGUI ) {

		JPanel panelVisibleToAll = new JPanel();
        panelVisibleToAll.setBounds(45, 40, 944, 465);
        panelVisibleToAll.setLayout(null);
          
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(819, 0, 125, 35);
        btnSearch.setFont(new Font("굴림", Font.BOLD, 20));
        panelVisibleToAll.add(btnSearch);
        btnSearch.addActionListener(this);
        
        createInputFields(panelVisibleToAll);
        createOutputFields(panelVisibleToAll);
        createColumnHeaders(panelVisibleToAll);
     
        totalGUI.add(panelVisibleToAll);
        displayMercList( currentList);
	}
	
	private void createInputFields(JPanel panel) {
		inputKeyword = new JTextField();
        inputKeyword.setBounds(0, 0, 650, 35);
        panel.add(inputKeyword);
        inputKeyword.setColumns(40);
        
        comboBox = new JComboBox();
        comboBox.setBounds(650, 0, 145, 35);
        comboBox.setBorder(new LineBorder(new Color(0, 0, 0)));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Name", "Type"}));
        comboBox.setSelectedIndex(0);
        comboBox.setFont(new Font("굴림", Font.BOLD, 18));
        comboBox.setBackground(new Color(255, 255, 255));
        panel.add(comboBox);
	}
	
	private void createOutputFields(JPanel panel) {
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
        panel.add(scrollPane);
        
        lblOperationResult = new JLabel();
        lblOperationResult.setForeground(new Color(255, 0, 0));
        lblOperationResult.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 18));
        lblOperationResult.setBounds(0, 425, 944, 40);
        panel.add(lblOperationResult);
	}
	
	private void createColumnHeaders(JPanel panel) {
		JPanel panelColumn = new JPanel();
        panelColumn.setBounds(0, 56, 944, 20);
        panel.add(panelColumn);
        panelColumn.setLayout(null);
        
        JButton btnColumnName = new JButton("Name");
        btnColumnName.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnName.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnName.setHorizontalTextPosition(SwingConstants.LEFT);
        btnColumnName.setBounds(75, 0, 290, 20);
        btnColumnName.addActionListener(this);
        panelColumn.add(btnColumnName);
        
        JButton btnColumnQty = new JButton("Qty");
        btnColumnQty.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnQty.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnQty.setBounds(364, 0, 80, 20);
        btnColumnQty.addActionListener(this);
        panelColumn.add(btnColumnQty);
        
        JButton btnColumnPrice = new JButton("Price");
        btnColumnPrice.setHorizontalAlignment(SwingConstants.LEFT);
        btnColumnPrice.setFont(new Font("굴림", Font.BOLD, 12));
        btnColumnPrice.setBounds(443, 0, 100, 20);
        btnColumnPrice.addActionListener(this);
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
	public static void displayMercList( ArrayList<Merchandise> merchandises) {
		currentList = refreshList(Inventory.getInstance(),   merchandises);
		DefaultListModel<Merchandise> model = new DefaultListModel<Merchandise>();	//the list will automatically be refreshed
		outputList.removeAll();
		for(Merchandise m : currentList) {
			model.addElement(m);
		}
		outputList.setModel(model);
	}
	public static ArrayList<Merchandise> refreshList(Inventory invInstance, ArrayList<Merchandise> oldList) {
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
	
	private void search() {
		try {
			String _inputKeyword = inputKeyword.getText().toUpperCase();
			
			// throws an exception if input is empty
			if(_inputKeyword.isEmpty()) {
				throw new NullPointerException();
			}
			
			String searchBy = (String)comboBox.getSelectedItem();
			
			ArrayList<Merchandise> methodResult = new ArrayList<Merchandise>();

			Owner owner1 = new Owner(1,1);
			
			if(searchBy.compareTo("Name") == 0) { //checking drop down list value
				methodResult = owner1.searchMedicineByName(_inputKeyword, userType);
			}
			else if(searchBy.compareTo("Type") == 0) {
				MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(_inputKeyword);
				methodResult = owner1.searchMedicineByType(_inputFieldType, userType);
			}
			
			if(methodResult.isEmpty()) {
				operationResult = _inputKeyword + " is not in the inventory";
				setOperationResult(operationResult);
			}
			else {
				operationResult = "";
				setOperationResult(operationResult);
			}
			currentList = methodResult;
			displayMercList( currentList);
		}	
		catch (NullPointerException exception) {
			JOptionPane.showMessageDialog(frame,"Search keyword is empty. Input required: type something in the search box", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}
		catch (IllegalArgumentException exception) {
			JOptionPane.showMessageDialog(frame,"Merchandise Type is invalid. Please enter a valid Merchandise Type: Cough, Cold, Fever, Sinus", "Invalid input", JOptionPane.WARNING_MESSAGE);
		}

	
	}
	
	public static void setOperationResult(String string) {
		lblOperationResult.setText(string);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String actionCommand = e.getActionCommand();
		if(actionCommand.equalsIgnoreCase("Search")) {
			this.search();
		}
		else if(actionCommand.equalsIgnoreCase("Name")) {
			displayMercList( inv.displayAlphabetically(currentList));
		}
		else if(actionCommand.equalsIgnoreCase("Qty")) {
			displayMercList( inv.displayByQuantity(currentList));
		}
		else if(actionCommand.equalsIgnoreCase("Price")) {
			displayMercList(inv.displayByPrice(currentList));
		}
		else {
			//left empty for further expansion
		}

	}
	

}
