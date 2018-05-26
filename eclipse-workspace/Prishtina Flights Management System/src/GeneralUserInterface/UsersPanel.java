package GeneralUserInterface;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
public class UsersPanel extends JPanel {
	Connection connection = null;;
	CallableStatement callableStatement = null;
	ResultSet resultSet = null;;
	private JTable table;
	private JTextField txtKerko;
	private JTextField txtEmri;
	private JTextField txtMbiemri;
	private JTextField txtEmail;
	private JTextField txtUsername;
	private JTextField txtTel;
	private JTextField txtAdresa;
	private JTextField txtDatelindja;
	private JLabel lblMistake;
	private JComboBox cmbRoli;
	
	private boolean updateModeEnable = false;
	private int userId;

	/**
	 * Create the panel.
	 */
	public UsersPanel() {
		try {
			connection = DBconnection.sqlConnector();
			callableStatement = connection.prepareCall("{call GetAllUsers()}");
			callableStatement.execute();
			resultSet = callableStatement.getResultSet();
		} catch (Exception e) {
			e.getMessage();
		}
		setSize(1050, 650);
		setLayout(null);
		
		JLabel lblKerko = new JLabel("K\u00EBrko:");
		lblKerko.setBounds(16, 16, 50, 29);
		lblKerko.setFont(new Font("Segoi UI", Font.PLAIN, 16));
		add(lblKerko);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 49, 1018, 488);
		add(scrollPane);
		
		table = new JTable();
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
		txtKerko.setBounds(66, 16, 141, 26);
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
		
		txtEmri = new JTextField("");
		txtEmri.setBounds(16, 586, 116, 26);
		txtEmri.setColumns(10);
		add(txtEmri);
		
		txtMbiemri = new JTextField("");
		txtMbiemri.setBounds(140, 586, 130, 26);
		txtMbiemri.setColumns(10);
		add(txtMbiemri);
		
