package audioChat;

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

	public  int receiving_port = 3333;
	//public  int sending_port = 8888;
	public String add_client = "192.168.0.106";
	
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
					Server_f frame = new Server_f();
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
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			DataLine.Info info_out = new DataLine.Info(SourceDataLine.class, format);
			
			if(!AudioSystem.isLineSupported(info))
			{
				JOptionPane.showMessageDialog(null, "Line not supported !");
			}
			else if(!AudioSystem.isLineSupported(info_out))
			{
				JOptionPane.showMessageDialog(null, "Line not supported !");
			}
			
			DatagramSocket ds = new DatagramSocket(receiving_port);
			
			audio_in = (TargetDataLine) AudioSystem.getLine(info);
			audio_in.open(format);
			audio_in.start();
			
			recorder_thread r = new recorder_thread();
			InetAddress inet = InetAddress.getByName(add_client);
			r.audio_in = audio_in;
			r.dout = ds;
			r.server_ip = inet;
			r.server_port = receiving_port;
			
			audio_out = (SourceDataLine) AudioSystem.getLine(info_out);
			audio_out.open(format);
			audio_out.start();
			
			player_thread p = new player_thread();
			p.din = ds;
			p.audio_out = audio_out;

			calling = true;
			
			r.start();
			p.start();
			
			btnStart.setEnabled(false);
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
	public Server_f() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblServeri = new JLabel("SERVERI");
		lblServeri.setHorizontalAlignment(SwingConstants.CENTER);
		lblServeri.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblServeri.setBounds(81, 13, 264, 70);
		contentPane.add(lblServeri);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				init_audio();
			}
		});
		btnStart.setBounds(84, 164, 97, 25);
		contentPane.add(btnStart);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				calling = false;
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
			}
		});
		btnStop.setBounds(250, 164, 97, 25);
		contentPane.add(btnStop);
	}

}
