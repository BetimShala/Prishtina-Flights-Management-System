package chat;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GeneralUserInterface.PFMSinterface;
//import test.ThreadingClass;

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
	
	private String message;
	private DataInputStream dataIn = null;
	private DataOutputStream dataOut = null;
	private boolean firstMessage = true;
	
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
					dataOut.writeUTF("PFMS Agent: " + conversationPanel.sendTextArea.getText());
					conversationPanel.textArea.append("PFMS Agent: " + conversationPanel.sendTextArea.getText() + "\n");
					conversationPanel.sendTextArea.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		while (true) {
			try {
				message = dataIn.readUTF();
				conversationPanel.textArea.append(message + "\n");
				
				if (firstMessage) {
					clientNameLabel.setText(message.split(":")[0]); 
//					= new JLabel(message.split(":")[0]);
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
}
