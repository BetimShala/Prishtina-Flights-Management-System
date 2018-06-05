package src.chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chat.ConversationPanel;

import javax.swing.JTextField;
import javax.swing.JButton;

public class ClientFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtAsdf;
	ConversationPanel conversationPanel;
	JButton btnCallPfmsAgent;

	public ClientFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 422, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		conversationPanel = new ConversationPanel();
		conversationPanel.setBounds(0, 0, 416, 336);
		contentPane.add(conversationPanel);
		
		btnCallPfmsAgent = new JButton("Call PFMS Agent");
		btnCallPfmsAgent.setBounds(282, 335, 134, 36);
		contentPane.add(btnCallPfmsAgent);
		
	}
}
