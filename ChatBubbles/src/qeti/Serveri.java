package qeti;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Serveri extends JFrame {

	private static JPanel contentPane;
	private JTextField txtMesazhi;
	private JButton btnSend;
	private static JScrollPane scrollPane;
	private static JPanel pnlChati;

	private static int y = 13;
	
	static ServerSocket ss;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Serveri frame = new Serveri();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		String msgin="";
		try 
		{
			ss=new ServerSocket(3333);
			s=ss.accept();
			din=new DataInputStream(s.getInputStream());
			dout=new DataOutputStream(s.getOutputStream());
			while(!msgin.equals("exit"))
			{
				msgin=din.readUTF();
				JLabel lblReceiveMesazhi = new JLabel(msgin);
				
				JPanel msgReceivePanel = new LeftArrowBubble();
				GroupLayout msgPanelLayout = new GroupLayout(msgReceivePanel);
				msgReceivePanel.setLayout(msgPanelLayout);
				
				JPanel pnlReceive = new JPanel();
				pnlReceive.setBounds(12, y, 380, 40);
				FlowLayout flReceive = new FlowLayout(FlowLayout.LEFT);
				pnlReceive.setLayout(flReceive);
				pnlReceive.add(msgReceivePanel);
				
				msgPanelLayout.setHorizontalGroup(
			            msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			            .addGroup(msgPanelLayout.createSequentialGroup()
			                .addGap(25, 25, 25)
			                .addComponent(lblReceiveMesazhi)
			                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			        );
				msgPanelLayout.setVerticalGroup(
			            msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
			            .addGroup(msgPanelLayout.createSequentialGroup()
			                .addComponent(lblReceiveMesazhi)
			                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			        );
				
				pnlChati.add(pnlReceive);
				scrollPane.getViewport().revalidate();			
				
				y = y + 40;
			}
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		
	}

	/**
	 * Create the frame.
	 */
	public Serveri() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
		        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 13, 408, 182);
		contentPane.add(scrollPane);
		
		pnlChati = new JPanel();
		pnlChati.setLayout(null);
		pnlChati.setPreferredSize(new Dimension(182, 380));
		scrollPane.setViewportView(pnlChati);
		
		txtMesazhi = new JTextField();
		txtMesazhi.setBounds(12, 208, 325, 32);
		contentPane.add(txtMesazhi);
		txtMesazhi.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				
				try 
				{
					String msgout="";
					msgout=txtMesazhi.getText().trim();
					dout.writeUTF(msgout);
					JLabel lblSendMesazhi = new JLabel(msgout);

					JPanel msgSendPanel = new RightArrowBubble();
					GroupLayout msgPanelLayout = new GroupLayout(msgSendPanel);
					msgSendPanel.setLayout(msgPanelLayout);
					
					JPanel pnlSend = new JPanel();
					pnlSend.setBounds(12, y, 380, 40);
					FlowLayout flSend = new FlowLayout(FlowLayout.RIGHT);
					pnlSend.setLayout(flSend);
					pnlSend.add(msgSendPanel);
					
					
					msgPanelLayout.setHorizontalGroup(
							msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				            .addGroup(msgPanelLayout.createSequentialGroup()
				                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				                .addComponent(lblSendMesazhi)
				                .addGap(25, 25, 25))
				        );
					msgPanelLayout.setVerticalGroup(
							msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				            .addGroup(msgPanelLayout.createSequentialGroup()
				                .addComponent(lblSendMesazhi)
				                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				        );
					
					pnlChati.add(pnlSend);
					scrollPane.getViewport().revalidate();
					
					y = y + 40;
					
					txtMesazhi.setText("");
				} 
				catch (Exception e) 
				{
					
				}
				
			}
		});
		btnSend.setBounds(349, 208, 71, 32);
		contentPane.add(btnSend);
		
	}

}
