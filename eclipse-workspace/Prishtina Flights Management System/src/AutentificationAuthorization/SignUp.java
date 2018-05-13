package AutentificationAuthorization;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.border.MatteBorder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Connection;

import java.awt.event.MouseMotionAdapter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.SystemColor;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import SharedPackage.*;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JTextField txtUsernameReg;
	private JTextField txtEmailReg;
	private JPasswordField txtPasswordReg;
	private JPasswordField txtRePasswordReg;
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtTel;
	private JTextField txtAddress;
	
	String name="", surname="", tel="" , address="", username="", email="",password="",pass="",creditCard="";
	int Shtesa, userRoleId=4;
	MessageDigest md ;
	
	
	static SignUp frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtCreditCard;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new SignUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignUp() {
		setResizable(false);
		
		Connection DBconn = DBconnection.sqlConnector();		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 526, 574);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		tabbedPane.setOpaque(true);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(0, 0, 510, 535);
		contentPane.add(tabbedPane);
		
		
		JPanel Personal = new JPanel();
		Personal.setBackground(Color.WHITE);
		tabbedPane.addTab("Personal", null, Personal, null);
		Personal.setLayout(null);
		
		
		txtName = new JTextField();
		txtName.addKeyListener(new KeyAdapter() { 
			int i=1;
			@Override
			public void keyTyped(KeyEvent k) 
			{	
				if(txtName.getText().length()==0)
				{
					i=1;
				}

				if(k.getKeyChar()==' ')
				{
					i=1;
					return;
				}
				
				if(i==1)
				{
					k.setKeyChar(Character.toUpperCase(k.getKeyChar()));
				}
				
				
				if(k.getKeyChar()!=KeyEvent.VK_BACK_SPACE)
				{
					i++;
					return;
				}
				else
				{
					i--;
					return;
				}
				
				
				
			}
		});
		txtName.setColumns(10);
		txtName.setBorder(new LineBorder(SystemColor.scrollbar));
		txtName.setBounds(81, 59, 304, 28);
		Personal.add(txtName);
		
		txtSurname = new JTextField();
		txtSurname.addKeyListener(new KeyAdapter() {
			int i=1;
			
			@Override
			public void keyTyped(KeyEvent k) 
			{					
				/*if(txtSurname.getText().length()==0)
				{
					i=1;
				}*/

				if(k.getKeyChar()==' ')
				{
					i=1;
					return;
				}
				
				if(i==1)
				{
					k.setKeyChar(Character.toUpperCase(k.getKeyChar()));
				}
				
				
				if(k.getKeyChar()!=KeyEvent.VK_BACK_SPACE)
				{
					i++;
					return;
				}
				else
				{
					i--;
					return;
				}
				
			}
		});
		txtSurname.setColumns(10);
		txtSurname.setBorder(new LineBorder(SystemColor.scrollbar));
		txtSurname.setBounds(81, 133, 304, 28);
		Personal.add(txtSurname);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblName.setBounds(81, 38, 64, 14);
		Personal.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblSurname.setBounds(81, 115, 51, 17);
		Personal.add(lblSurname);
		
		JLabel lblTel = new JLabel("Tel");
		lblTel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblTel.setBounds(81, 188, 64, 14);
		Personal.add(lblTel);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblBirthday.setBounds(81, 259, 107, 14);
		Personal.add(lblBirthday);
		
		JLabel lblEmpty2 = new JLabel("* You can't leave this empty!");
		lblEmpty2.setVisible(false);
		lblEmpty2.setForeground(Color.RED);
		lblEmpty2.setBounds(81, 161, 333, 14);
		Personal.add(lblEmpty2);
		
		JLabel label_5 = new JLabel("");
		label_5.setForeground(Color.RED);
		label_5.setBounds(81, 286, 333, 14);
		Personal.add(label_5);
		
		JLabel lblEmpty = new JLabel("* You can't leave this empty!");
		lblEmpty.setVisible(false);
		lblEmpty.setForeground(Color.RED);
		lblEmpty.setBounds(81, 88, 333, 14);
		Personal.add(lblEmpty);
		
		
		txtTel = new JTextField();
		txtTel.setBorder(new LineBorder(SystemColor.scrollbar));
		txtTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(Character.isDigit(e.getKeyChar()))
				{
					if(txtTel.getText().toString().length()==2)
					{
						if(txtTel.getText().startsWith("04"))
						{
							txtTel.setText("+3834");
						}
					}
				}
				else if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
				{
					getToolkit().beep();
					e.consume();	
				}
				}
		});
		
		txtTel.setColumns(10);
		txtTel.setBounds(81, 203, 304, 28);
		Personal.add(txtTel);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblAddress.setBounds(81, 375, 107, 14);
		Personal.add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.addKeyListener(new KeyAdapter() {
			int i=1;
			
			@Override
			public void keyTyped(KeyEvent k) 
			{	
				if(txtAddress.getText().length()==0)
				{
					i=1;
				}

				if(k.getKeyChar()==' ')
				{
					i=1;
					return;
				}
				
				if(i==1)
				{
					k.setKeyChar(Character.toUpperCase(k.getKeyChar()));
				}
				
				
				if(k.getKeyChar()!=KeyEvent.VK_BACK_SPACE)
				{
					i++;
					return;
				}
				else
				{
					i--;
					return;
				}
				
			}
		});
		txtAddress.setColumns(10);
		txtAddress.setBorder(new LineBorder(SystemColor.scrollbar));
		txtAddress.setBounds(81, 392, 304, 28);
		Personal.add(txtAddress);
		
		JLabel lblEmpty3 = new JLabel("* You can't leave this empty!");
		lblEmpty3.setVisible(false);
		lblEmpty3.setForeground(Color.RED);
		lblEmpty3.setBounds(81, 232, 333, 14);
		Personal.add(lblEmpty3);
		
		JLabel lblEmpty5 = new JLabel("* You can't leave this empty!");
		lblEmpty5.setVisible(false);
		lblEmpty5.setForeground(Color.RED);
		lblEmpty5.setBounds(81, 423, 333, 14);
		Personal.add(lblEmpty5);
		
		JButton btnNext = new JButton("Next");
		
		btnNext.setForeground(Color.WHITE);
		btnNext.setBackground(new Color(165,32,38));
		btnNext.setBounds(80, 442, 304, 36);
		btnNext.setFocusPainted(false);
		Personal.add(btnNext);
		
		JLabel lblDay = new JLabel("Day:");
		lblDay.setBounds(298, 289, 26, 16);
		Personal.add(lblDay);
		
		JComboBox cmbDay = new JComboBox();
		JComboBox cmbYear = new JComboBox();
		JComboBox cmbMonth = new JComboBox();
		
		cmbYear.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) 
			{
				if((Integer.parseInt(cmbYear.getSelectedItem().toString())%4)==0)
				{
					if(cmbMonth.getSelectedItem().toString() == "February" || cmbMonth.getSelectedItem().toString() == "Shkurt" )
					{
						cmbDay.setModel(new DefaultComboBoxModel(
								new String[] {"1","2","3","4","5","6","7","8","9","10",
											  "11","12","13","14","15","16","17","18","19","20",
											  "21","22","23","24","25","26","27","28","29"}));
				
					}
				}
				else
				{
					if(cmbMonth.getSelectedItem().toString() == "February" || cmbMonth.getSelectedItem().toString() == "Shkurt")
					{
						cmbDay.setModel(new DefaultComboBoxModel(
								new String[] {"1","2","3","4","5","6","7","8","9","10",
											  "11","12","13","14","15","16","17","18","19","20",
											  "21","22","23","24","25","26","27","28"}));
					}
				}
			}
		});
		
		
		cmbMonth.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) 
			{
				if(cmbMonth.getSelectedItem().toString() == "February" || cmbMonth.getSelectedItem().toString() == "Shkurt")
				{
					if((Integer.parseInt(cmbYear.getSelectedItem().toString())%4)==0)
					{
						cmbDay.setModel(new DefaultComboBoxModel(
								new String[] {"1","2","3","4","5","6","7","8","9","10",
											  "11","12","13","14","15","16","17","18","19","20",
											  "21","22","23","24","25","26","27","28","29"}));
					}
					else
					{
						cmbDay.setModel(new DefaultComboBoxModel(
								new String[] {"1","2","3","4","5","6","7","8","9","10",
											  "11","12","13","14","15","16","17","18","19","20",
											  "21","22","23","24","25","26","27","28"}));
					}
						
				}
				else if(cmbMonth.getSelectedItem().toString()== "April" || cmbMonth.getSelectedItem().toString()== "June" ||
						cmbMonth.getSelectedItem().toString()== "September" || cmbMonth.getSelectedItem().toString()== "November")
				{
					cmbDay.setModel(new DefaultComboBoxModel(
							new String[] {"1","2","3","4","5","6","7","8","9","10",
										  "11","12","13","14","15","16","17","18","19","20",
										  "21","22","23","24","25","26","27","28","29","30"}));
				}
				else
				{
					cmbDay.setModel(new DefaultComboBoxModel(
							new String[] {"1","2","3","4","5","6","7","8","9","10",
										  "11","12","13","14","15","16","17","18","19","20",
										  "21","22","23","24","25","26","27","28","30","31"}));
				}	
			}
		});
		
		cmbDay.setBorder(new LineBorder(SystemColor.scrollbar));
		cmbDay.setModel(new DefaultComboBoxModel(
				new String[] {"1","2","3","4","5","6","7","8","9","10",
							  "11","12","13","14","15","16","17","18","19","20",
							  "21","22","23","24","25","26","27","28","29","30","31"}));
		cmbDay.setBackground(Color.WHITE);
		cmbDay.setBounds(329, 286, 56, 22);
		Personal.add(cmbDay);
		
		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setBounds(176, 290, 44, 14);
		Personal.add(lblMonth);
		
		cmbMonth.setBorder(null);
		cmbMonth.setBackground(Color.WHITE);
		cmbMonth.setModel(new DefaultComboBoxModel(new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"}));
		cmbMonth.setBounds(220, 286, 75, 22);
		Personal.add(cmbMonth);
		
		JLabel lblYear = new JLabel("Year:");
		lblYear.setBounds(81, 289, 36, 16);
		Personal.add(lblYear);
		
		
		cmbYear.setBackground(Color.WHITE);
		cmbYear.setModel(new DefaultComboBoxModel(new String[] {"2000","1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960", "1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951", "1950", "1949", "1948", "1947", "1946", "1945", "1944", "1943", "1942", "1941", "1940"}));
		cmbYear.setBounds(116, 288, 56, 19);
		Personal.add(cmbYear);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
		lblGender.setBounds(81, 338, 56, 16);
		Personal.add(lblGender);
		
		JRadioButton rdbtnM = new JRadioButton("M");
		buttonGroup.add(rdbtnM);
		rdbtnM.setBackground(Color.WHITE);
		rdbtnM.setBounds(144, 334, 56, 25);
		Personal.add(rdbtnM);
		
		JRadioButton rdbtnF = new JRadioButton("F");
		buttonGroup.add(rdbtnF);
		rdbtnF.setBackground(Color.WHITE);
		rdbtnF.setBounds(204, 334, 56, 25);
		Personal.add(rdbtnF);
		
		
		
		JPanel Identification = new JPanel();
		Identification.setBackground(Color.WHITE);
		tabbedPane.addTab("Identification", null, Identification, null);
		Identification.setLayout(null);
		tabbedPane.setEnabledAt(1, false); //nuk e lejon me kliku ne tabin signup2
		
		txtUsernameReg = new JTextField();
		txtUsernameReg.setColumns(10);
		txtUsernameReg.setBorder(new LineBorder(Color.LIGHT_GRAY));
		txtUsernameReg.setBounds(88, 63, 304, 23);
		Identification.add(txtUsernameReg);
		
		txtEmailReg = new JTextField();
		txtEmailReg.setColumns(10);
		txtEmailReg.setBorder(new LineBorder(Color.LIGHT_GRAY));
		txtEmailReg.setBounds(88, 143, 304, 23);
		Identification.add(txtEmailReg);
		
		
		
		
		JLabel lblUsernameReg = new JLabel("Username");
		lblUsernameReg.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblUsernameReg.setBounds(88, 36, 129, 14);
		Identification.add(lblUsernameReg);
		
		JLabel lblEmailReg = new JLabel("Email ");
		lblEmailReg.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblEmailReg.setBounds(88, 116, 129, 14);
		Identification.add(lblEmailReg);
		
		JLabel lblPasswordReg = new JLabel("Password");
		lblPasswordReg.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblPasswordReg.setBounds(88, 193, 129, 14);
		Identification.add(lblPasswordReg);
		
		
		JLabel lblCreditCard = new JLabel("Add a payment method");
		lblCreditCard.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblCreditCard.setBounds(88, 345, 157, 14);
		Identification.add(lblCreditCard);
		
		JLabel lblConfirmPasswordReg = new JLabel("Confirm Password");
		lblConfirmPasswordReg.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		lblConfirmPasswordReg.setBounds(88, 269, 129, 14);
		Identification.add(lblConfirmPasswordReg);
		
		txtPasswordReg = new JPasswordField();
		txtPasswordReg.setBorder(new LineBorder(Color.LIGHT_GRAY));
		txtPasswordReg.setBounds(88, 220, 304, 23);
		Identification.add(txtPasswordReg);
		
		txtRePasswordReg = new JPasswordField();
		txtRePasswordReg.setBorder(new LineBorder(Color.LIGHT_GRAY));
		txtRePasswordReg.setBounds(88, 296, 304, 23);
		Identification.add(txtRePasswordReg);
		
		JLabel lblEmailVal = new JLabel("");
		lblEmailVal.setForeground(Color.RED);
		lblEmailVal.setBounds(88, 166, 333, 14);
		Identification.add(lblEmailVal);
		
		JLabel lblPasswordVal = new JLabel("");
		lblPasswordVal.setForeground(Color.RED);
		lblPasswordVal.setBounds(88, 242, 333, 14);
		Identification.add(lblPasswordVal);
		
		JLabel lblRePasswordVal = new JLabel("");
		lblRePasswordVal.setForeground(Color.RED);
		lblRePasswordVal.setBounds(88, 318, 333, 14);
		Identification.add(lblRePasswordVal);
		
		JLabel lblUsernameVal = new JLabel("");
		lblUsernameVal.setForeground(Color.RED);
		lblUsernameVal.setBounds(88, 89, 333, 14);
		Identification.add(lblUsernameVal);
		
		JLabel lblCreditCardVal = new JLabel("");
		lblCreditCardVal.setForeground(Color.RED);
		lblCreditCardVal.setBounds(88, 392, 333, 14);
		Identification.add(lblCreditCardVal);
		
		JCheckBox chckbxNotNow = new JCheckBox("Not now");
		chckbxNotNow.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(chckbxNotNow.isSelected())
				{
					txtCreditCard.setEditable(false);
					lblCreditCard.setForeground(Color.GRAY);
				}
				else
				{
					txtCreditCard.setEditable(true);
					lblCreditCard.setForeground(Color.BLACK);
				}
			}
		});
		chckbxNotNow.setBackground(Color.WHITE);
		chckbxNotNow.setBounds(315, 402, 75, 25);
		Identification.add(chckbxNotNow);
		
		txtCreditCard = new JTextField();
		txtCreditCard.setColumns(10);
		txtCreditCard.setBorder(new LineBorder(Color.LIGHT_GRAY));
		txtCreditCard.setBounds(88, 370, 304, 23);
		Identification.add(txtCreditCard);
		

		JButton btnRegister = new JButton("Register");
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setBackground(new Color(165,32,38));
		btnRegister.setBounds(88, 436, 304, 36);
		btnRegister.setFocusPainted(false);
		Identification.add(btnRegister);
		
		
		String birthday = cmbDay.getSelectedItem().toString()+"/"+(cmbMonth.getSelectedIndex()+1)+"/"+cmbYear.getSelectedItem().toString();
		
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				
				
				if(txtName.getText().equals(""))
				{
					lblEmpty.setVisible(true);
					txtName.setBorder(new MatteBorder(0, 0, 1, 0, Color.RED));
				}
				else
				{
					txtName.setBorder(new LineBorder(Color.GREEN));
					lblEmpty.setVisible(false);
					
					name = txtName.getText();
				}
				if(txtSurname.getText().equals(""))
				{
					lblEmpty2.setVisible(true);
					txtSurname.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					
				}
				else
				{
					txtSurname.setBorder(new LineBorder(Color.GREEN));
					lblEmpty2.setVisible(false);
					
					surname = txtSurname.getText();
				}
				if(txtTel.getText().equals(""))
				{
					lblEmpty3.setVisible(true);
					txtTel.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
				}
				else
				{
					txtTel.setBorder(new LineBorder(Color.GREEN));
					lblEmpty3.setVisible(false);
					
					tel=txtTel.getText();
				}
				if(txtAddress.getText().equals(""))
				{
					lblEmpty5.setVisible(true);
					txtAddress.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
				}
				else
				{
					txtAddress.setBorder(new LineBorder(Color.GREEN));
					lblEmpty5.setVisible(false);
					
					address=txtAddress.getText();
				}
				if(!(txtName.getText().equals("")) && !(txtSurname.getText().equals("")) && !(txtTel.getText().equals(""))
					&& !(txtAddress.getText().equals("")) && !(cmbDay.getSelectedItem().toString().equals("None")))
				{

					tabbedPane.setEnabledAt(1,true);
					tabbedPane.setSelectedIndex(1);
				}
			}
		});
		
		
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				
				
				// Username Validation
				if(((txtUsernameReg.getText().length() < 3) || (txtUsernameReg.getText().length() >30)) && (txtUsernameReg.getText().length()!=0))
				{
					txtUsernameReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					if(Login.gjuha == "Shqip")
					{
						lblUsernameVal.setText("* Emri i perdoruesit te jete mes 3 dhe 30 karaktere");		
					}
					else
					{
						lblUsernameVal.setText("* Username must be between 3 and 30 characters");		
					}
								
				}
				else if (txtUsernameReg.getText().equals(""))
				{
					txtUsernameReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					if(Login.gjuha == "Shqip")
					{
						lblUsernameVal.setText("* Mos e le te zbrazet.");
					}
					else
					{
						lblUsernameVal.setText("* You can't leave this empty.");
					}
				}
				else
				{
					String user = "{call Useri('"+txtUsernameReg.getText()+"')}";
					ResultSet rez;
					
					try 
					{
						CallableStatement useri = DBconn.prepareCall(user);
						rez = useri.executeQuery();
						
						if(!rez.next())
						{
							txtUsernameReg.setBorder(new LineBorder(Color.GREEN));
							lblUsernameVal.setText("");
							
							username = txtUsernameReg.getText();
						}
						else
						{
							txtUsernameReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
							
							
							if(Login.gjuha == "Shqip")
							{
								lblUsernameVal.setText("* Emri i perdoruesit eshte i zene!");
							}
							else
							{
								lblUsernameVal.setText("* Username is already taken!");
							}
						}
					} 
					catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
										
				}
				
				//Email Registration Exception Validation 
				String emailPattern="[a-zA-Z]{1}[a-zA-Z0-9]{1,20}[.]{0,1}[-]{0,1}[_]{0,1}[a-zA-Z0-9]{1,20}@[a-zA-Z0-9]{1,10}[-]{0,1}[a-zA-Z0-9]{1,10}.[a-zA-Z0-9]{2,3}[.]{0,1}[a-zA-Z0-9]{0,2}";
				Pattern pattern=Pattern.compile(emailPattern);
				Matcher regMatcher=pattern.matcher(txtEmailReg.getText());
				
				if (!regMatcher.matches() && !(txtEmailReg.getText().equals("")))
				{
					txtEmailReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					if(Login.gjuha == "Shqip")
					{
						lblEmailVal.setText("* Format i gabuar");	
					}
					else
					{
						lblEmailVal.setText("* Wrong Format");	
					}
						
				}
				else if (txtEmailReg.getText().equals(""))
				{
					txtEmailReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					if(Login.gjuha == "Shqip")
					{
						lblEmailVal.setText("* Mos e le te zbrazet.");	
					}
					else
					{
						lblEmailVal.setText("* You can't leave this empty.");
					}
										
				}
				else
				{
					String imell = "{call Emaili('"+txtEmailReg.getText()+"')}";
					ResultSet rez;
					try
					{
						CallableStatement imelli = DBconn.prepareCall(imell);
						rez = imelli.executeQuery();
						
						if(!rez.next())
						{
							txtEmailReg.setBorder(new LineBorder(Color.GREEN));
							lblEmailVal.setText("");		
						
							email = txtEmailReg.getText();
						}
						else
						{
							txtEmailReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
							if(Login.gjuha == "Shqip")
							{
								lblEmailVal.setText("* Adresa elektronike ka nje llogari.");	
							}
							else
							{
								lblEmailVal.setText("* Email already has an account!");
							}
							
						}
												
					}
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
				
				
				//Password Validation
				if (((txtPasswordReg.getPassword().length < 3) || (txtPasswordReg.getPassword().length >20)) && (txtPasswordReg.getPassword().length!=0))
				{
					txtPasswordReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					if(Login.gjuha == "Shqip")
					{
						lblPasswordVal.setText("* Fjalekalimi le te jete mes 3 dhe 20 karaktere");
					}
					else
					{
						lblPasswordVal.setText("* Password must be between 3 and 20 characters");
					}
					
				}
				else if (txtPasswordReg.getPassword().length == 0)
				{
					txtPasswordReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					if(Login.gjuha == "Shqip")
					{
						lblPasswordVal.setText("* Mos e le te zbrazet.");
					}
					else
					{
						lblPasswordVal.setText("* You can't leave this empty.");
					}
					
				}
				else
				{
					txtPasswordReg.setBorder(new LineBorder(Color.GREEN));
					lblPasswordVal.setText("");
					
					Shtesa= 100000+ (int)(Math.random() *2000000); 
					
					pass=txtPasswordReg.getText();
					String saltPassword=pass+String.valueOf(Shtesa);
					
					try {
						md = MessageDigest.getInstance("SHA1");
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try 
					  {
						byte[] byteSaltPassword = saltPassword.getBytes("UTF8");
						byte[] byteSaltedHash = md.digest(byteSaltPassword);
						byte[] encodedBytes = java.util.Base64.getEncoder().encode(byteSaltedHash);
						pass=new String(encodedBytes);
						
					  } 
					  catch (UnsupportedEncodingException e1)
					  {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						
					  }
					
				}
				
				
				if (!(Arrays.equals(txtRePasswordReg.getPassword(), txtPasswordReg.getPassword())) && !(txtRePasswordReg.getPassword().length == 0))
				{
					txtRePasswordReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					if(Login.gjuha == "Shqip")
					{
						lblRePasswordVal.setText("* Fjalekalimet nuk perputhen!");
					}
					else
					{
						lblRePasswordVal.setText("* Confirmation password doesn't match!");
					}
					
				}
				else if (txtRePasswordReg.getPassword().length == 0)
				{
					txtRePasswordReg.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					if(Login.gjuha == "Shqip")
					{
						lblRePasswordVal.setText("* Mos e le te zbrazet.");
					}
					else
					{
						lblRePasswordVal.setText("* You can't leave this empty.");
					}
					
				}
				else
				{
					txtRePasswordReg.setBorder(new LineBorder(Color.GREEN));
					lblRePasswordVal.setText("");
					
					password = pass;
				}
				
				if(chckbxNotNow.isSelected())
				{
					creditCard = "default";
				}
				else
				{
					if(txtCreditCard.getText().equals(""))
					{
						txtCreditCard.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
						lblCreditCardVal.setText("* You can't leave this empty.");
					}
					else
					{
						txtCreditCard.setBorder(new LineBorder(Color.GREEN));
						lblCreditCardVal.setText("");
						
						creditCard = txtCreditCard.getText();
						
						try {
							md = MessageDigest.getInstance("SHA1");
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try 
						  {
							byte[] byteCreditCard = creditCard.getBytes("UTF8");
							byte[] byteSaltedHashC = md.digest(byteCreditCard);
							byte[] encodedBytes = java.util.Base64.getEncoder().encode(byteSaltedHashC);
							creditCard=new String(encodedBytes);
							
						  } 
						  catch (UnsupportedEncodingException e1)
						  {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							
						  }
					}
					
				}
				
				//String name="", surname="", tel="" , address="", username="", email="",pass="";
				if( !(name.equals(""))&& !(surname.equals("")) && !(tel.equals("")) && !(address.equals("")) && !(email.equals("")) && !(username.equals("")) && !(pass.equals("")) && !(password.equals("")) && !(creditCard.equals("")))
				{
					String inserto = "{call shtoUser('"+username+"','"+name+"','"+surname+"','"+tel+"','"+birthday+"','"+address+"','"+email+"','"+userRoleId+"','"+Shtesa+"','"+password+"','"+creditCard+"')}";
					try {
						CallableStatement insert = DBconn.prepareCall(inserto);
						insert.execute();
						frame.dispose();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
								
			}
		});
		setLocationRelativeTo(null);
		
		if(Login.gjuha=="Shqip")
		{
			tabbedPane.setTitleAt(0, "Personale");
			tabbedPane.setTitleAt(1, "Identifikimi");
			
			lblName.setText("Emri");
			lblSurname.setText("Mbiemri");
			lblBirthday.setText("Datelindja");
			lblYear.setText("Viti:");
			lblMonth.setText("Muaji:");
			lblDay.setText("Dita:");
			lblAddress.setText("Adresa");
			btnNext.setText("Vazhdo");
			lblGender.setText("Gjinia");
			
			lblEmpty.setText("* Mos e le te zbrazet.");
			lblEmpty2.setText("* Mos e le te zbrazet.");
			lblEmpty3.setText("* Mos e le te zbrazet.");
			lblEmpty5.setText("* Mos e le te zbrazet.");
			
			lblUsernameReg.setText("Emri i perdoruesit");
			lblEmailReg.setText("Adresa elektronike");
			lblPasswordReg.setText("Fjalekalimi");
			lblConfirmPasswordReg.setText("Konfirmo fjalekalimin");
			
			cmbMonth.setModel(new DefaultComboBoxModel(new String[]{"Janar","Shkurt","Mars","Prill","Maj","Qershor","Korrik","Gusht","Shtator","Tetor","Nentor","Dhjetor"}));
			
			btnRegister.setText("Regjistrohu");
		}
		
	}
}