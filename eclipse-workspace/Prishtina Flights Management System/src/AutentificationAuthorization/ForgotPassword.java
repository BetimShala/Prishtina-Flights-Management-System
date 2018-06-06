package AutentificationAuthorization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.html.HTML;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import SharedPackage.*;

public class ForgotPassword extends JFrame {
	String email="";
	String pass="";
	int code;
	MessageDigest md ;
	private JPanel contentPane;
	private JLabel lblEmail;
	private JLabel lblpass;
	private JTextField txtemail;
	private JTextField txtConfirmCode;
	private JPasswordField txtpass;
	private JLabel lblEmailVal;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	double width=dim.getWidth();
	double height=dim.getHeight();
	private JLabel lblErrorCode;
	private JLabel label;

	  public void option(String emailid,int code)
	    {
		  try
		  	{
	         
	         String id=emailid;             
	         String[] to={id}; 
	         //new email
	         if(EmailSender.sendMail("noreply.pfms@gmail.com","pfmsproject2018","Use this code to reset your password "+code+" . Do not forward or give this code to anyone.\n\n The PFMS-PROJECT Accounts Team " ,to,"Change your password!"))
		     	{
		             
		       	}
		     else 
		       	{        
		        	JOptionPane.showMessageDialog(null,"Connection Failed!!! Please try again Later after Connecting to the internet...");
	         	}
	                
		  	}
	                
		  catch(Exception e)
	        {
	                JOptionPane.showMessageDialog(null,e.toString());
	        }
	    }
	  public void option1(String emailid,String date)
	    {
		  try
		  	{
	                
	         String id=emailid;             
	         String[] to={id}; 
	         
		     if(EmailSender.sendMail("noreply.pfms@gmail.com","pfmsproject2018","The password for your PFMS-PROJECT Account "+id +" was recently changed\n\nDate : "+date+"\n\nThe PFMS-PROJECT Accounts Team " ,to,"Your password changed!"))
		         {
		             
		         }
		     else 
		         {        
		        	JOptionPane.showMessageDialog(null,"Connection Failed!!! Please try again Later after Connecting to the internet...");
		         }         
	                
		  	}
	                
		  catch(Exception e)
	        {
	          JOptionPane.showMessageDialog(null,e.toString());
	        }
	    }
	        
	      
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ForgotPassword frame = new ForgotPassword();
					  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
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
	public ForgotPassword() {
		
		
		Connection DBconn = DBconnection.sqlConnector();	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 515, 566);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Javanese Text", Font.PLAIN, 32));
		label.setBounds(134, 37, 248, 104);
		contentPane.add(label);
		
		lblEmail = new JLabel("Write your email address");
		lblEmail.setBounds(151, 165, 231, 28);
		contentPane.add(lblEmail);
		
		lblpass = new JLabel("Reset your password");
		lblpass.setBounds(151, 280, 210, 28);
		lblpass.setVisible(false);
		contentPane.add(lblpass);
		
		txtemail = new JTextField();
		txtemail.setBounds(151, 193, 210, 27);
		txtemail.setColumns(10);
		contentPane.add(txtemail);
		
		txtpass = new JPasswordField();
		txtpass.setEnabled(false);
		
		txtpass.setBounds(151, 320, 210, 27);
		txtpass.setColumns(10);
		txtpass.setVisible(false);
		contentPane.add(txtpass);
		
