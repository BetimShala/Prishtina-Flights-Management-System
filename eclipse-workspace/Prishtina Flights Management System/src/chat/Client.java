package chat;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private ClientFrame clientFrame;
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private String message;
	private String clientFirstname;
	private String clientLastname;
	
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
						dataOut.writeUTF(clientFirstname +" " + clientLastname + ": " + clientFrame.conversationPanel.sendTextArea.getText());
						clientFrame.conversationPanel.textArea.append(clientFirstname +" " + clientLastname + ": " + clientFrame.conversationPanel.sendTextArea.getText() + "\n");
						clientFrame.conversationPanel.sendTextArea.setText("");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			while (true) {
				message = dataIn.readUTF();
				clientFrame.conversationPanel.textArea.append(message + "\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
