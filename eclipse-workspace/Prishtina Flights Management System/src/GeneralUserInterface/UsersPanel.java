package GeneralUserInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.EventObject;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import SharedPackage.DBconnection;
import SharedPackage.EmailSender;
import net.proteanit.sql.DbUtils;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
public class UsersPanel extends JPanel {
	Connection connection = null;;
	CallableStatement callableStatement = null;
	ResultSet resultSet = null;;
	private JTable table;
	private JTextField txtKerko;
	private JTextField txtFirstname;
	private JTextField txtLastname;
	private JTextField txtEmail;
	private JTextField txtUsername;
	private JTextField txtPhone;
	private JTextField txtAddress;
	private JTextField txtBirthday;
	private JLabel lblMistake;
	private JComboBox cmbRole;
	
	private boolean updateModeEnable = false;
	private int userId;

	/**
	 * Create the panel.
	 */
	public UsersPanel() {
		setPreferredSize(new Dimension(1592, 770));
		setLayout(null);
		
		try {
			connection = DBconnection.sqlConnector();
			callableStatement = connection.prepareCall("{call GetAllUsers()}");
			callableStatement.execute();
			resultSet = callableStatement.getResultSet();
		} catch (Exception e) {
			e.getMessage();
		}
		
		JLabel lblKerko = new JLabel("Search:");
		lblKerko.setBounds(16, 16, 60, 29);
		lblKerko.setFont(new Font("Segoi UI", Font.PLAIN, 16));
		add(lblKerko);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 49, 1400, 188);
		add(scrollPane);
		
		table = new JTable();
		table.setFocusable(false);
		table.setModel(DbUtils.resultSetToTableModel(resultSet));
		
