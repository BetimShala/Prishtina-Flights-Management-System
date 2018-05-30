package chat;

public class StartServerSocketThread implements Runnable {
	private Server server;
	
	
	@Override
	public void run() {
		Server server = new Server();
//		serverFinal.listenForSocketConnections();
	}
	
}