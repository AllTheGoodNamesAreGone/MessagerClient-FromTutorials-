package imp;

import javax.swing.JFrame;

public class Client_Test {

	public static void main(String[] args) {

		Client client = new Client("192.168.1.5");
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		client.startRunning();
		
		//This is the IP of a local host i.e the same computer that you are at.
		

	}

}
