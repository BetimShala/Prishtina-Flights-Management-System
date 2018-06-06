package GeneralUserInterface;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import AutentificationAuthorization.Login;
import SharedPackage.DBconnection;
import SharedPackage.EmailSender;

import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PFMAccountManagment extends JPanel {
	public JTextField txtEmri;
	public JTextField txtMbiemri;
	public JTextField txtNrTel;
	public JTextField txtBirthday;
	public JTextField txtAdresa;
	public JTextField txtUsername;
	public JTextField txtEmail;
	public JPasswordField txtPassword;
	public JPasswordField txtConfirmPassA;
	public JButton btnSaveChangesPI;
	public JButton btnSaveChangesI;
	
	public String firstname, lastname, nrTel, birthday, adresa, username, email;
	
	MessageDigest md ;
	
	boolean onClickChangeText[] = {true,true,true,true,true, true, true};

	/**
	 * Create the panel.
	 */
	public PFMAccountManagment(int id, String _firstname, String _lastname, String _nrTel, String _birthday, String _adresa, String _username, String _email) {
		
		Connection DBconn = DBconnection.sqlConnector();
		
		firstname = _firstname;
		lastname = _lastname;
		nrTel = _nrTel;
		birthday = _birthday;
		adresa = _adresa;
		username = _username;
		email = _email;
		
		setPreferredSize(new Dimension(1592, 770));
		setBackground(new Color(0, 102, 153));
		setMaximumSize(new Dimension(200, 200));
		setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
		
		JPanel pnlPersonal = new JPanel();
		pnlPersonal.setBackground(Color.WHITE);
		pnlPersonal.setPreferredSize(new Dimension(781, 750));
		add(pnlPersonal);
		pnlPersonal.setLayout(null);
		
		JLabel lblPersonal = new JLabel("Personal Information");
		lblPersonal.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblPersonal.setBounds(91, 46, 400, 80);
		pnlPersonal.add(lblPersonal);
		
		JLabel lblEmri = new JLabel("Emri:");
		lblEmri.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 17));
		lblEmri.setBounds(91, 189, 92, 37);
		pnlPersonal.add(lblEmri);
		
		JLabel lblMbiemri = new JLabel("Mbiemri:");
		lblMbiemri.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 17));
		lblMbiemri.setBounds(87, 279, 96, 40);
		pnlPersonal.add(lblMbiemri);
		
		JLabel lblNrtel = new JLabel("NrTel:");
		lblNrtel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 17));
		lblNrtel.setBounds(87, 369, 96, 37);
		pnlPersonal.add(lblNrtel);
		
		JLabel lblBirthday = new JLabel("Birthday:");
		lblBirthday.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 17));
		lblBirthday.setBounds(87, 459, 96, 40);
		pnlPersonal.add(lblBirthday);
		
		JLabel lblAdresal = new JLabel("Adresa:");
		lblAdresal.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 17));
		lblAdresal.setBounds(87, 549, 96, 37);
		pnlPersonal.add(lblAdresal);
		
		txtEmri = new JTextField();
		txtEmri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(onClickChangeText[0] == true)
				{
					txtEmri.setText(firstname);
					onClickChangeText[0] = false;
				}
			}
		});
		txtEmri.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtEmri.setBounds(272, 189, 370, 40);
		txtEmri.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnlPersonal.add(txtEmri);
		txtEmri.setColumns(10);
		
		txtMbiemri = new JTextField();
		txtMbiemri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(onClickChangeText[1] == true)
				{
					txtMbiemri.setText(lastname);
					onClickChangeText[1] = false;
				}
				
				
			}
		});
		txtMbiemri.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtMbiemri.setColumns(10);
		txtMbiemri.setBounds(272, 279, 370, 40);
		txtMbiemri.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnlPersonal.add(txtMbiemri);
		
		txtNrTel = new JTextField();
		txtNrTel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(onClickChangeText[2] == true)
				{
					txtNrTel.setText(nrTel);
					onClickChangeText[2] = false;
				}
				
			}
		});
		txtNrTel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtNrTel.setColumns(10);
		txtNrTel.setBounds(272, 369, 370, 40);
		txtNrTel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnlPersonal.add(txtNrTel);
		
		txtBirthday = new JTextField();
		txtBirthday.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(onClickChangeText[3] == true)
				{
					txtBirthday.setText(birthday);
					onClickChangeText[3] = false;
				}
			}
		});
		txtBirthday.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtBirthday.setColumns(10);
		txtBirthday.setBounds(272, 459, 370, 40);
		txtBirthday.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnlPersonal.add(txtBirthday);
		
		txtAdresa = new JTextField();
		txtAdresa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(onClickChangeText[4] == true)
				{
					txtAdresa.setText(adresa);
					onClickChangeText[4] = false;
				}
			}
		});
		txtAdresa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtAdresa.setColumns(10);
		txtAdresa.setBounds(272, 549, 370, 40);
		txtAdresa.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnlPersonal.add(txtAdresa);
		
		btnSaveChangesPI = new JButton("SAVE CHANGES");
		btnSaveChangesPI.setForeground(Color.WHITE);
		btnSaveChangesPI.setBackground(new Color(0, 102, 153));
		btnSaveChangesPI.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSaveChangesPI.setBounds(443, 642, 201, 56);
		pnlPersonal.add(btnSaveChangesPI);
		
		JPanel pnlIdentification = new JPanel();
		pnlIdentification.setBackground(Color.WHITE);
		pnlIdentification.setPreferredSize(new Dimension(781, 750));
		add(pnlIdentification);
		pnlIdentification.setLayout(null);
		
		JLabel lblIdentification = new JLabel("Identification");
		lblIdentification.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblIdentification.setBounds(96, 46, 400, 80);
		pnlIdentification.add(lblIdentification);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 17));
		lblUsername.setBounds(96, 189, 129, 39);
		pnlIdentification.add(lblUsername);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 17));
		lblEmail.setBounds(96, 279, 129, 39);
		pnlIdentification.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 17));
		lblPassword.setBounds(96, 369, 129, 39);
		pnlIdentification.add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 17));
		lblConfirmPassword.setBounds(96, 459, 151, 39);
		pnlIdentification.add(lblConfirmPassword);
		
		txtUsername = new JTextField();
		txtUsername.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(onClickChangeText[5] == true)
				{
					txtUsername.setText(username);
					onClickChangeText[5] = false;
				}
				
			}
		});
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtUsername.setBounds(275, 189, 370, 40);
		txtUsername.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnlIdentification.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(onClickChangeText[6] == true)
				{
					txtEmail.setText(email);
					onClickChangeText[6] = false;
				}
			}
		});
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtEmail.setColumns(10);
		txtEmail.setBounds(275, 279, 370, 40);
		txtEmail.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnlIdentification.add(txtEmail);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtPassword.setBounds(275, 369, 370, 40);
		txtPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnlIdentification.add(txtPassword);
		
		txtConfirmPassA = new JPasswordField();
		txtConfirmPassA.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtConfirmPassA.setBounds(275, 459, 370, 40);
		txtConfirmPassA.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnlIdentification.add(txtConfirmPassA);
		
		btnSaveChangesI = new JButton("SAVE CHANGES");
		btnSaveChangesI.setForeground(Color.WHITE);
		btnSaveChangesI.setBackground(new Color(0, 102, 153));
		btnSaveChangesI.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSaveChangesI.setBounds(444, 558, 201, 56);
		pnlIdentification.add(btnSaveChangesI);
		
		PromptSupport.setPrompt(firstname, txtEmri);
		PromptSupport.setPrompt(lastname, txtMbiemri);
		PromptSupport.setPrompt(nrTel, txtNrTel);
		PromptSupport.setPrompt(adresa, txtAdresa);
		PromptSupport.setPrompt(birthday, txtBirthday);
		PromptSupport.setPrompt(email, txtEmail);
		PromptSupport.setPrompt(username, txtUsername);

		txtNrTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(Character.isDigit(e.getKeyChar()))
				{
					if(txtNrTel.getText().toString().length()==2)
					{
						if(txtNrTel.getText().startsWith("04"))
						{
							txtNrTel.setText("+3834");
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
		
		btnSaveChangesPI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to save changes?","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
					
					String Emri = firstname;
					String Mbiemri = lastname;
					String Birthday = birthday;
					String NrTel = nrTel;
					String Adresa = adresa;
					
					if(!txtEmri.getText().equals(""))
					{
						Emri = txtEmri.getText();
					}
					
					if(!txtMbiemri.getText().equals(""))
					{
						Mbiemri = txtMbiemri.getText();
					}
					
					if(!txtNrTel.getText().equals(""))
					{
						NrTel = txtNrTel.getText();
					}
					
					if(!txtAdresa.getText().equals(""))
					{
						Adresa = txtAdresa.getText();
					}
					
					if(!txtBirthday.getText().equals(""))
					{
						Birthday = txtBirthday.getText();
					}
					
					String inserto = "{call PersonalInformation('"+id+"','"+Emri+"','"+Mbiemri+"','"+NrTel+"','"+Birthday+"','"+Adresa+"')}";
						
					try {
						CallableStatement insert = DBconn.prepareCall(inserto);
						insert.execute();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					txtEmri.setText("");
					txtMbiemri.setText("");
					txtNrTel.setText("");
					txtAdresa.setText("");
					txtBirthday.setText("");
					
					PromptSupport.setPrompt(Emri, txtEmri);
					PromptSupport.setPrompt(Mbiemri, txtMbiemri);
					PromptSupport.setPrompt(NrTel, txtNrTel);
					PromptSupport.setPrompt(Adresa, txtAdresa);
					PromptSupport.setPrompt(Birthday, txtBirthday);
					
					for(int i=0; i< onClickChangeText.length; i++)
					{
						onClickChangeText[i] = true;
					}
					
					firstname = Emri;
					lastname = Mbiemri;
					birthday = Birthday;
					nrTel = NrTel;
					adresa = Adresa;
					
					JOptionPane.showMessageDialog(null, "Succesfully updated your personal data.");
					
					}		
			}
		});
		
		btnSaveChangesI.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to save changes?","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION)
				{
					String newUsername = username;
					String newEmail = email;
					
					if(!txtEmail.getText().equals(""))
					{
						newEmail = txtEmail.getText();
					}
					
					if(!txtUsername.getText().equals(""))
					{
						newUsername = txtUsername.getText();
					}
					
					
					if(!txtPassword.getText().equals(""))
					{
						
						if(txtPassword.getText().equals(txtConfirmPassA.getText()))
						{
							int Shtesa= 100000+ (int)(Math.random() *2000000);
							String pass = txtPassword.getText()+String.valueOf(Shtesa);
							
							try 
							{
								md = MessageDigest.getInstance("SHA1");
							} 
							catch (NoSuchAlgorithmException q) 
							{
								// TODO Auto-generated catch block
								q.printStackTrace();
							}
							
							try 
							  {
								byte[] byteSaltPassword = pass.getBytes("UTF8");
								byte[] byteSaltedHash = md.digest(byteSaltPassword);
								byte[] encodedBytes = java.util.Base64.getEncoder().encode(byteSaltedHash);
								pass=new String(encodedBytes);
								
							  } 
							  catch (UnsupportedEncodingException e1)
							  {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								
							  }

							
							String updateAccountIdentification = "{call Identification('"+id+"','"+newEmail+"','"+newUsername+"','"+Shtesa+"','"+pass+"')}";
							
							try 
							{
								CallableStatement insert = DBconn.prepareCall(updateAccountIdentification);
								insert.execute();
							} catch (SQLException p) 
							{
								// TODO Auto-generated catch block
								p.printStackTrace();
							}
							
							
							JOptionPane.showMessageDialog(null, "Succesfully updated your identification data.");
							txtPassword.setText("");
							txtEmail.setText("");
							txtUsername.setText("");
							txtConfirmPassA.setText("");
							
							PromptSupport.setPrompt(newEmail, txtEmail);
							PromptSupport.setPrompt(newUsername, txtUsername);
							
							for(int i=0; i< onClickChangeText.length; i++)
							{
								onClickChangeText[i] = true;
							}
							
							email = newEmail;
							username = newUsername;
							
						
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Password doesn't match!");
						}
						
						
					}
					else
					{
						String updateAccountIdentification = "{call updateEmailUsername('"+id+"','"+newEmail+"','"+newUsername+"')}";
						
						try 
						{
							CallableStatement insert = DBconn.prepareCall(updateAccountIdentification);
							insert.execute();
							JOptionPane.showMessageDialog(null, "Succesfully updated your identification data.");
						} catch (SQLException p) 
						{
							// TODO Auto-generated catch block
							p.printStackTrace();
						}
						txtEmail.setText("");
						txtUsername.setText("");
						
						PromptSupport.setPrompt(newEmail, txtEmail);
						PromptSupport.setPrompt(newUsername, txtUsername);
					}

						
					
					
				}
				
			
		}
		});
	}
}
