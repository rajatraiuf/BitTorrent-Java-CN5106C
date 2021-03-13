/*
 * The handler to handle any functionalities about interested/not interested message.
 */

package cnt5106C.MessageHandlers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.ByteBuffer;
import cnt5106C.*;
import cnt5106C.Message.*;

public class ChokeUnchokeHandler {
	/**
	 * Construct a Interested message.
	 * @param remotePeerId the peerid of remote peer
	 * @return the message just has been constructed
	 */
	public static Message construct(int remotePeerId, boolean isChoke) {
		if (isChoke){
			System.out.println("Sending choke byte field "+remotePeerId);
			return Message.actualMessageWrapper(remotePeerId, 2 , new byte[0]);
		}
		else {
			System.out.println("Sending unchoke byte field "+remotePeerId);
			return Message.actualMessageWrapper(remotePeerId, 3 , new byte[0]);
		}
	}
	
	/**
	 * Handle a interested/not interested message in a proper way.
	 * @param m the message to be handled
	 */
	public static void handle(Message m, boolean isChoke) throws Exception {
		//TODO this is just for testing
		if(isChoke){
			System.out.println("Received a choke message from peer " + m.remotePeerId);
			// PeerProcess.peers.get(m.remotePeerIndex).isChoked=true;
		}
		else{
			System.out.println("RECEIVED a unchoke message from peer " + m.remotePeerId);
			// Send request if needed
			DynamicPeerInfo p = PeerProcess.peers.get(m.remotePeerIndex);
			// List tmp = new ArrayList<int>(0);
			for(int i=0; i< PeerProcess.numOfPieces;i++){
				if(!p.getFilePieces(i)){
					System.out.println("REQUESTING peer " + m.remotePeerId+" for piece #"+i);
					PeerProcess.messageQueues.get(m.remotePeerIndex).add(RequestHandler.construct(m.remotePeerId,i));
				}
			}
		}
	}
}