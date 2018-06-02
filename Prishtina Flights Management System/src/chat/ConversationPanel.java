package chat;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollPane;

public class ConversationPanel extends JPanel {
	JPanel pnlChat;
	JButton sendButton;
	JTextArea sendTextArea;
	JScrollPane scrollPaneOfTextArea;
	
	private JScrollPane scrollPaneOfSend;
	
	public ConversationPanel() {
		setBounds(0, 0, 422, 330);
		setLayout(null);
		
		scrollPaneOfTextArea = new JScrollPane();
		scrollPaneOfTextArea.setBounds(0, 0, 422, 281);
		add(scrollPaneOfTextArea);
		
		pnlChat = new JPanel();
		pnlChat.setLayout(null);
		scrollPaneOfTextArea.setViewportView(pnlChat);
		
		sendButton = new JButton("Send");
		sendButton.setBounds(333, 307, 89, 23);
		add(sendButton);
		
		scrollPaneOfSend = new JScrollPane();
		scrollPaneOfSend.setBounds(0, 295, 313, 35);
		add(scrollPaneOfSend);
		
		sendTextArea = new JTextArea();
		scrollPaneOfSend.setViewportView(sendTextArea);
	}
}

