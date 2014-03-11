/* 
 * ATLAS Group - Virtual Campfire - www.dbis.rwth-aachen.de
 * Copyright © 2010-2012 Lehrstuhl Informatik V, RWTH Aachen, Germany. All Rights Reserved.
 * 
 */
package i5.atlas.xmpp.mensabot;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;

import com.rizzoweb.alarm.AlarmListener;
import com.rizzoweb.alarm.AlarmManager;
import com.rizzoweb.alarm.AlarmSchedule;
import com.rizzoweb.alarm.PastDateException;

import i5.atlas.xmpp.mensabot.Constants;

/**
 * The main mensabot class. It logs in and logs out from an XMPP multi-user
 * chat room and listens to commands addressed to the mensabot.
 * 
 *  @author kovachev
 */
/**
 * @author kovachev
 *
 */
public class Mensabot implements Observer {
	
	/** The connection. */
	private static XMPPConnection connection = null;
	
	/** The conn listener. */
	private static ConnectionListener connListener = null;
	
	/**
	 * Instantiates a new Mensabot.
	 */
	private Mensabot(){
		super();
	}
	
	/**
	 * Creates an XMPP {@link www.xmpp.org} connection.
	 *
	 * @return the connection object
	 */
	public static XMPPConnection getConnection(){
		if(connection == null){
			
			XMPPConnection.DEBUG_ENABLED = false;
			
			ConnectionConfiguration connConfiguration = new ConnectionConfiguration(Constants.XMPP_HOST, Constants.XMPP_PORT);
			connection = new XMPPConnection(connConfiguration);
			
			try {
				connection.connect();
				
				//login
				connection.login(Constants.USER_NAME, Constants.PASS);
				System.out.println("XMPPClient"+ "Logged in as " + connection.getUser());
				
			}catch (XMPPException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	/**
	 * Configure provider manager.
	 *
	 * @param pm the pm
	 */
	private static void configureProviderManager(ProviderManager pm) {
	
	}
	
	/**
	 * Configure packet listeners.
	 */
	private static void configurePacketListeners(){
	
	} 
	
	
	/**
	 * Configure file manager.
	 */
	private static void configureFileManager(){
		FileTransferManager manager = new FileTransferManager(getConnection());    
	}
	
	public void logout() {
		if (connection != null) {
			connection.disconnect();
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){

		Mensabot mensabot = new Mensabot();
		
		Mensabot.getConnection();
		MuChatter.get().joinChatRoom();
		MuChatter.get().addObserver( mensabot );
		
		// set cron job to publish mensa plan every day at 12:55 pm
		AlarmManager mgr = new AlarmManager();
		
		AlarmListener listener = new AlarmListener() {
		  public void handleAlarm(AlarmSchedule schedule) {
			System.out.println("\u0007Alarm : " + schedule);
			
			Calendar calendar = Calendar.getInstance();
			System.out.println(calendar.get(Calendar.DAY_OF_WEEK));

			int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
			if ( currentDay > 1 && currentDay < 7 ) {
				MensaPlan mp = new MensaPlan();
				MuChatter.get().sendMessage( mp.getMensaPlan() );
			}
		  }
		};
		
		try {
			mgr.addAlarm(55, 12, -1, -1, -1, -1, listener);
		} catch (PastDateException e) {
			System.out.println("Got PastDateException");
			e.printStackTrace();
		}
		
	}

	
	/**
	 * This function sends message back to the chatroom
	 * 
	 *  @param obj	the observable object
	 *  @param arg	the argument object
	 */
	public void update(Observable obj, Object arg) {
		MensaPlan mp = new MensaPlan();
		MuChatter.get().sendMessage( mp.getMensaPlan() );		
	}
}
