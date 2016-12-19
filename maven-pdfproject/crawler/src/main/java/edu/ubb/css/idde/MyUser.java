package edu.ubb.css.idde;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class MyUser extends JFrame {
	
	private JPanel buttonPanel = new JPanel();
	private JPanel labelPanel = new JPanel();
	private JPanel contantPanel = new JPanel();
	
	private JButton tableButton = new JButton("Persons Table");
	private JTable personTable;
	
	private JLabel message = new JLabel();
	
	public MyUser() {
		super("Client");
		this.setBounds(0,0,500,300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(contantPanel);
		contantPanel.setLayout(new BorderLayout());
		
		buttonPanel.setLayout(new FlowLayout());
		contantPanel.add(buttonPanel,BorderLayout.SOUTH);

		buttonPanel.add(tableButton);
		
		Object rowsData[][] = {
								{"1", "20", "Lilla", "Str.Garibaldi Nr.24","00210548"},
								{"2", "18", "Isti", "Str.Utca Nr.910", "014785469"}
								};
		String colums[] = {"ID", "Age", "Name", "Address", "Phone Number"};
		
		personTable = new JTable(rowsData, colums);
		contantPanel.add(personTable,BorderLayout.CENTER);
		
		contantPanel.add(personTable.getTableHeader(), BorderLayout.NORTH);
		/*message.setText("Persons Table");
		labelPanel.add(message);
		contantPanel.add(labelPanel, BorderLayout.NORTH);*/
		this.setResizable(false);
		this.setVisible(true);
	}
	
}