		txtConfirmCode = new JTextField();
		txtConfirmCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) 
			{
				txtpass.setEnabled(true);
			}
		});
		txtConfirmCode.setBounds(151, 223, 210, 28);
		txtConfirmCode.setColumns(10);
		txtConfirmCode.setVisible(false);
		contentPane.add(txtConfirmCode);
		
		lblEmailVal = new JLabel("");
		lblEmailVal.setForeground(Color.RED);
		lblEmailVal.setBounds(151, 217, 211, 27);
		contentPane.add(lblEmailVal);
		
		lblErrorCode = new JLabel("");
		lblErrorCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorCode.setForeground(Color.RED);
		lblErrorCode.setBounds(150, 256, 211, 27);
		lblErrorCode.setVisible(false);
		contentPane.add(lblErrorCode);
		

		JButton btnConfirmCode = new JButton("Reset Password");
		btnConfirmCode.setBackground(new Color(0, 102, 153));
		btnConfirmCode.setForeground(Color.WHITE);
		btnConfirmCode.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
		btnConfirmCode.setBorder(null);
		btnConfirmCode.setBounds(150, 332, 211, 36);
		btnConfirmCode.setFocusPainted(false);
		btnConfirmCode.setVisible(false);
		contentPane.add(btnConfirmCode);
		
		JLabel lblchanged = new JLabel("Your password has been successfully changed");
		lblchanged.setHorizontalAlignment(SwingConstants.LEFT);
		lblchanged.setForeground(new Color(0, 102, 153));
		lblchanged.setBounds(101, 390, 308, 27);
		lblchanged.setVisible(false);
		contentPane.add(lblchanged);
		
		JButton btnConfirm = new JButton("Send a code");
		btnConfirm.setBackground(new Color(0, 102, 153));
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
		btnConfirm.setBorder(null);
		btnConfirm.setBounds(151, 271, 211, 36);
		btnConfirm.setFocusPainted(false);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				String emailPattern="[a-zA-Z]{1}[a-zA-Z0-9]{1,20}[.]{0,1}[-]{0,1}[_]{0,1}[a-zA-Z0-9]{1,20}@[a-zA-Z0-9]{1,10}[-]{0,1}[a-zA-Z0-9]{1,10}.[a-zA-Z0-9]{2,3}[.]{0,1}[a-zA-Z0-9]{0,2}";
				Pattern pattern=Pattern.compile(emailPattern);
				Matcher regMatcher=pattern.matcher(email);
				
				if (!regMatcher.matches() && !(email.equals("")))
				{
					txtemail.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					JOptionPane.showMessageDialog(null, "WRONG FORMAT");
					
				}
				else if (txtemail.getText().equals(""))
				{
					txtemail.setBorder(new MatteBorder(0, 0, 2, 0, Color.red));
					lblEmailVal.setText("* You can't leave this empty.");	
									
				}
				else
				{
					String emailcheck = "{call Emaili('"+txtemail.getText()+"')}";
					ResultSet rez;
					try
					{
						CallableStatement chkEmail = DBconn.prepareCall(emailcheck);
						rez = chkEmail.executeQuery();
						
						if(rez.next())
						{
									lblEmailVal.setText("");
									txtemail.setBorder(new LineBorder(Color.GREEN));
								   
		
									email = txtemail.getText();
								    code= 1000+ (int)(Math.random() *9999); 
			                        String SaltCode=String.valueOf(code);
			                        
									
			
									try {
										md = MessageDigest.getInstance("SHA1");
									} catch (NoSuchAlgorithmException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
			
									try 
									{
				
										byte[] byteSaltCode = SaltCode.getBytes("UTF8");
										byte[] byteSaltedHash = md.digest(byteSaltCode);
										byte[] encodedBytes = java.util.Base64.getEncoder().encode(byteSaltedHash);
										SaltCode=new String(encodedBytes);
										String forgotpassword = "{call shtoPinCode('"+email+"','"+SaltCode+"')}";
										try {
											CallableStatement FP = DBconn.prepareCall(forgotpassword);
											FP.execute();
											option(email,code);
											
											lblEmail.setText("Check your email for your code");
											
											
											lblEmail.setBounds(151, 185, 231, 28);
											txtemail.setVisible(false);
											txtpass.setVisible(true);
											lblpass.setVisible(true);
											
											txtConfirmCode.setVisible(true);
											btnConfirm.setVisible(false);
											btnConfirmCode.setVisible(true);
											btnConfirmCode.setBounds(151, 471, 211, 36);
											
											
											
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
				
									} 
									catch (UnsupportedEncodingException e1)
									{
										// TODO Auto-generated catch block
										e1.printStackTrace();
				  
									}
							}
							else
							{
								txtemail.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
								lblEmailVal.setText("");
								lblEmailVal.setText("This email doesn't exists");
							}
							}
						
						
												
					
				
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
				
			}
		});
		contentPane.add(btnConfirm);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String date1=dateFormat.format(date); //2016/11/16 12:08:43
		
		btnConfirmCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String comparecode=txtConfirmCode.getText();
				
				try {
					md = MessageDigest.getInstance("SHA1");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try 
				{

					byte[] byteSaltCode = comparecode.getBytes("UTF8");
					byte[] byteSaltedHash = md.digest(byteSaltCode);
					byte[] encodedBytes = java.util.Base64.getEncoder().encode(byteSaltedHash);
					comparecode=new String(encodedBytes);
					
					String krahaso = "{call merrPinCode('"+txtemail.getText()+"')}";
					
					try {
					
						CallableStatement krahasim = DBconn.prepareCall(krahaso);
						String pincode;
						ResultSet rez=krahasim.executeQuery();
						
						if(rez.next())
						{
						
						pincode=rez.getString("pincode");
						
						if(pincode.equals(comparecode))
						{
							
							pass=txtpass.getText();
							
							String emailcheck = "{call Emaili('"+txtemail.getText()+"')}";
							ResultSet rez1;
							try
							{
								CallableStatement chkEmail = DBconn.prepareCall(emailcheck);
								rez1 = chkEmail.executeQuery();
								
								if(rez1.next())
								{
									
											txtemail.setBorder(new LineBorder(Color.GREEN));
										     lblEmailVal.setText("");		
				
											email = txtemail.getText();
											int Shtesa = 100000+ (int)(Math.random() *2000000); 
											
					
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
												byte[] byteSaltedHash1 = md.digest(byteSaltPassword);
												byte[] encodedBytes1 = java.util.Base64.getEncoder().encode(byteSaltedHash1);
												pass=new String(encodedBytes1);
												String forgotpassword = "{call ForgotPassword('"+email+"','"+Shtesa+"','"+pass+"')}";
												try {
													CallableStatement FP = DBconn.prepareCall(forgotpassword);
													FP.execute();
													option1(email,date1);
													lblchanged.setVisible(true);
													
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
						
											} 
											catch (UnsupportedEncodingException e1)
											{
												// TODO Auto-generated catch block
												e1.printStackTrace();
						  
											}
									}
//									else
//									{
//											txtemail.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
//											lblEmailVal.setText("");
//											lblEmailVal.setText("This email doesn't exists");
//									}
									}
								
							
														
							
						
							catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else
						{
							lblErrorCode.setVisible(true);
						
							lblErrorCode.setText("Incorrent code");
							
							//JOptionPane.showMessageDialog(null, "Incorrent code");
						}
					}
					}
					catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					
					
				
				
				
			}
				catch (UnsupportedEncodingException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
				
			}
			});
		
	
		
		
		
		setLocationRelativeTo(null);
	}
}