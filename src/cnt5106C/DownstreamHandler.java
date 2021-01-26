/**
 * The thread responsible for receiving messages from another host.
 */

package cnt5106C;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class DownstreamHandler extends Thread{
	private Socket socket; //The socket connected to the remote host.
	private int index; //Index of the specific remote host to communicate;
	private int myIndex; //Index of the local host.
	private ArrayList<DynamicPeerInfo> peers; //The peerInfo of the remote hosts.
	private ObjectInputStream input; //The input stream of the socket.
	private LinkedBlockingQueue<InterThreadMessage> queue;//The message queue for thread communication.
	
	/**
	 * The constructor of the thread. We need the socket, peerInfo and message queue to create the new thread.
	 * @param socket
	 * @param queue A queue to communicate with other threads.
	 * @param index TODO
	 * @param myIndex TODO
	 * @param peerInfo
	 */
	DownstreamHandler(Socket socket, ArrayList<DynamicPeerInfo> peers, LinkedBlockingQueue<InterThreadMessage> queue, int index, int myIndex){
		this.socket = socket;
		this.peers = peers;
		this.queue = queue;
		this.index = index;
		this.myIndex = myIndex;
		try {
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *Run the thread.
	 */
	public void run() {
		System.out.println("Downstream thread start to work.");
		while(true) {
			try {
				String msg = (String) input.readObject();
				System.out.println("Receive message from peer " + index + " : " + msg);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
