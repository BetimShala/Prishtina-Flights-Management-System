package chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import GeneralUserInterface.PFMSinterface;

import java.awt.FlowLayout;

public class ServerFrame extends JFrame {

	private JPanel contentPane;
	JPanel containerPanel;
	JPanel pnlScrollPane;
	ConversationPanel conversationPanel;
	JLabel clientNameLabel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 ServerFrame serverFrame = new ServerFrame();
					serverFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		containerPanel = new JPanel();
		containerPanel.setBounds(10, 11, 422, 330);
		containerPanel.setLayout(null);
//		containerPanel.add(new ConversationPanel());
		contentPane.add(containerPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(442, 11, 142, 330);
		contentPane.add(scrollPane);
		
		pnlScrollPane = new JPanel();
		scrollPane.setViewportView(pnlScrollPane);
		pnlScrollPane.setLayout(new BoxLayout(pnlScrollPane, BoxLayout.Y_AXIS));
	}

}
