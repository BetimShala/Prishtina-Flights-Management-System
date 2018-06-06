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
import javax.swing.table.JTableHeader;

//import org.jdatepicker.JDatePicker;
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
		
		setSize(1560, 720);
		setLayout(null);
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 102, 153), 10));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		
		JScrollPane spDepartures = new JScrollPane();
		spDepartures.setBorder(null);
		spDepartures.setBounds(20, 80, 1520, 487);
		add(spDepartures);
		//spDepartures.setBackground(Color.RED);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
		
		
		
		tblDepartures = new JTable();
		//tblDepartures.setGridColor(new Color(165,32,38));
		//tblDepartures.setSelectionBackground(new Color(220, 20, 60));
		tblDepartures.setShowVerticalLines(false);
		//tblDepartures.setBorder(new LineBorder(new Color(192, 192, 192), 3, true));
		//tblDepartures.setForeground(new Color(165,32,38));
		//tblDepartures.setTableHeader(null);
		tblDepartures.setOpaque(true);
		tblDepartures.setFillsViewportHeight(true);
		tblDepartures.setBackground(Color.WHITE);
		
		JTableHeader header = tblDepartures.getTableHeader();
		header.setBackground(Color.WHITE);
		header.setFont(new Font("Tahoma",Font.PLAIN,17));
		
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
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEdit.setBackground(new Color(0, 102, 153));
		btnEdit.setForeground(Color.WHITE);

		btnEdit.setBounds(1321, 23, 103, 32);
		 add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBackground(new Color(0, 102, 153));
		btnDelete.setBounds(1437, 23, 103, 32);
		 add(btnDelete);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(106, 23, 424, 32);
		 add(textField);
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSearch.setBounds(38, 32, 56, 16);
		 add(lblSearch);
		

		Date date = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(date,null,null,Calendar.HOUR_OF_DAY);
		
		JPanel pnlCRUD = new JPanel();
		pnlCRUD.setBackground(Color.WHITE);
		pnlCRUD.setBounds(20, 571, 1520, 109);
		add(pnlCRUD);
		pnlCRUD.setLayout(null);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(12, 34, 46, 14);
		pnlCRUD.add(lblTo);
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		txtTo = new JTextField();
		txtTo.setBounds(12, 66, 156, 32);
		pnlCRUD.add(txtTo);
		txtTo.setColumns(10);
		
		JLabel lblAirline = new JLabel("Airline Name");
		lblAirline.setBounds(201, 30, 137, 22);
		pnlCRUD.add(lblAirline);
		lblAirline.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		cmbAirline = new JComboBox();
		cmbAirline.setBounds(199, 65, 156, 32);
		pnlCRUD.add(cmbAirline);
		cmbAirline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblFlightNr = new JLabel("Flight No.");
		lblFlightNr.setBounds(391, 30, 112, 22);
		pnlCRUD.add(lblFlightNr);
		lblFlightNr.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		txtFlightNr = new JTextField();
		txtFlightNr.setBounds(391, 66, 156, 32);
		pnlCRUD.add(txtFlightNr);
		txtFlightNr.setColumns(10);
		
		JLabel lblGate = new JLabel("Gate");
		lblGate.setBounds(582, 34, 46, 14);
		pnlCRUD.add(lblGate);
		lblGate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		txtGate = new JTextField();
		txtGate.setBounds(580, 66, 156, 32);
		pnlCRUD.add(txtGate);
		txtGate.setColumns(10);
		
		cmbStatuses = new JComboBox();
		cmbStatuses.setBounds(774, 65, 156, 32);
		pnlCRUD.add(cmbStatuses);
		cmbStatuses.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cmbStatuses.setSelectedIndex(-1);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(774, 30, 103, 22);
		pnlCRUD.add(lblStatus);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel lblSelectDate = new JLabel("Select Date");
		lblSelectDate.setBounds(963, 30, 121, 22);
		pnlCRUD.add(lblSelectDate);
		lblSelectDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(963, 65, 137, 32);
		pnlCRUD.add(dateChooser);
		dateChooser.setDateFormatString("yyyy-dd-MM");
		
		JLabel lblSelectTime = new JLabel("Select Time");
		lblSelectTime.setBounds(1125, 24, 121, 22);
		pnlCRUD.add(lblSelectTime);
		lblSelectTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinner = new JSpinner(sm);
		spinner.setBounds(1125, 60, 149, 32);
		pnlCRUD.add(spinner);
		
		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner,"HH:mm:ss");
		spinner.setEditor(de);
		
		JButton btnClean = new JButton("Clean");
		btnClean.setBounds(1334, 31, 174, 32);
		pnlCRUD.add(btnClean);
		btnClean.setForeground(Color.WHITE);
		btnClean.setBackground(new Color(0, 102, 153));
		btnClean.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(1334, 66, 174, 32);
		pnlCRUD.add(btnAdd);
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(new Color(0, 102, 153));
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
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
		btnClean.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) 
			{
				cleanFields();
				btnAdd.setText("Add");
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
			
			String sql="SELECT Departures.id as 'ID', Departures.to as 'TO', airline.airline as 'AIRLINE', Departures.date as 'DATE', Departures.time as 'TIME',"
					+ "Departures.flightNr as 'FLIGHT', Departures.gate as 'GATE', Departures.status as 'STATUS'\r\n" + 
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

