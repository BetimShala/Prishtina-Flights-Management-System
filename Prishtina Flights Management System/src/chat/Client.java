package chat;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import chat.RightArrowBubble;
import chat.LeftArrowBubble;

public class Client {
	private ClientFrame clientFrame;
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private String message, msgin, msg, msgout;
	private String clientFirstname;
	private String clientLastname;
	
	int y = 13;
	
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
			socket = new Socket("localhost", 8888);
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
			
			clientFrame.conversationPanel.sendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						
						msg = clientFirstname +" " + clientLastname + ": " + 
								clientFrame.conversationPanel.sendTextArea.getText();
						dataOut.writeUTF(msg);
						msgout = msg.split(":")[1];
						appendMessage(msgout, new RightArrowBubble(), new FlowLayout(FlowLayout.RIGHT));	
						clientFrame.conversationPanel.sendTextArea.setText("");
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			while (true) {
				message = dataIn.readUTF();
				msgin = message.split(":")[1];
				appendMessage(msgin, new LeftArrowBubble(), new FlowLayout(FlowLayout.LEFT));		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void appendMessage(String message, JPanel msgPanel, FlowLayout fl)
	{
		JLabel lblMessage = new JLabel(message);

		GroupLayout msgPanelLayout = new GroupLayout(msgPanel);
		msgPanel.setLayout(msgPanelLayout);
		
		JPanel pnlHolder = new JPanel();
		pnlHolder.setBounds(12, y, 380, 40);
		pnlHolder.setLayout(fl);
		pnlHolder.add(msgPanel);
		
		
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
		
		clientFrame.conversationPanel.pnlChat.add(pnlHolder);
		clientFrame.conversationPanel.pnlChat.setPreferredSize(
				new Dimension(0, 50 * clientFrame.conversationPanel.pnlChat.getComponents().length));
		clientFrame.conversationPanel.scrollPaneOfTextArea.getViewport().revalidate();
		
		y = y + 40;
	}
}
