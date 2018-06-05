package src.chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Client {
	private ClientFrame clientFrame;
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private String message, msgout;
	private String clientFirstname;
	private String clientLastname;
	private String address;
	
	int y = 30;
	
	public Client(String clientFirstname, String clientLastname) {
		clientFrame = new ClientFrame();
		clientFrame.setVisible(true);
		
		this.clientFirstname = clientFirstname;
		this.clientLastname = clientLastname;
		initiateSocketConnection();
	}
	
	public static void main(String[] args) {
		Client client = new Client("Firstname", "Lastname");
	}
	
	public void initiateSocketConnection() {
		try {
			socket = new Socket("192.168.0.107", 8888);
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			
			clientFrame.conversationPanel.sendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						
						msgout = clientFirstname +" " + clientLastname + ": " + 
								clientFrame.conversationPanel.sendTextArea.getText();
						dataOut.writeUTF(msgout);
						appendMessage(msgout, new RightArrowBubble(), new FlowLayout(FlowLayout.RIGHT));	
						clientFrame.conversationPanel.sendTextArea.setText("");
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			
			clientFrame.btnCallPfmsAgent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						address = InetAddress.getLocalHost().getHostAddress();
						dataOut.writeUTF("ip," + address + "," + clientFirstname + " " + clientLastname);
						
						Client_f udpClientFrame = new Client_f();
						udpClientFrame.setVisible(true);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			while (true) {
				message = dataIn.readUTF();
				appendMessage(message, new LeftArrowBubble(), new FlowLayout(FlowLayout.LEFT));		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void appendMessage(String message, JPanel msgPanel, FlowLayout fl)
	{
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel lblTime = new JLabel(sdf.format(cal.getTime()));
		lblTime.setFont(new Font("TimesRoman", Font.PLAIN, 10));
		JLabel lblMessage = new JLabel(message.split(":")[1]);
		JLabel lblName = new JLabel(message.split(":")[0]);
		lblName.setFont(new Font("TimesRoman", Font.PLAIN, 12));

		GroupLayout msgPanelLayout = new GroupLayout(msgPanel);
		msgPanel.setLayout(msgPanelLayout);
		
		JPanel pnlNameHolder = new JPanel();
		pnlNameHolder.setBounds(12, y-20, 380, 20);
		pnlNameHolder.setLayout(fl);
		pnlNameHolder.setBackground(Color.WHITE);
		pnlNameHolder.add(lblName);
		
		JPanel pnlMessageHolder = new JPanel();
		pnlMessageHolder.setBounds(12, y, 380, 40);
		pnlMessageHolder.setLayout(fl);
		pnlMessageHolder.setBackground(Color.WHITE);
		
		if(fl.getAlignment() == FlowLayout.LEFT){
			pnlMessageHolder.add(msgPanel);
			pnlMessageHolder.add(lblTime);
		}
		else{
			pnlMessageHolder.add(lblTime);
			pnlMessageHolder.add(msgPanel);
		}
		
		msgPanelLayout.setHorizontalGroup(
				msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            	.addGroup(msgPanelLayout.createSequentialGroup()
	            	.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(lblMessage)
	                .addGap(25, 25, 25))
				);
		msgPanelLayout.setVerticalGroup(
				msgPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(msgPanelLayout.createSequentialGroup()
	                .addComponent(lblMessage)
	                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		
		clientFrame.conversationPanel.pnlChat.add(pnlMessageHolder);
		clientFrame.conversationPanel.pnlChat.add(pnlNameHolder);
		clientFrame.conversationPanel.pnlChat.setPreferredSize(
				new Dimension(0, 100 * clientFrame.conversationPanel.pnlChat.getComponents().length));
		clientFrame.conversationPanel.scrollPaneOfTextArea.getViewport().revalidate();
		
		y = y + 60;
	}
}
