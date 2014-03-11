/* 
 * ATLAS Group - Virtual Campfire - www.dbis.rwth-aachen.de
 * Copyright © 2010-2012 Lehrstuhl Informatik V, RWTH Aachen, Germany. All Rights Reserved.
 * 
 */
package i5.atlas.xmpp.mensabot;

import java.util.Observable;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

import i5.atlas.xmpp.mensabot.Constants;

// TODO: Auto-generated Javadoc
/**
 * Helper class to communicate with the XMPP multi-user chatroom where the
 * mensabot is operational.
 * 
 */
public class MuChatter extends Observable implements PacketListener{
	
	/** The mu chatter. */
	private static MuChatter muChatter = null;
	
	/** The muc. */
	private MultiUserChat muc = null;
	
	/**
	 * Instantiates a new mu chatter.
	 */
	private MuChatter(){
		super();
	}
	
	/**
	 * Gets the.
	 *
	 * @return the mu chatter
	 */
	public static MuChatter get(){
		if (muChatter == null){
			muChatter = new MuChatter();
		} 
		
		return muChatter;
	}
	
	/**
	 * Join chat room.
	 *
	 * @return true, if successful
	 */
	public boolean joinChatRoom(){
		
		// Create a MultiUserChat using a Connection for a room
	    muc = new MultiUserChat(Mensabot.getConnection(), Constants.MUC_CHAT_ROOM);

	    
	    try {
	    	// joins the new room using a password and specifying
	        // the amount of history to receive. In this example we are requesting the last 5 messages.
	        DiscussionHistory history = new DiscussionHistory();
	        history.setMaxStanzas(0);
	        
	    	// join  to the muc room  
			muc.join(Constants.USER_NAME, "", history, SmackConfiguration.getPacketReplyTimeout());
			
			
			// adds packet listener as himself
			muc.addMessageListener(this);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return true;
	}
	
	/**
	 * Send message.
	 *
	 * @param messageContent the message content
	 */
	public void sendMessage(String messageContent){
		if(muc == null && !muc.isJoined()){
			System.out.println("muc == null && !muc.isJoined() !!!");
			System.out.println("Message "+messageContent+" can not be send!");
		}
		
		
		if((!Mensabot.getConnection().isConnected()) || (!Mensabot.getConnection().isAuthenticated()) ){
			System.out.println("Message:"+messageContent+" can not be send XMPP not connected!");
			return;
		}
		
		
		Message message = new Message();
		message.setTo(Constants.MUC_CHAT_ROOM);
		//message.setFrom(from);
		message.setBody(messageContent);
		message.setType(Message.Type.groupchat);
		
		try {
			muc.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
			System.out.println("An exception happened while sending Message "+messageContent+" !");
	
		}
	}

	/**
	 * Process incoming packets
	 * 
	 * @param p the packet
	 */
	public void processPacket(Packet p) {
		Message message = (Message)p;
		String body = message.getBody();
		
		if (message.getFrom().equalsIgnoreCase(Constants.MUC_JID)) {
			return;
		}
		
		if(body.matches("\\s*ping\\s+mensa.*")){
			setChanged();
			notifyObservers("mensaplan");
		}
	}

}
