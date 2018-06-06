package AutentificationAuthorization;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.JTextField;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Cursor;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import SharedPackage.*;
import GeneralUserInterface.*;
import java.awt.Toolkit;

public class Login extends JFrame {

	private JPanel contentPane;
	private JPasswordField txtPassword;
	private JTextField txtUsername;
	private JTextField txtTemp;
	
	String useri = "", passi = "";
	public String name ="" , surname="";
	MessageDigest md ;
	String gjuha="English";
	
	public int id;
	public String useriii = "";
	public String teliii="";
	public String emailiii="";
	public String birthday="";
	public String adresa= "";
	public int roli;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
		setIconImage(icon);
		setResizable(false);
		
		Connection DBconn = DBconnection.sqlConnector();		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 515, 566);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		txtPassword = new JPasswordField();
		PromptSupport.setPrompt("Password", txtPassword);
		txtPassword.setBorder(new LineBorder(SystemColor.scrollbar));
		txtPassword.setBounds(119, 287, 211, 36);
		contentPane.add(txtPassword);
		
		txtTemp = new JTextField();
		txtTemp.setBounds(119, 287, 211, 36);
		txtTemp.setBorder(new LineBorder(SystemColor.scrollbar));
		txtTemp.setVisible(false);
		contentPane.add(txtTemp);
		txtTemp.setColumns(10);
	
		JButton btnLogin = new JButton("Login");
		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				txtTemp.setText(txtPassword.getText());
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					btnLogin.doClick();
				}
			}
		});

		JLabel lblEyePassword = new JLabel("");
		
		lblEyePassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblEyePassword.setBorder(new LineBorder(SystemColor.scrollbar));
		lblEyePassword.setBounds(328, 287, 38, 36);
		contentPane.add(lblEyePassword);
		lblEyePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) 
			{
				txtTemp.setVisible(true);
				txtPassword.setVisible(false);
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				txtTemp.setVisible(false);
				txtPassword.setVisible(true);
			}
		});
		
		
		
		
		JLabel lblWelcome = new JLabel("PFMS");
		lblWelcome.setVerticalAlignment(SwingConstants.TOP);
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setForeground(new Color(0, 51, 153));
		lblWelcome.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 47));
		lblWelcome.setBounds(118, 83, 248, 81);
		contentPane.add(lblWelcome);
		
		JLabel lblUsername = new JLabel("* You can't leave this empty!");
		lblUsername.setVisible(false);
		lblUsername.setForeground(Color.RED);
		lblUsername.setBounds(119, 229, 247, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("* You can't leave this empty!");
		lblPassword.setVisible(false);
		lblPassword.setForeground(Color.RED);
		lblPassword.setBounds(119, 326, 247, 14);
		contentPane.add(lblPassword);
		
		
		
		
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if (txtUsername.getText().equals("") )
				{
					txtUsername.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					lblUsername.setForeground(Color.RED);
					lblUsername.setVisible(true);
					
				}
				else
				{
					useri = txtUsername.getText();
				}

				if (txtPassword.getPassword().length == 0)
				{
					txtPassword.setBorder(new MatteBorder(0, 0, 1, 0, Color.red));
					lblPassword.setForeground(Color.RED);
					lblPassword.setVisible(true);
					
				}
				else
				{
					
					passi = txtPassword.getText();
				}
				
				if(useri!="" && passi!="")
				{
					
					String user = "{call Useri('"+useri+"')}";
					String salt = "{call Salti('"+useri+"')}";
					
					CallableStatement userii=null;
					CallableStatement salti=null;
					
					ResultSet rezu=null;
					ResultSet resz=null;
					try {
						userii = DBconn.prepareCall(user);
						salti = DBconn.prepareCall(salt);
						rezu = userii.executeQuery();
						resz = salti.executeQuery();
						
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					try {
						md = MessageDigest.getInstance("SHA1");
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						if(rezu.next() && resz.next())
						{
							id= rezu.getInt("Id");
							
							name = rezu.getString("Emri");
							surname = rezu.getString("Mbiemri");
							
							useriii=rezu.getString("Username");
							teliii=rezu.getString("NrTel");
							emailiii=rezu.getString("Email");
							roli = rezu.getInt("UserRoleId");
							birthday = rezu.getString("Birthday");
							adresa = rezu.getString("Adresa");
							
							String password = passi+String.valueOf(resz.getString("Salt"));
														
							byte[] byteSaltPassword = password.getBytes("UTF8");
							byte[] byteSaltedHash = md.digest(byteSaltPassword);
							byte[] encodedBytes = java.util.Base64.getEncoder().encode(byteSaltedHash);
							password=new String(encodedBytes);
							
							
							String uandp = "{call Userandpass('"+useri+"','"+password+"')}";
							CallableStatement userandpass = DBconn.prepareCall(uandp);
							ResultSet rezi = userandpass.executeQuery();
						
							if(rezi.first() && rezi.getBoolean("CreatedByAdmin"))
							{
								ForgotPassword forgotPassword = new ForgotPassword();
								forgotPassword.setVisible(true);
							}
							else if(rezi.first())
							{
								PFMSinterface pfms = new PFMSinterface(id,name, surname, teliii, birthday, adresa, emailiii, useriii, roli);
								pfms.setVisible(true);
								pfms.setExtendedState(pfms.getExtendedState() | JFrame.MAXIMIZED_BOTH);
								
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Error on your data validation!");
							}
						}
						
						else
						{
							JOptionPane.showMessageDialog(null, "Error on your data validation!");
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				
			}
		});
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
		btnLogin.setBorder(null);
		btnLogin.setBackground(new Color(0,102,153));
		btnLogin.setBounds(119, 398, 247, 36);
		btnLogin.setFocusPainted(false);
		contentPane.add(btnLogin);
		
		txtUsername = new JTextField();
		PromptSupport.setPrompt("Username", txtUsername); // permes jar file te importum na mundesohet
		txtUsername.setColumns(10);
		txtUsername.setBorder(new LineBorder(SystemColor.scrollbar));
		txtUsername.setBounds(119, 193, 247, 36);
		contentPane.add(txtUsername);
		
		
		JLabel lblForgot = new JLabel("Forgot Password ?");
		lblForgot.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForgot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				lblForgot.setForeground(new Color(165,32,38));
			}
			public void mouseExited(MouseEvent e) 
			{
				lblForgot.setForeground(new Color(0,102,153));
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				ForgotPassword frgpass = new ForgotPassword();
				frgpass.setVisible(true);
				
			}
		});
		lblForgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblForgot.setForeground(new Color(0,102,153));
		lblForgot.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblForgot.setBackground(Color.BLUE);
		lblForgot.setBounds(219, 373, 147, 14);
		contentPane.add(lblForgot);
		
		JLabel lblCreateAccount = new JLabel("Create Account");
		lblCreateAccount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCreateAccount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
		lblCreateAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) 
			{
				lblCreateAccount.setForeground(new Color(165,32,38));
			}
			@Override
			public void mouseExited(MouseEvent e) 
			{
				lblCreateAccount.setForeground(new Color(0,102,153));
			}
			@Override
			public void mousePressed(MouseEvent e) 
			{
				SignUp.main(null);
			}
		});
		lblCreateAccount.setBounds(219, 351, 147, 14);
		lblCreateAccount.setForeground(new Color(0,102,153));
		lblCreateAccount.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblCreateAccount);
		
		
		
		setLocationRelativeTo(null);
	}
}