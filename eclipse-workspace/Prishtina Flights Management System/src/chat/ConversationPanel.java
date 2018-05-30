package chat;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class ConversationPanel extends JPanel {
	JTextArea textArea;
	JButton sendButton;
	JTextArea sendTextArea;
	
	private JScrollPane scrollPaneOfSend;
	
	public ConversationPanel() {
		setBounds(0, 0, 422, 330);
		setLayout(null);
		
		JScrollPane scrollPaneOfTextArea = new JScrollPane();
		scrollPaneOfTextArea.setBounds(0, 0, 422, 281);
		add(scrollPaneOfTextArea);
		
		textArea = new JTextArea();
		scrollPaneOfTextArea.setViewportView(textArea);
		
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