		txtEmail = new JTextField("");
		txtEmail.setBounds(278, 586, 95, 26);
		txtEmail.setColumns(10);
		add(txtEmail);
		txtEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtEmail.getText().equals("Email")) {
					txtEmail.setText("");
				}
			}
		});
		
		txtUsername = new JTextField("");
		txtUsername.setBounds(381, 586, 96, 26);
		txtUsername.setColumns(10);
		add(txtUsername);
		
		txtTel = new JTextField("");
		txtTel.setBounds(485, 586, 95, 26);
		txtTel.setColumns(10);
		add(txtTel);
		
		txtAdresa = new JTextField("");
		txtAdresa.setBounds(588, 586, 95, 26);
		txtAdresa.setColumns(10);
		add(txtAdresa);
		
		txtDatelindja = new JTextField("");
		txtDatelindja.setBounds(691, 586, 95, 26);
		txtDatelindja.setColumns(10);
		add(txtDatelindja);
		
		JButton btnShto = new JButton("Shto Punonj\u00EBs");
		btnShto.setBounds(901, 578, 133, 29);
		btnShto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!checkIfNull(txtEmri.getText(), txtMbiemri.getText(), txtEmail.getText(), txtUsername.getText(), txtTel.getText(), txtAdresa.getText(), txtDatelindja.getText(), cmbRoli.getSelectedItem())) {
					if ((validateField(txtDatelindja, "([0-9]{2})/([0-9]{2})/([0-9]{4})", "Ne fush�n 'Dat�lindja', ju lutem shkruani dat�n n� formatin 'dd/mm/yyy'.", lblMistake) &&
							true)) {
//						validateField(txtRoli, "[1234]{1}", "N� fush�n 'Roli', shkruani mes vlerave: 1, 2, 3 ose 4", lblMistake)
						if (updateModeEnable) {
							System.out.println("Insdie if(updateModeEnable)..");
							try {
								connection = DBconnection.sqlConnector();
								callableStatement = connection.prepareCall("{call UpdateWorker(?,?,?,?,?,?,?,?,?)}");
								callableStatement.setInt(1, userId);
								callableStatement.setString(2, txtUsername.getText());
								callableStatement.setString(3, txtEmri.getText());
								callableStatement.setString(4, txtMbiemri.getText());
								callableStatement.setString(5, txtTel.getText());
								callableStatement.setString(6, txtDatelindja.getText());
								callableStatement.setString(7, txtAdresa.getText());
								callableStatement.setString(8, txtEmail.getText());
								callableStatement.setString(9, cmbRoli.getSelectedItem().toString());
								callableStatement.execute();
								
								callableStatement = connection.prepareCall("{call GetAllUsers()}");
								callableStatement.execute();
								resultSet = callableStatement.getResultSet();
								table.setModel(DbUtils.resultSetToTableModel(resultSet));
								
								callableStatement.close();
								resultSet.close();
								connection.close();
								clearTextComponents(txtUsername, txtEmri, txtMbiemri, txtTel, txtDatelindja, txtAdresa, txtEmail);
								btnShto.setEnabled(true);
								btnShto.setText("Shto punonj�s");
								
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
								callableStatement.setString(2, txtEmri.getText());
								callableStatement.setString(3, txtMbiemri.getText());
								callableStatement.setString(4, txtTel.getText());
								callableStatement.setString(5, txtDatelindja.getText());
								callableStatement.setString(6, txtAdresa.getText());
								callableStatement.setString(7, txtEmail.getText());
								callableStatement.setString(8, cmbRoli.getSelectedItem().toString());
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
								btnShto.setEnabled(true);
								sendAccountPassword(txtEmri.getText(), txtEmail.getText(), password);
								clearTextComponents(txtUsername, txtEmri, txtMbiemri, txtTel, txtDatelindja, txtAdresa, txtEmail);
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
		add(btnShto);		
		
		JLabel lblEmri = new JLabel("Emri");
		lblEmri.setBounds(16, 563, 41, 15);
		lblEmri.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(lblEmri);
		
		JLabel lblMbiemri = new JLabel("Mbiemri");
		lblMbiemri.setBounds(140, 563, 47, 15);
		lblMbiemri.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(lblMbiemri);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(278, 563, 31, 15);
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(lblEmail);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(381, 563, 58, 15);
		lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(lblUsername);
		
		JLabel lblTel = new JLabel("Nr. Telefonit");
		lblTel.setBounds(485, 563, 72, 15);
		lblTel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(lblTel);
		
		JLabel lblAdresa = new JLabel("Adresa");
		lblAdresa.setBounds(588, 563, 41, 15);
		lblAdresa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(lblAdresa);
		
		JLabel lblDatlindja = new JLabel("Dat\u00EBlindja");
		lblDatlindja.setBounds(691, 563, 59, 15);
		lblDatlindja.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(lblDatlindja);
		
		JLabel lblRoli = new JLabel("Roli");
		lblRoli.setBounds(790, 563, 21, 15);
		lblRoli.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		add(lblRoli);
		
		lblMistake = new JLabel("Mistake Message");
		lblMistake.setBounds(215, 617, 660, 17);
		lblMistake.setVisible(false);
		
		cmbRoli = new JComboBox();
		cmbRoli.setBounds(790, 586, 85, 27);
		cmbRoli.setModel(new DefaultComboBoxModel(new String[] {"S", "F"}));
		add(cmbRoli);
		lblMistake.setForeground(new Color(255, 0, 0));
		lblMistake.setHorizontalAlignment(SwingConstants.CENTER);
		lblMistake.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		add(lblMistake);
		
		JButton btnFshij = new JButton("Fshij");
		btnFshij.setBounds(888, 16, 75, 29);
		btnFshij.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow > -1) {
					String id = table.getModel().getValueAt(selectedRow, 0).toString();
					int dialogResult = JOptionPane.showConfirmDialog(null, "A jeni i/e sigurt�?", "Kujdes!", JOptionPane.YES_NO_OPTION);
						
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
		
		JButton btnRuaj = new JButton("Edito");
		btnRuaj.setBounds(790, 16, 85, 29);
		btnRuaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow > -1) {
					userId = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
					txtEmri.setText(table.getModel().getValueAt(selectedRow, 1).toString());
					txtMbiemri.setText(table.getModel().getValueAt(selectedRow, 2).toString());
					txtEmail.setText(table.getModel().getValueAt(selectedRow, 3).toString());
					txtUsername.setText(table.getModel().getValueAt(selectedRow, 4).toString());
					txtTel.setText(table.getModel().getValueAt(selectedRow, 5).toString());
					txtAdresa.setText(table.getModel().getValueAt(selectedRow, 6).toString());
					txtDatelindja.setText(table.getModel().getValueAt(selectedRow, 7).toString());
					cmbRoli.setSelectedItem(table.getModel().getValueAt(selectedRow, 8).toString());
					btnShto.setText("Ruaj Ndryshimet");
					updateModeEnable = true;
					System.out.println("btnEdito - updateModeEnalbed: " + updateModeEnable);
				}
			}
		});
		add(btnRuaj);
		
		JButton btnPastroFushat = new JButton("Pastro Fushat");
		btnPastroFushat.setBounds(901, 541, 133, 29);
		btnPastroFushat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateModeEnable = false;
				clearTextComponents(txtUsername, txtEmri, txtMbiemri, txtTel, txtDatelindja, txtAdresa, txtEmail);
			}
		});
		add(btnPastroFushat);
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
	
	public void sendAccountPassword(String name, String email, String password) {
		String[] emailArray = {email};
		if(EmailSender.sendMail("noreplyknk@gmail.com","knkproject2017","I/E nderuar " + name
								+ ", ju njoftojm� se llogaria juaj n� 'Prishtina Flights Management System'"
								+ " �sht� hapur. K�to jan� kredencialet tuaja: \n\n" + "Email: " + email
								+ "\n" + "Password: " + password + "\n\n The HCI-PROJECT Accounts Team "
								, emailArray, "Llogaria e juaj n� 'Prishtina Flights Management System' �sht� hapur"))
     	{
			System.out.println("Emaili u dergua me sukses..");
     	} else {        
    	JOptionPane.showMessageDialog(null,"Emaili nuk mund te dergohet per momentin. Ju lutem provoni perseri!");
     	}
	}
}
