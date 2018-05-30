package chat;

public class StartClientSocketThread implements Runnable {
	private String clientFirstname;
	private String clientLastname;
	
	public StartClientSocketThread(String clientFirstname, String clientLastname) {
		this.clientFirstname = clientFirstname;
		this.clientLastname = clientLastname;
	}
	
	@Override
	public void run() {
		Client client = new Client(clientFirstname, clientLastname);
		
	}

}
