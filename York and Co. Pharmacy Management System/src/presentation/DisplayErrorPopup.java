package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//import middleLayer.AuthenticateUser;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.Dimension;

public class DisplayErrorPopup {
	
	public static void displayErrorPopup(String error) {
		//creates frame
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frmPopup = new JFrame("Error");
        frmPopup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel_1 = new JPanel();
        frmPopup.setContentPane(panel_1);
        frmPopup.setSize(600, 400);
        frmPopup.getContentPane().setLayout(null);
        
        JPanel panelError = new JPanel();
        panelError.setBounds(12, 28, 562, 88);
        frmPopup.getContentPane().add(panelError);
        panelError.setLayout(null);
        
        JLabel lblError = new JLabel(error);
        lblError.setFont(new Font("굴림", Font.ITALIC, 20));
        lblError.setForeground(new Color(255, 0, 0));
        lblError.setHorizontalTextPosition(SwingConstants.CENTER);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setBounds(12, 10, 538, 68);
        panelError.add(lblError);
        
        JPanel panel = new JPanel();
        panel.setBounds(12, 249, 562, 52);
        frmPopup.getContentPane().add(panel);
        
        JButton btnOk = new JButton("Ok");
        btnOk.setFont(new Font("굴림", Font.BOLD, 20));
        btnOk.setPreferredSize(new Dimension(125, 35));
        panel.add(btnOk);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(12, 126, 562, 88);
        panel_1.add(panel_2);
        panel_2.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Please press Ok button to dismiss");
        lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(new Color(255, 0, 0));
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
        lblNewLabel.setBounds(0, 0, 562, 88);
        panel_2.add(lblNewLabel);
        btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frmPopup.dispose();
			}
		});
        frmPopup.setVisible(true);
	}
	

}
