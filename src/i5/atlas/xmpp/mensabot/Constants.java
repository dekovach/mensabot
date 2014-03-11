/* 
 * ATLAS Group - Virtual Campfire - www.dbis.rwth-aachen.de
 * Copyright © 2010-2012 Lehrstuhl Informatik V, RWTH Aachen, Germany. All Rights Reserved.
 * 
 */
package i5.atlas.xmpp.mensabot;


/**
 * The immutable class with connection details
 * 
 * @author kovachev
 */
public class Constants {
	
	private static boolean debug = false;
	
	// URI of the XMPP server
	public static final String XMPP_HOST = "yourxmpp@server.com";
    
    // Port for XMPP connection
    public static final int XMPP_PORT = 5222;
    
    // Account username of the bot at the XMPP server 
    public static final String USER_NAME = "mensabot";
    
    // Password
    public static final String PASS = "PASSWORD";
    
    // XMPP JID
    public static final String SERVER_JID = USER_NAME+"@"+XMPP_HOST;
    
    // JID with resource
    public static final String SERVER_JID_WITH_RESOURCE = Constants.SERVER_JID + "/Smack";
    
    // the Multi-user chat room where the bot will be operational
    public static final String MUC_CHAT_ROOM = "chatroom@conference." + XMPP_HOST; 

    // JID of the chat room
    public static final String MUC_JID = MUC_CHAT_ROOM + "/" + USER_NAME;
    
    // URL of the Web resource
    public static final String MENSA_URL = "http://speiseplan.studentenwerk-aachen.de/mensa/tg_mensa_ahorn.std.php";
}
