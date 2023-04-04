package presentation;


import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;


import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


import middleLayer.Orders.*;
import java.awt.Color;

public class DisplayReport {
	private JTextField textFieldRevenue;
	private JTextField textFieldProfit;
	private JFrame frame;
	private JFrame superFrame;
	private Report report;
	


	
	public void displayReport(JFrame previous) {

		
		this.report = new Report();
		this.frame = new JFrame("Report");
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.superFrame = previous;
		this.superFrame.setEnabled(false);

		this.frame.setBounds(new Rectangle(0, 0, 900, 600));
		this.frame.getContentPane().setLayout(null);

		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("굴림", Font.BOLD, 18));
		btnExit.setBounds(592, 462, 197, 35);
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				superFrame.setEnabled(true);
				superFrame.toFront();
				frame.dispose();
			}
			
		});
		this.frame.getContentPane().add(btnExit);
		this.createLabels();
		this.createOutputFields();

		
		this.frame.setEnabled(true);
		this.frame.setVisible(true);
	}
	private void createLabels() {
		JLabel lblRevenue = new JLabel("Revenue");
		lblRevenue.setFont(new Font("굴림", Font.BOLD, 18));
		lblRevenue.setBounds(592, 15, 197, 35);
		this.frame.getContentPane().add(lblRevenue);
		
		JLabel lblNProfit = new JLabel("Profit");
		lblNProfit.setFont(new Font("굴림", Font.BOLD, 18));
		lblNProfit.setBounds(592, 105, 197, 35);
		this.frame.getContentPane().add(lblNProfit);
		
		JLabel lblSales = new JLabel("Sales History");
		lblSales.setFont(new Font("굴림", Font.BOLD, 18));
		lblSales.setBounds(592, 191, 197, 35);
		this.frame.getContentPane().add(lblSales);
	}
	
	private void createOutputFields() {
		this.textFieldRevenue = new JTextField();
		this.textFieldRevenue.setFont(new Font("굴림", Font.PLAIN, 18));
		this.textFieldRevenue.setBounds(592, 60, 197, 35);
		this.frame.getContentPane().add(textFieldRevenue);
		this.textFieldRevenue.setColumns(10);
		
		this.textFieldProfit = new JTextField();
		this.textFieldProfit.setFont(new Font("굴림", Font.PLAIN, 18));
		this.textFieldProfit.setBounds(592, 150, 197, 35);
		this.frame.getContentPane().add(textFieldProfit);
		this.textFieldProfit.setColumns(10);
		
		JTextArea textAreaSummary = new JTextArea();
		textAreaSummary.setBorder(new LineBorder(new Color(0, 0, 0)));

		textAreaSummary.setEditable(false);
		JScrollPane scrollPaneSummary = new JScrollPane(textAreaSummary);
		scrollPaneSummary.setBounds(12, 10, 568, 443);
		this.frame.getContentPane().add(scrollPaneSummary);
		this.setSummaryOutput(textAreaSummary);
		
		JTextArea textAreaSalesHistory = new JTextArea();
		textAreaSalesHistory.setEditable(false);
		JScrollPane scrollPaneSalesHistory = new JScrollPane(textAreaSalesHistory);
		scrollPaneSalesHistory.setBounds(592, 232, 197, 220);
		this.frame.getContentPane().add(scrollPaneSalesHistory);
		this.setSalesOutput(textAreaSalesHistory);
	}
	
	private void setSummaryOutput(JTextArea outputArea) {
		double revenue = 0;
		double profit = 0;
		ArrayList<String> summary = new ArrayList<String>();


		revenue = report.calculateRevenue();
		profit = report.calculateProfit();
		summary = report.seeSummaryOfSales();

		this.textFieldProfit.setText(String.format("%.2f", profit));
		this.textFieldRevenue.setText(String.format("%.2f", revenue));
		for(String s : summary) {
			outputArea.append(s);
		}
	}
	
	private void setSalesOutput(JTextArea outputArea) {
		//implement the logic here
	}
	

}