		try {
			connection.close();
			callableStatement.close();
			resultSet.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(20);
		Font objF = new Font("Segoi UI", Font.PLAIN, 12);
		table.setFont(objF);
		DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
		defaultTableCellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.setDefaultRenderer(Object.class, defaultTableCellRenderer);
		scrollPane.setViewportView(table);
		
		txtKerko = new JTextField("");
		txtKerko.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtKerko.setBounds(77, 16, 141, 26);
		add(txtKerko);
		txtKerko.setColumns(10);
		txtKerko.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					connection = DBconnection.sqlConnector();
					callableStatement = connection.prepareCall("{call GetUsersBySearch(?)}");
					callableStatement.setString(1,  txtKerko.getText()+"%");
					callableStatement.execute();
					resultSet = callableStatement.getResultSet();
					table.setModel(DbUtils.resultSetToTableModel(resultSet));
				} catch (Exception e2) {
					e2.getMessage();
				}
			}
		});
		
		txtFirstname = new JTextField("");
		txtFirstname.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtFirstname.setBounds(-62, 364, 116, 26);
		txtFirstname.setColumns(10);
		add(txtFirstname);
		
		txtLastname = new JTextField("");
		txtLastname.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtLastname.setBounds(62, 364, 130, 26);
		txtLastname.setColumns(10);
		add(txtLastname);
		
		txtEmail = new JTextField("");
		txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtEmail.setBounds(200, 364, 95, 26);
		txtEmail.setColumns(10);
		add(txtEmail);
		String emailPattern = "[a-zA-Z]{1}[a-zA-Z0-9]{1,20}[.]{0,1}[-]{0,1}[_]{0,1}[a-zA-Z0-9]{1,20}" + 
							   "@[a-zA-Z0-9]{1,10}[-]{0,1}[a-zA-Z0-9]{1,10}.[a-zA-Z0-9]{2,3}[.]{0,1}[a-zA-Z0-9]{0,2}";
		txtEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtEmail.getText().equals("Email")) {
					txtEmail.setText("");
				}
			}
		});
		
		txtUsername = new JTextField("");
		txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtUsername.setBounds(303, 364, 96, 26);
		txtUsername.setColumns(10);
		add(txtUsername);

		txtPhone = new JTextField("");
		txtPhone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtPhone.setBounds(407, 364, 95, 26);
		txtPhone.setColumns(10);
		add(txtPhone);
		txtPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (Character.isDigit(e.getKeyChar())) {
					if (txtPhone.getText().toString().length() == 2) {
						if (txtPhone.getText().startsWith("04")) {
							txtPhone.setText("+3834");
						}
					}
				} else if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
					getToolkit().beep();
					e.consume();
				}
			}
		});
		
		txtAddress = new JTextField("");
		txtAddress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtAddress.setBounds(510, 364, 95, 26);
		txtAddress.setColumns(10);
		add(txtAddress);
		
		txtBirthday = new JTextField("");
		txtBirthday.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtBirthday.setBounds(613, 364, 95, 26);
		txtBirthday.setColumns(10);
		add(txtBirthday);
		
		JButton btnAdd = new JButton("Add Worker");
		btnAdd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(new Color(0, 102, 153));
		btnAdd.setBounds(859, 363, 133, 29);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!checkIfNull(txtFirstname.getText(), txtLastname.getText(), txtEmail.getText(), txtUsername.getText(), txtPhone.getText(), txtAddress.getText(), txtBirthday.getText(), cmbRole.getSelectedItem())) {
					if ((validateField(txtFirstname, "[a-zA-Z]+", "Please use only letters in the field 'Firstname'.", lblMistake) &&
						 validateField(txtLastname, "[a-zA-Z]+", "Please use only letters in the field 'Lastname'.", lblMistake) &&
						 validateField(txtEmail, emailPattern, "Please type in a valid format of an Email ID.", lblMistake) &&
						 validateField(txtUsername, "[a-zA-Z0-9]+", "Please use only letters in the field 'Firstname'.", lblMistake) &&
						 validateField(txtAddress, "[a-zA-Z0-9, ]+", "Please use only alphanumeric symbols in the field 'Address'.", lblMistake) &&
						 validateField(txtBirthday, "([0-9]{2})/([0-9]{2})/([0-9]{4})", "The birthday must be filled in the format 'dd/mm/yyy'.", lblMistake))) {
						
						if (updateModeEnable) {
							System.out.println("Insdie if(updateModeEnable)..");
							try {
								connection = DBconnection.sqlConnector();
								callableStatement = connection.prepareCall("{call UpdateWorker(?,?,?,?,?,?,?,?,?)}");
								callableStatement.setInt(1, userId);
								callableStatement.setString(2, txtUsername.getText());
								callableStatement.setString(3, txtFirstname.getText());
								callableStatement.setString(4, txtLastname.getText());
								callableStatement.setString(5, txtPhone.getText());
								callableStatement.setString(6, txtBirthday.getText());
								callableStatement.setString(7, txtAddress.getText());
								callableStatement.setString(8, txtEmail.getText());
								callableStatement.setString(9, cmbRole.getSelectedItem().toString());
								callableStatement.execute();
								
								callableStatement = connection.prepareCall("{call GetAllUsers()}");
								callableStatement.execute();
								resultSet = callableStatement.getResultSet();
								table.setModel(DbUtils.resultSetToTableModel(resultSet));
								
								callableStatement.close();
								resultSet.close();
								connection.close();
								clearTextComponents(txtUsername, txtFirstname, txtLastname, txtPhone, txtBirthday, txtAddress, txtEmail);
								btnAdd.setEnabled(true);
								btnAdd.setText("Shto punonj�s");
								
							} catch (SQLException e) {
								e.printStackTrace();
							}
							System.out.println("btnEdito - updateModeEnalbed: " + updateModeEnable);
							updateModeEnable = false;
							System.out.println("btnEdito - updateModeEnalbed: " + updateModeEnable);
						} else {
							System.out.println("Insdie Else..");
							String password = generatePassword();
							int salt = generateSalt();
							String hash = generateHash(password + String.valueOf(salt));
						
							try {
								connection = DBconnection.sqlConnector();
								callableStatement = connection.prepareCall("{call RegisterWorker(?,?,?,?,?,?,?,?,?,?)}");
								callableStatement.setString(1, txtUsername.getText());
								callableStatement.setString(2, txtFirstname.getText());
								callableStatement.setString(3, txtLastname.getText());
								callableStatement.setString(4, txtPhone.getText());
								callableStatement.setString(5, txtBirthday.getText());
								callableStatement.setString(6, txtAddress.getText());
								callableStatement.setString(7, txtEmail.getText());
								callableStatement.setString(8, cmbRole.getSelectedItem().toString());
								callableStatement.setInt(9, salt);
								callableStatement.setString(10, hash);
								callableStatement.execute();
								
								callableStatement = connection.prepareCall("{call GetAllUsers()}");
								callableStatement.execute();
								resultSet = callableStatement.getResultSet();
								table.setModel(DbUtils.resultSetToTableModel(resultSet));
									
								callableStatement.close();
								resultSet.close();
								connection.close();
								btnAdd.setEnabled(true);
								sendAccountPassword(txtFirstname.getText(), txtEmail.getText(), txtUsername.getText(), password);
								clearTextComponents(txtUsername, txtFirstname, txtLastname, txtPhone, txtBirthday, txtAddress, txtEmail);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}	
						}
					}	
				} else {
					lblMistake.setText("Ju lutem mbushni te gjitha fushat!");
					lblMistake.setVisible(true);
				}
			}
		});
		add(btnAdd);		
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(-62, 341, 95, 15);
		lblFirstname.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname");
		lblLastname.setBounds(62, 341, 95, 15);
		lblLastname.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblLastname);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(200, 341, 72, 15);
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblEmail);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(303, 341, 85, 15);
		lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblUsername);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setBounds(407, 341, 72, 15);
		lblPhone.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblPhone);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(510, 341, 95, 15);
		lblAddress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblAddress);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(613, 341, 59, 15);
		lblBirthday.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblBirthday);
		
		JLabel lblRoli = new JLabel("Role");
		lblRoli.setBounds(712, 341, 66, 15);
		lblRoli.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblRoli);
		
		lblMistake = new JLabel("Mistake Message");
		lblMistake.setBounds(137, 395, 660, 17);
		lblMistake.setVisible(false);
		
		cmbRole = new JComboBox();
		cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbRole.setBounds(712, 364, 85, 27);
		cmbRole.setModel(new DefaultComboBoxModel(new String[] {"S", "F"}));
		add(cmbRole);
		lblMistake.setForeground(new Color(255, 0, 0));
		lblMistake.setHorizontalAlignment(SwingConstants.CENTER);
		lblMistake.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblMistake);
		
		JButton btnFshij = new JButton("Delete");
		btnFshij.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnFshij.setBounds(859, 16, 75, 29);
		btnFshij.setForeground(Color.WHITE);
		btnFshij.setBackground(new Color(0, 102, 153));
		btnFshij.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow > -1) {
					String id = table.getModel().getValueAt(selectedRow, 0).toString();
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning!", JOptionPane.YES_NO_OPTION);
						
					if (dialogResult == JOptionPane.YES_OPTION) {
						
						try {
							connection = DBconnection.sqlConnector();
							callableStatement = connection.prepareCall("{call DeleteWorker(?)}");
							callableStatement.setString(1, id);
							callableStatement.execute();
							resultSet = callableStatement.getResultSet();
								
							callableStatement = connection.prepareCall("{call GetAllUsers()}");
							callableStatement.execute();
							resultSet = callableStatement.getResultSet();
							table.setModel(DbUtils.resultSetToTableModel(resultSet));
								
							callableStatement.close();
							resultSet.close();
							connection.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		add(btnFshij);
		
		JButton btnRuaj = new JButton("Edit");
		btnRuaj.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnRuaj.setForeground(Color.WHITE);
		btnRuaj.setBackground(new Color(0, 102, 153));
		btnRuaj.setBounds(761, 16, 85, 29);
		btnRuaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow > -1) {
					userId = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
					txtFirstname.setText(table.getModel().getValueAt(selectedRow, 1).toString());
					txtLastname.setText(table.getModel().getValueAt(selectedRow, 2).toString());
					txtEmail.setText(table.getModel().getValueAt(selectedRow, 3).toString());
					txtUsername.setText(table.getModel().getValueAt(selectedRow, 4).toString());
					txtPhone.setText(table.getModel().getValueAt(selectedRow, 5).toString());
					txtAddress.setText(table.getModel().getValueAt(selectedRow, 6).toString());
					txtBirthday.setText(table.getModel().getValueAt(selectedRow, 7).toString());
					cmbRole.setSelectedItem(table.getModel().getValueAt(selectedRow, 8).toString());
					btnAdd.setText("Save Changes");
					updateModeEnable = true;
					System.out.println("btnEdito - updateModeEnalbed: " + updateModeEnable);
				}
			}
		});
		add(btnRuaj);
		
		JButton btnPastroFushat = new JButton("");
		btnPastroFushat.setIcon(new ImageIcon("C:\\Users\\Gentris\\Downloads\\clear-button.png"));
		btnPastroFushat.setBounds(807, 278, 133, 29);
		btnPastroFushat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateModeEnable = false;
				clearTextComponents(txtUsername, txtFirstname, txtLastname, txtPhone, txtBirthday, txtAddress, txtEmail);
			}
		});
		add(btnPastroFushat);
		
		JLabel lblClear = new JLabel("");
		lblClear.setIcon(new ImageIcon(UsersPanel.class.getResource("/res/Clear-icon-32.png")));
		lblClear.setBounds(807, 362, 33, 32);
		add(lblClear);
		lblClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnAdd.setText("Add Worker");
				updateModeEnable = false;
				clearTextComponents(txtUsername, txtFirstname, txtLastname, txtPhone, txtBirthday, txtAddress, txtEmail);
			}
		});
		
		JButton button = new JButton("");
		button.setBackground(new Color(0, 102, 153));
		button.setBounds(807, 362, 31, 32);
		add(button);
		
	}
	
	// Shiko nese ndonje nga lista e objekteve te dhene e ka vleren "null".
	public static boolean checkIfNull(Object...objects) {
		for (Object object : objects) {
			if (object.equals("")) {
				return true;
			}
		}
		return false;
	}
	
	public static String generatePassword() {
		int ascii;
		String password = "";
		// 12 digit password
		for (int i = 0; i < 12; i++) {
			// Gjenerojme passwordin nga vargu i karaktereve te ASCII tabeles pres 047D deri 0126D
			ascii  = 47 + (int)(Math.random() * 79);
			password = password + Character.toString((char)ascii);;
		}
		return password;
	}
	
	public static int generateSalt() {
		return 100000 + (int)(Math.random() * 2000000);
	}
	
	public static String generateHash(String saltedPassword) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		try {
			byte[] saltedPasswordByteArray = saltedPassword.getBytes("UTF8");
			byte[] hashByteArray = messageDigest.digest(saltedPasswordByteArray);
			byte[] base64EncodedHash = Base64.getEncoder().encode(hashByteArray);
			return new String(base64EncodedHash);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void clearTextComponents(JTextComponent...components) {
		for (JTextComponent component : components) {
			component.setText("");
		}
	}
	
	public static boolean validateField(JTextComponent component, String regularExpression, String mistakeMessage, JLabel lblMistake) {
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(component.getText());
		
		if (!matcher.matches()) {
			lblMistake.setText(mistakeMessage);
			lblMistake.setVisible(true);
			return false;
		} else {
			lblMistake.setVisible(false);
			return true;
		}
		
	}
	
	public void sendAccountPassword(String name, String email, String username, String password) {
		String[] emailArray = {email};
		
		if(EmailSender.sendMail("noreplyknk@gmail.com","knkproject2017","Dear " + name + ", your account for " + 
				"'Prishtina Flights Management System' has been created. " + 
				"These are your initial login credentials: \n\n" + "Username: " + username + 
				"\n" + "Password: " + password + "\n\n PRISHTINA FLIGHTS MANAGEMENT SYSTEM"
								, emailArray, "Your Account for 'Prishtina Flights Management System' has been created"))
     	{
			System.out.println("Email was send successfully..");
     	} else {        
    	JOptionPane.showMessageDialog(null,"Email can't be sent at the moment. Please try again later!");
     	}
	}
}
