package GeneralUserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import AutentificationAuthorization.Login;
import SharedPackage.DBconnection;
import SharedPackage.EmailSender;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class PFMSinterface extends JFrame {

	private JPanel contentPane;
	
	MessageDigest md ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PFMSinterface frame = new PFMSinterface();
					frame.setVisible(true);
					frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PFMSinterface() {
		
		Connection DBconn = DBconnection.sqlConnector();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1043);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setFocusable(true);
		
		JPanel pnlHeader = new JPanel();
		pnlHeader.setBackground(new Color(0, 102, 153));
		pnlHeader.setBounds(0, 0, 1902, 218);
		contentPane.add(pnlHeader);
		pnlHeader.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Arrivals");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(341, 173, 119, 45);
		pnlHeader.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Departures");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(472, 173, 116, 45);
		pnlHeader.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Kastriot Dobratiqi");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(1557, 173, 197, 45);
		pnlHeader.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Log Out");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(1754, 173, 119, 45);
		pnlHeader.add(lblNewLabel_3);
		
		JPanel pnlNav = new JPanel();
		pnlNav.setBackground(new Color(0, 102, 153));
		pnlNav.setBounds(0, 231, 298, 773);
		contentPane.add(pnlNav);
		pnlNav.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 10));
		
		JLabel lblHome = new JLabel("Home");
		lblHome.setBackground(Color.WHITE);
		lblHome.setForeground(Color.WHITE);
		lblHome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		lblHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblHome.setPreferredSize(new Dimension(290, 100));
		pnlNav.add(lblHome);
		
		JLabel lblAirlines = new JLabel("Airlines");
		lblAirlines.setForeground(Color.WHITE);
		lblAirlines.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAirlines.setHorizontalAlignment(SwingConstants.CENTER);
		lblAirlines.setPreferredSize(new Dimension(290, 100));
		pnlNav.add(lblAirlines);
		
		JLabel lblUsers = new JLabel("Users");
		lblUsers.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsers.setForeground(Color.WHITE);
		lblUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsers.setPreferredSize(new Dimension(290, 100));
		pnlNav.add(lblUsers);
		
		JPanel pnlFund = new JPanel();
		pnlFund.setLayout(new BoxLayout(pnlFund,BoxLayout.Y_AXIS));
		pnlFund.setBackground(new Color(0, 102, 153));
		pnlFund.setPreferredSize(new Dimension(290,470));
		pnlFund.add(Box.createRigidArea(new Dimension(0,220)));
		pnlNav.add(pnlFund);
		
		JLabel lblAccount = new JLabel("Account Managment");
		lblAccount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAccount.setForeground(Color.WHITE);
		lblAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccount.setMaximumSize(new Dimension(290, 100));
		pnlFund.add(lblAccount);
		
		JLabel lblHelp = new JLabel("Help");
		lblHelp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHelp.setForeground(Color.WHITE);
		lblHelp.setHorizontalAlignment(SwingConstants.CENTER);
		lblHelp.setMaximumSize(new Dimension(290, 100));
		pnlFund.add(lblHelp);
	
		JPanel pnlWorking = new JPanel();
		pnlWorking.setBackground(Color.WHITE);
		pnlWorking.setBounds(310, 231, 1592, 773);
		contentPane.add(pnlWorking);
		pnlWorking.setLayout(new BorderLayout());
		
		PFMAccountManagment pnlAccountManagment = new PFMAccountManagment();
		
		UsersPanel usersPanel = new UsersPanel();
		
		lblAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnlWorking.add(pnlAccountManagment, BorderLayout.CENTER);
				pnlWorking.validate();
				pnlWorking.repaint();
			}
		});
		
		lblUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnlWorking.add(usersPanel, BorderLayout.CENTER);
				pnlWorking.validate();
				pnlWorking.repaint();
			}
		});
		
		
	}
}
