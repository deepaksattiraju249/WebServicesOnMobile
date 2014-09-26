package Client;


import java.util.Collection;
import java.util.Iterator;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;


class XMPPClient {
	
	
	  private static final int packetReplyTimeout = 500; // millis  
	    public String server;
	    public int port;
	    public String servername;
	    
	    int buddySize = 0;
	    String[] buddies = null;
	    
	    public ConnectionConfiguration config;
	    public XMPPConnection connection;

	    private ChatManager chatManager;
	    private MessageListener messageListener;
	    
	    
	    private Roster roster =null;
	
	
	XMPPClient()
	{			
		SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);
		//Setup Initialization
		config = new ConnectionConfiguration("10.200.40.153",5222,"vysper.org");
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
		config.setSASLAuthenticationEnabled(true);
		config.setSelfSignedCertificateEnabled(true);
        config.setKeystorePath("src/Client/bogus_mina_tls.cert");
        config.setTruststorePath("src/Client/bogus_mina_tls.cert");
		config.setTruststorePassword("boguspw");
		connection = new XMPPConnection(config);
		
		try
		{
			connection.connect();
			connection.login("user1@vysper.org","user1");
		}
		catch(XMPPException ex)
		{
			System.out.println("=============Error=============");
			System.out.println(ex);
			System.out.println("Check the connection");
			System.out.println("===============================");			
		}
		
		System.out.println("Connection Successful");		
		
		
	}
	
	public void setStatus(boolean available, String status) 
	{
        
        Presence.Type type = available? Type.available: Type.unavailable;
        Presence presence = new Presence(type);
        
        presence.setStatus(status);
        connection.sendPacket(presence);
     
    }
	
	public void closeConnection()
	{
		if (connection!=null && connection.isConnected()) 
        {
            connection.disconnect();
        }
	}
	
	
	public void sendMessage(String message, String buddyJID) throws XMPPException
	{
        System.out.println(String.format("Sending mesage '%1$s' to user %2$s", message, buddyJID));
        Chat chat = chatManager.createChat(buddyJID, messageListener);
        chat.sendMessage(message);
    }
	
	 public void getRosterDetail(String groupName)
	 {
	    	
	    	RosterGroup rostergroup = roster.getGroup(groupName);
	    	Collection<RosterEntry> entries = rostergroup.getEntries();
	    	Iterator<RosterEntry> iter = entries.iterator();
	    	buddySize = entries.size();  
	        buddies = new String[buddySize];
	        int i = 0;
	        System.out.println(rostergroup.getName() + " have subscribed users: " + rostergroup.getEntryCount());
	        while (iter.hasNext()) 
	        {
	            RosterEntry rosterEntry = (RosterEntry) iter.next();
	            buddies[i] = rosterEntry.getUser();
	            i++;
	            System.out.println(rosterEntry.getUser());          
	        }
	    	
	  }
	 
	 public void getAllRosterGroup()
	 {
	    	
	    	Collection<RosterGroup> entries = roster.getGroups();
	    	Iterator<RosterGroup> iter = entries.iterator();
	    	buddySize = entries.size();  
	        buddies = new String[buddySize];
	        int i = 0;
	        System.out.println("Groups formed: ");
	        while (iter.hasNext()) 
	        {
	            RosterGroup rostergroup = (RosterGroup) iter.next();
	            buddies[i] = rostergroup.getName();
	            i++;
	            System.out.println(rostergroup.getName());          
	        }
	   }
	 public void getUserRoster(String userName)
	 {
	    	
	    	RosterEntry rosterentry = roster.getEntry(userName);
	    	Collection<RosterGroup> entries = rosterentry.getGroups();
	    	Iterator<RosterGroup> iter = entries.iterator();
	    	
	    	buddySize = entries.size();  
	        buddies = new String[buddySize];
	        int i = 0;
	        System.out.println("Groups Belong to: " + userName);
	        while (iter.hasNext()) 
	        {
	            RosterGroup rostergroup = (RosterGroup) iter.next();
	            buddies[i] = rostergroup.getName();
	            i++;
	            System.out.println(rostergroup.getName());
	        }
	    	
	    	
	 }
	 
	 
	 public void displayBuddyList()
	 {
	        
	        Collection<RosterEntry> entries = roster.getEntries();
	        System.out.println("Total Providers!!");
	        System.out.println(entries.size() + " Providers \n");
	        Iterator<RosterEntry> iter = entries.iterator();
	        buddySize = entries.size();  
	        buddies = new String[buddySize];
	        int i = 0;
	        while (iter.hasNext()) 
	        {
	            RosterEntry rosterEntry = (RosterEntry) iter.next();
	            buddies[i] = rosterEntry.getUser();
	            i++;
	            System.out.println(i + ". " + rosterEntry.getUser());          
	        }
	        System.out.println("----------");
	 }
	 public void updateRoster()
	 {
		 roster = connection.getRoster();
	 }
	 
	
}
