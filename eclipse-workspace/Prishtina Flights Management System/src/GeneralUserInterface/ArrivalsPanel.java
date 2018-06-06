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

public class ArrivalsPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblArrivals;
	private int id;
	Connection conn=null;
	
	ResultSet res=null;
	
	PreparedStatement pst=null;
	private JTextField txtFrom;
	private JTextField txtFlightNr;
	private JTextField txtGate;
	private JTextField textField;
	private JComboBox cmbAirline,cmbStatuses;
	private JTextField textField_2;
	private JDateChooser dateChooser;
	private JSpinner spinner;
	int idOfArrivals=0;
	boolean editMode=false,deleteMode=false;
	public ArrivalsPanel() {
		conn=DBconnection.sqlConnector();
		
		setSize(1560, 720);
		setLayout(null);
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 102, 153), 10));
		
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		
		JScrollPane spArrivals = new JScrollPane();
		spArrivals.setBorder(null);
		spArrivals.setBounds(20, 80, 1520, 487);
		add(spArrivals);
		//spArrivals.setBackground(Color.RED);
		
		tblArrivals = new JTable();
		//tblArrivals.setGridColor(new Color(165,32,38));
		//tblArrivals.setSelectionBackground(new Color(220, 20, 60));
		tblArrivals.setShowVerticalLines(false);
		//tblArrivals.setBorder(new LineBorder(new Color(192, 192, 192), 3, true));
		//tblArrivals.setForeground(new Color(165,32,38));
		//tblArrivals.setTableHeader(null);
		tblArrivals.setOpaque(true);
		tblArrivals.setFillsViewportHeight(true);
		tblArrivals.setBackground(Color.WHITE);
		
		JTableHeader header = tblArrivals.getTableHeader();
		header.setBackground(Color.WHITE);
		header.setFont(new Font("Tahoma",Font.PLAIN,17));
		
		//DefaultTableModel model=(DefaultTableModel)tblArrivals.getModel();
		tblArrivals.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblArrivals.setRowHeight(20);
		Font objF = new Font("Segoi UI", Font.PLAIN, 12);
		tblArrivals.setFont(objF);
		DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
		defaultTableCellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		tblArrivals.setDefaultRenderer(Object.class, defaultTableCellRenderer);
		spArrivals.setViewportView(tblArrivals);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setBackground(new Color(0, 102, 153));
		btnEdit.setForeground(Color.WHITE);
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));

		btnEdit.setBounds(1321, 23, 103, 32);
		 add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setForeground(Color.WHITE);
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
		pnlCRUD.setBounds(20, 569, 1520, 112);
		add(pnlCRUD);
		pnlCRUD.setLayout(null);
		
		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(12, 34, 46, 14);
		pnlCRUD.add(lblFrom);
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		txtFrom = new JTextField();
		txtFrom.setBounds(12, 65, 156, 32);
		pnlCRUD.add(txtFrom);
		txtFrom.setColumns(10);
		
		JLabel lblAirline = new JLabel("Airline Name");
		lblAirline.setBounds(197, 30, 137, 22);
		pnlCRUD.add(lblAirline);
		lblAirline.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		cmbAirline = new JComboBox();
		cmbAirline.setBounds(197, 65, 156, 32);
		pnlCRUD.add(cmbAirline);
		
		JLabel lblFlightNr = new JLabel("Flight No.");
		lblFlightNr.setBounds(384, 30, 112, 22);
		pnlCRUD.add(lblFlightNr);
		lblFlightNr.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		txtFlightNr = new JTextField();
		txtFlightNr.setBounds(384, 65, 156, 32);
		pnlCRUD.add(txtFlightNr);
		txtFlightNr.setColumns(10);
		
		txtGate = new JTextField();
		txtGate.setBounds(568, 65, 156, 32);
		pnlCRUD.add(txtGate);
		txtGate.setColumns(10);
		
		JLabel lblGate = new JLabel("Gate");
		lblGate.setBounds(568, 34, 46, 14);
		pnlCRUD.add(lblGate);
		lblGate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		cmbStatuses = new JComboBox();
		cmbStatuses.setBounds(755, 65, 156, 32);
		pnlCRUD.add(cmbStatuses);
		cmbStatuses.setSelectedIndex(-1);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(755, 30, 103, 22);
		pnlCRUD.add(lblStatus);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(938, 65, 137, 32);
		pnlCRUD.add(dateChooser);
		dateChooser.setDateFormatString("yyyy-dd-MM");
		
		JLabel lblSelectDate = new JLabel("Select Date");
		lblSelectDate.setBounds(938, 30, 121, 22);
		pnlCRUD.add(lblSelectDate);
		lblSelectDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinner = new JSpinner(sm);
		spinner.setBounds(1106, 65, 149, 32);
		pnlCRUD.add(spinner);
		
		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner,"HH:mm:ss");
		spinner.setEditor(de);
		
		JLabel lblSelectTime = new JLabel("Select Time");
		lblSelectTime.setBounds(1104, 30, 121, 22);
		pnlCRUD.add(lblSelectTime);
		lblSelectTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JButton btnClean = new JButton("Clean");
		btnClean.setBounds(1311, 26, 174, 32);
		pnlCRUD.add(btnClean);
		btnClean.setForeground(Color.WHITE);
		btnClean.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClean.setBackground(new Color(0, 102, 153));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(1311, 64, 174, 32);
		pnlCRUD.add(btnAdd);
		btnAdd.setBackground(new Color(0, 102, 153));
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					{
						if(editMode==false)
						{
							btnAdd.setText("Add");
		
							if(!txtFrom.getText().equals("") && !txtFlightNr.getText().equals("") &&
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
									String sql="insert into arrivals values (default,'"+txtFrom.getText()+"','"+df.format(dateChooser.getDate())+"',"
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
							
							String updateArrival = "{call UpdateArrivals('"+idOfArrivals+"','"+txtFrom.getText()+"'"
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
				DefaultTableModel model=(DefaultTableModel)tblArrivals.getModel();
				idOfArrivals=(int)model.getValueAt(tblArrivals.getSelectedRow(), 0);
				int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete this flight");
				if(dialogResult == JOptionPane.YES_OPTION){
				  // Saving code here
					String deleteArrivals = "{call deleteArrivals('"+idOfArrivals+"')}";
					try {
						CallableStatement UA = conn.prepareCall(deleteArrivals);
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
		DefaultTableModel model=(DefaultTableModel)tblArrivals.getModel();
		idOfArrivals=(int)model.getValueAt(tblArrivals.getSelectedRow(), 0);
		String from =(String)model.getValueAt(tblArrivals.getSelectedRow(),1);
		String airlineName =(String)model.getValueAt(tblArrivals.getSelectedRow(),2);
		Date date =(Date)model.getValueAt(tblArrivals.getSelectedRow(),3);
		Time time =(Time)model.getValueAt(tblArrivals.getSelectedRow(),4);
		int flightNr =(int)model.getValueAt(tblArrivals.getSelectedRow(),5);
		int gate =(int)model.getValueAt(tblArrivals.getSelectedRow(),6);
		String status =(String)model.getValueAt(tblArrivals.getSelectedRow(),7);
		
		txtFrom.setText(from);
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
		txtFrom.setText("");	
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
			
			String sql="SELECT arrivals.id as 'ID', arrivals.from as 'FROM', airline.airline as 'AIRLINE', arrivals.date as 'DATE', arrivals.time as 'TIME',"
					+ "arrivals.flightNr as 'FLIGHT', arrivals.gate as 'GATE', arrivals.status as 'STATUS' \r\n" + 
					"FROM arrivals\r\n" + 
					"INNER JOIN airline ON arrivals.airlineId=airline.id";
			pst=conn.prepareStatement(sql);
			res=pst.executeQuery();
			//vendosja e rezultatit ne JTable; duhet te importohet rs2xml.jar
			tblArrivals.setModel(DbUtils.resultSetToTableModel(res));	
			
			pst.close();
			
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}

