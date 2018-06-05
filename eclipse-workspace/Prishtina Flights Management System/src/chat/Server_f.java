package src.chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Server_f extends JFrame {

	private JPanel contentPane;
	JButton btnStart;
	JButton btnStop;
	
	private String clientIPAddress;
	private String clientName;
	public  int port = 3333;
	DatagramSocket ds;
	
	public static boolean calling = false;
	
	public static AudioFormat getAudioFormat()
	{
		float rate = 8000.0F;
		int sizeInBits = 16;
		int channel = 2;
		boolean signed = true;
		boolean bigEndian = false;
		
		return new AudioFormat(rate, sizeInBits, channel, signed, bigEndian);
	}
	public TargetDataLine audio_in;
	public SourceDataLine audio_out;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_f frame = new Server_f("","");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void init_audio()
	{
		try
		{
			AudioFormat format = getAudioFormat();
			DataLine.Info info_in = new DataLine.Info(TargetDataLine.class, format);
			DataLine.Info info_out = new DataLine.Info(SourceDataLine.class, format);
			
			if(!AudioSystem.isLineSupported(info_in))
			{
				JOptionPane.showMessageDialog(null, "Line not supported !");
			}
			else if(!AudioSystem.isLineSupported(info_out))
			{
				JOptionPane.showMessageDialog(null, "Line not supported !");
			}
			
			ds = new DatagramSocket(port);
			
			audio_in = (TargetDataLine) AudioSystem.getLine(info_in);
			audio_in.open(format);
			audio_in.start();
			
			recorder_thread r = new recorder_thread();
			InetAddress inet = InetAddress.getByName(clientIPAddress);
			
			r.audio_in = audio_in;
			r.socket = ds;
			r.server_ip = inet;
			r.server_port = port;
			
			audio_out = (SourceDataLine) AudioSystem.getLine(info_out);
			audio_out.open(format);
			audio_out.start();
			
			player_thread p = new player_thread();
			p.socket = ds;
			p.audio_out = audio_out;

			calling = true;
			
			r.start();
			p.start();
			
			btnStart.setEnabled(false);
			btnStop.setText("End Call");
			btnStop.setEnabled(true);
			
		}
		catch(LineUnavailableException | UnknownHostException | SocketException e)
		{
			System.out.println("Problem with audio initialization");
		}
	}
	

	/**
	 * Create the frame.
	 */
	public Server_f(String clientIpAddress, String clientName) {
		
		this.clientIPAddress = clientIpAddress;
		this.clientName = clientName;
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblServeri = new JLabel(clientName + " is calling");
		lblServeri.setHorizontalAlignment(SwingConstants.CENTER);
		lblServeri.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblServeri.setBounds(41, 13, 351, 138);
		contentPane.add(lblServeri);
		
		btnStart = new JButton("Accept Call");
		btnStart.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				init_audio();
			}
		});
		btnStart.setBounds(85, 179, 107, 37);
		contentPane.add(btnStart);
		
		btnStop = new JButton("Ignore Call");
		btnStop.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				calling = false;
				ds.close();
				dispose();
			}
		});
		btnStop.setBounds(240, 178, 107, 39);
		contentPane.add(btnStop);
	}

}
