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
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frmPopup = new JFrame("Error");
        
        frmPopup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmPopup.setContentPane(new JPanel());
        frmPopup.setSize(600, 400);
        frmPopup.getContentPane().setLayout(null);
        
        JPanel panelError = new JPanel();
        panelError.setBounds(12, 81, 562, 88);
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
