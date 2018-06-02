package chat;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GeneralUserInterface.PFMSinterface;
//import test.ThreadingClass;
import chat.LeftArrowBubble;
import chat.RightArrowBubble;

public class Server {
	private ServerFrame serverFrame;
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	public Server() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					serverFrame = new ServerFrame();
					serverFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		listenForSocketConnections();
	}
	
	public static void main(String[] args) {
		Server server = new Server();
	}
	
	public void listenForSocketConnections() {
		try {
			serverSocket = new ServerSocket(8888);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		while (true) {
			ClientHandler clientHandler;
			try {
				clientHandler = new ClientHandler(serverSocket.accept(), serverFrame, serverFrame.pnlScrollPane, new JLabel());
				Thread thread = new Thread(clientHandler);
				thread.start();
			} catch (IOException e) {
				System.out.println("Here!!");
			}
		}
	}

}

class ClientHandler implements Runnable {
	private Socket clientSocket;
//	private JPanel containerPanel;
	private ConversationPanel conversationPanel;
	private JPanel pnlScrollPane;
	private JLabel clientNameLabel;
	private ServerFrame serverFrame;
	
	private String message, msgin, msg, msgout;
	private DataInputStream dataIn = null;
	private DataOutputStream dataOut = null;
	private boolean firstMessage = true;
	
	int y = 13;
	
	public ClientHandler(Socket clientSocket, ServerFrame serverFrame, JPanel pnlScrollPane, JLabel clientNameLabel) {
		this.clientSocket = clientSocket;
		this.serverFrame = serverFrame;
		this.pnlScrollPane = pnlScrollPane;
		this.clientNameLabel = clientNameLabel;
	}
	
	@Override
	public void run() {
		conversationPanel = new ConversationPanel();
		
		try {
			dataIn = new DataInputStream(clientSocket.getInputStream());
			dataOut = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Over here!!");
		}
		
		clientNameLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				serverFrame.containerPanel.removeAll();
				serverFrame.containerPanel.add(conversationPanel);
				serverFrame.containerPanel.revalidate();
				serverFrame.containerPanel.repaint();	
			}
		});
		
		conversationPanel.sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					msg = "PFMS Agent: " + conversationPanel.sendTextArea.getText();
					dataOut.writeUTF(msg);	
					msgout = msg.split(":")[1];
					//conversationPanel.textArea.append("PFMS Agent: " + conversationPanel.sendTextArea.getText() + "\n");
					appendMessage(msgout, new RightArrowBubble(), new FlowLayout(FlowLayout.RIGHT));
					conversationPanel.sendTextArea.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		while (true) {
			try {
				message = dataIn.readUTF();
				msgin = message.split(":")[1];
				//conversationPanel.textArea.append(message + "\n");
				appendMessage(msgin, new LeftArrowBubble(), new FlowLayout(FlowLayout.LEFT));
				
				if (firstMessage) {
					clientNameLabel.setText(message.split(":")[0]); 
					//= new JLabel(message.split(":")[0]);
					pnlScrollPane.add(clientNameLabel);
					pnlScrollPane.repaint();
					pnlScrollPane.validate();
					firstMessage = false;
				}
			} catch (IOException e) {
				System.out.println("Client left..");
				serverFrame.containerPanel.remove(conversationPanel);
				serverFrame.containerPanel.revalidate();
				serverFrame.containerPanel.repaint();
				
				pnlScrollPane.remove(clientNameLabel);
				pnlScrollPane.repaint();
				pnlScrollPane.validate();
				firstMessage = true;
				break;
			}
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
		
		conversationPanel.pnlChat.add(pnlHolder);
		conversationPanel.pnlChat.setPreferredSize(
				new Dimension(0, 50 * conversationPanel.pnlChat.getComponents().length));
		conversationPanel.scrollPaneOfTextArea.getViewport().revalidate();
		
		y = y + 40;
	}
	
}
