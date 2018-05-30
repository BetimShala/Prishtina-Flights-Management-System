package GeneralUserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;
import org.jdesktop.swingx.prompt.PromptSupport;

import net.proteanit.sql.*;
import SharedPackage.DBconnection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import java.awt.Font;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JSpinner;

public class DeparturesPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblDepartures;
	private int id;
	Connection conn=null;
	
	ResultSet res=null;
	
	PreparedStatement pst=null;
	private JTextField txtTo;
	private JTextField txtFlightNr;
	private JTextField txtGate;
	private JTextField textField;
	private JComboBox cmbAirline,cmbStatuses;
	private JTextField textField_2;
	private JDateChooser dateChooser;
	private JSpinner spinner;
	int idOfDepartures=0;
	boolean editMode=false,deleteMode=false;
	public DeparturesPanel() {
		conn=DBconnection.sqlConnector();
		
		setSize(1050, 650);
		setLayout(null);
		
		
		JScrollPane spDepartures = new JScrollPane();
		spDepartures.setBorder(null);
		spDepartures.setBounds(0, 59, 1500, 487);
		add(spDepartures);
		//spDepartures.setBackground(Color.RED);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		
		
		
		tblDepartures = new JTable();
		tblDepartures.setGridColor(new Color(165,32,38));
		tblDepartures.setSelectionBackground(new Color(220, 20, 60));
		tblDepartures.setShowVerticalLines(false);
		tblDepartures.setBorder(new LineBorder(new Color(192, 192, 192), 3, true));
		tblDepartures.setForeground(new Color(165,32,38));
		tblDepartures.setTableHeader(null);
		tblDepartures.setOpaque(true);
		tblDepartures.setFillsViewportHeight(true);
		tblDepartures.setBackground(Color.WHITE);
		
		//DefaultTableModel model=(DefaultTableModel)tblDepartures.getModel();
		//DefaultTableModel model=(DefaultTableModel)tblDepartures.getModel();
				tblDepartures.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				tblDepartures.setRowHeight(20);
				Font objF = new Font("Segoi UI", Font.PLAIN, 12);
				tblDepartures.setFont(objF);
				DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
				defaultTableCellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
				tblDepartures.setDefaultRenderer(Object.class, defaultTableCellRenderer);
				spDepartures.setViewportView(tblDepartures);
		
		JButton btnAdd = new JButton("Add");
		
		btnAdd.setBounds(1316, 619, 174, 32);
		add(btnAdd);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTo.setBounds(10, 557, 46, 14);
		 add(lblTo);
		
		txtTo = new JTextField();
		txtTo.setBounds(10, 594, 156, 32);
		 add(txtTo);
		txtTo.setColumns(10);
		
		JLabel lblAirline = new JLabel("Airline Name");
		lblAirline.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAirline.setBounds(186, 553, 137, 22);
		 add(lblAirline);
		
		JLabel lblFlightNr = new JLabel("Flight No.");
		lblFlightNr.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFlightNr.setBounds(364, 557, 112, 22);
		 add(lblFlightNr);
		
		txtFlightNr = new JTextField();
		txtFlightNr.setColumns(10);
		txtFlightNr.setBounds(364, 594, 156, 32);
		 add(txtFlightNr);
		
		JLabel lblGate = new JLabel("Gate");
		lblGate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGate.setBounds(543, 557, 46, 14);
		 add(lblGate);
		
		txtGate = new JTextField();
		txtGate.setColumns(10);
		txtGate.setBounds(543, 594, 156, 32);
		 add(txtGate);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblStatus.setBounds(723, 553, 103, 22);
		 add(lblStatus);
		
		JButton btnEdit = new JButton("Edit");

		btnEdit.setBounds(1274, 23, 103, 32);
		 add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(1387, 23, 103, 32);
		 add(btnDelete);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(108, 23, 200, 25);
		 add(textField);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSearch.setBounds(10, 26, 56, 16);
		 add(lblSearch);
		
		JButton btnClean = new JButton("Clean");

		btnClean.setBounds(1316, 579, 174, 32);
		 add(btnClean);
		
		cmbAirline = new JComboBox();
		cmbAirline.setBounds(186, 594, 156, 32);
		 add(cmbAirline);
		
		cmbStatuses = new JComboBox();
		cmbStatuses.setSelectedIndex(-1);
		cmbStatuses.setBounds(723, 594, 156, 32);
		 add(cmbStatuses);
		
		JLabel lblSelectDate = new JLabel("Select Date");
		lblSelectDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelectDate.setBounds(907, 553, 121, 22);
		 add(lblSelectDate);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-dd-MM");
		dateChooser.setBounds(907, 594, 137, 32);
		 add(dateChooser);
		
		JLabel lblSelectTime = new JLabel("Select Time");
		lblSelectTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelectTime.setBounds(1074, 557, 121, 22);
		 add(lblSelectTime);
		

		Date date = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(date,null,null,Calendar.HOUR_OF_DAY);
	    spinner = new JSpinner(sm);
		spinner.setBounds(1074, 594, 149, 32);
		
		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner,"HH:mm:ss");
		spinner.setEditor(de);
		add(spinner);

	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(editMode==false)
				{
					btnAdd.setText("Add");

					if(!txtTo.getText().equals("") && !txtFlightNr.getText().equals("") &&
							!txtGate.getText().equals("") && cmbAirline.getSelectedIndex()!=-1 && 
							cmbStatuses.getSelectedIndex()!=-1 && !((JTextField)dateChooser.getDateEditor().getUiComponent()).getText().equals(""))
					{
						//JOptionPane.showMessageDialog(null,df.format(dateChooser.getDate()));
						try {


							
							String airID="select id from airline where airline = '"+cmbAirline.getSelectedItem().toString()+"'";
							pst=conn.prepareStatement(airID);
							int ida=0;
							res=pst.executeQuery();
							while(res.next())
							{
								ida=res.getRow();
							}
							String sql="insert into Departures values (default,'"+txtTo.getText()+"','"+df.format(dateChooser.getDate())+"',"
												+ "'"+sdf.format(spinner.getValue())+"',"
														+ ""+ida+","+txtFlightNr.getText()+","+txtGate.getText()+","
																+ "'"+cmbStatuses.getSelectedItem().toString()+"')";
													pst=conn.prepareStatement(sql);
													pst.execute();
													pst.close();
													updateTable();
													cleanFields();
													
							}
						catch (Exception e2) {
													e2.printStackTrace();;
												}
					}
						
					else 
					{
							JOptionPane.showMessageDialog(null,"Please fill all text fields");
					}
				}
				else if(editMode==true)
				{
					
					String updateArrival = "{call UpdateDepartures('"+idOfDepartures+"','"+txtTo.getText()+"'"
							+ ",'"+df.format(dateChooser.getDate())+"','"+sdf.format(spinner.getValue())+"','"+txtFlightNr.getText()+"','"+txtGate.getText()+"','"+cmbStatuses.getSelectedItem().toString()+"')}";
					try {
						CallableStatement UA = conn.prepareCall(updateArrival);
						UA.execute();
						updateTable();
						cleanFields();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
								
			}
		});
		
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				editMode=true;
				deleteMode=false;
				btnAdd.setText("Update");
				fillFields();
			}
		});
		
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				editMode=false;
				DefaultTableModel model=(DefaultTableModel)tblDepartures.getModel();
				idOfDepartures=(int)model.getValueAt(tblDepartures.getSelectedRow(), 0);
				int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete this flight");
				if(dialogResult == JOptionPane.YES_OPTION){
				  // Saving code here
					String deleteDepartures = "{call deleteDepartures('"+idOfDepartures+"')}";
					try {
						CallableStatement UA = conn.prepareCall(deleteDepartures);
						UA.execute();
						updateTable();
						cleanFields();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnClean.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) 
			{
				cleanFields();
				btnAdd.setText("Add");
			}
		});
		//JOptionPane.showMessageDialog(null,sdf.format(spinner.getValue()));
		
		
		//Call functions
		updateTable();
		fillCmbAirline();
		fillCmbStatuses();
		
	}
	public void fillFields()
	{
		DefaultTableModel model=(DefaultTableModel)tblDepartures.getModel();
		idOfDepartures=(int)model.getValueAt(tblDepartures.getSelectedRow(), 0);
		String to =(String)model.getValueAt(tblDepartures.getSelectedRow(),1);
		String airlineName =(String)model.getValueAt(tblDepartures.getSelectedRow(),2);
		Date date =(Date)model.getValueAt(tblDepartures.getSelectedRow(),3);
		Time time =(Time)model.getValueAt(tblDepartures.getSelectedRow(),4);
		int flightNr =(int)model.getValueAt(tblDepartures.getSelectedRow(),5);
		int gate =(int)model.getValueAt(tblDepartures.getSelectedRow(),6);
		String status =(String)model.getValueAt(tblDepartures.getSelectedRow(),7);
		
		txtTo.setText(to);
		cmbAirline.setSelectedItem(airlineName);
		dateChooser.setDate(date);
		spinner.setValue(time);
		txtFlightNr.setText(String.valueOf(flightNr));
		txtGate.setText(String.valueOf(gate));
		cmbStatuses.setSelectedItem(status);
	}
	public void cleanFields()
	{
		txtFlightNr.setText("");
		txtTo.setText("");	
		txtGate.setText("");
		cmbAirline.setSelectedIndex(-1);
		cmbStatuses.setSelectedIndex(-1);
		editMode=false;
		

	}
	
	public void fillCmbAirline() 
	{
		ArrayList<String> list = new ArrayList<String>();
		try {
		String fillCmbAirline = "select distinct airline from airline";
		
			pst=conn.prepareStatement(fillCmbAirline);
			res=pst.executeQuery();
			//JOptionPane.showMessageDialog(null,res.getFetchSize());
			while(res.next())
			{
				list.add(res.getString(1));
			}
			cmbAirline.setModel(new DefaultComboBoxModel(list.toArray()));
			cmbAirline.setSelectedIndex(-1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void fillCmbStatuses()
	{
		ArrayList<String> list = new ArrayList<String>();
		list.add("Arrived");
		list.add("Scheduled");
		list.add("On Time");
		list.add("Delayed");

		cmbStatuses.setModel(new DefaultComboBoxModel(list.toArray()));
		cmbStatuses.setSelectedIndex(-1);
		
		
	}
	
	public void updateTable()
	{		
		try 
		{
			
			String sql="SELECT Departures.id as 'ID',Departures.to as 'TO', airline.airline as 'AIRLINE' ,Departures.date as 'DATE',Departures.time as 'TIME,"
					+ "Departures.flightNr as 'FLIGHT #',Departures.gate as 'GATE',Departures.status as 'STATUS'\r\n" + 
					"FROM Departures\r\n" + 
					"INNER JOIN airline ON Departures.airlineId=airline.id";
			pst=conn.prepareStatement(sql);
			res=pst.executeQuery();
			//vendosja e rezultatit ne JTable; duhet te importohet rs2xml.jar
			tblDepartures.setModel(DbUtils.resultSetToTableModel(res));	
			
			pst.close();
			
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}

