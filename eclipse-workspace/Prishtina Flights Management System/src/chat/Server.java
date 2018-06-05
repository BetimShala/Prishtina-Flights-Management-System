package chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Server {
	private ServerFrame serverFrame;
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	public Server() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					serverFrame = new ServerFrame();
					serverFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
				e.printStackTrace();
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
	
	private String message, msgout;
	private DataInputStream dataIn = null;
	private DataOutputStream dataOut = null;
	private boolean firstMessage = true;
	
	int y = 30;
	
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
			e.printStackTrace();
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
					msgout = "PFMS Agent: " + conversationPanel.sendTextArea.getText();
					dataOut.writeUTF(msgout);	
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
				//conversationPanel.textArea.append(message + "\n");
				
				if(message.startsWith("ip,")){
					String clientIpAddress = message.split(",")[1];
					String clientName = message.split(",")[2];
					Server_f udpServerFrame = new Server_f(clientIpAddress, clientName);
					udpServerFrame.setVisible(true);
				}else{	
					appendMessage(message, new LeftArrowBubble(), new FlowLayout(FlowLayout.LEFT));
					if (firstMessage) {
						clientNameLabel.setText(message.split(":")[0]); 
						//= new JLabel(message.split(":")[0]);
						pnlScrollPane.add(clientNameLabel);
						pnlScrollPane.repaint();
						pnlScrollPane.validate();
						firstMessage = false;
					}
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
		
		conversationPanel.pnlChat.add(pnlMessageHolder);
		conversationPanel.pnlChat.add(pnlNameHolder);
		conversationPanel.pnlChat.setPreferredSize(
				new Dimension(0, 100 * conversationPanel.pnlChat.getComponents().length));
		conversationPanel.scrollPaneOfTextArea.getViewport().revalidate();
		
		y = y + 60;
	}
	
}