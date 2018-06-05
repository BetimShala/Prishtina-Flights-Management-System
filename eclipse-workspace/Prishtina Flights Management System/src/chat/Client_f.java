package src.chat;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.SwingConstants;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client_f extends JFrame {

	private JPanel contentPane;
	JButton btnStart;
	JButton btnStop;
	
	public int port = 3333;
	public static  String serverIpAddress = "192.168.0.107";
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
	TargetDataLine audio_in;
	SourceDataLine audio_out;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_f frame = new Client_f();
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
			InetAddress inet = InetAddress.getByName(serverIpAddress);
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
			
		}
		catch(LineUnavailableException | UnknownHostException | SocketException e)
		{
			System.out.println("Problem with audio initialization");
		}
		
	}
	

	/**
	 * Create the frame.
	 */
	public Client_f() {
		
		init_audio();
			
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblKlienti = new JLabel("You are calling PFMS Agent");
		lblKlienti.setHorizontalAlignment(SwingConstants.CENTER);
		lblKlienti.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblKlienti.setBounds(41, 13, 338, 125);
		contentPane.add(lblKlienti);
		
		btnStop = new JButton("End Call");
		btnStop.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				calling = false;
				ds.close();
				dispose();
			}
		});
		btnStop.setBounds(153, 151, 109, 44);
		contentPane.add(btnStop);
	}
}
