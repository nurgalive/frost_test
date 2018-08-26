package org.fraunhofer.test;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.awt.Font;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FrostGUI {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrostGUI window = new FrostGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
Connection connection = null;
private JTextField textField;
private JTextField textField_1;

String q;

	/**
	 * Create the application.
	 */
	public FrostGUI() {
		connection=ConnectionPostgres.dbConnector();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 761, 531);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setBounds(33, 64, 251, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(310, 64, 251, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		q = textField.getText();
		//System.out.println(q);
		
		//2018-05-31 14:50:46.545+02
		// Timestamp timestamp = new Timestamp();
		// q = Timestamp.parse("2018-05-31 14:50:46.545+02");
		
		//q = ZonedDateTime.parse("2018-05-31 14:50:46.545+02");
		
		//'2018-05-31 14:50:46.545+02'
		
		JButton btnNewButton = new JButton("Load Data");
		btnNewButton.setBounds(619, 61, 117, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					String query = "select  \"ID\",  \"RESULT_TIME\" , \"RESULT_NUMBER\"  "
							+ "from public.\"OBSERVATIONS\" where \"RESULT_TIME\" <'" + q + "';";

					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs= pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 107, 703, 356);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		
		
		JLabel lblChooseBalancingGroup = new JLabel("Choose balancing group");
		lblChooseBalancingGroup.setFont(new Font("Dialog", Font.BOLD, 16));
		lblChooseBalancingGroup.setBounds(234, 12, 236, 29);
		frame.getContentPane().add(lblChooseBalancingGroup);
		
		JLabel lblTimePeriod = new JLabel("Time period");
		lblTimePeriod.setBounds(256, 42, 94, 15);
		frame.getContentPane().add(lblTimePeriod);
		

		
		
	}
}
