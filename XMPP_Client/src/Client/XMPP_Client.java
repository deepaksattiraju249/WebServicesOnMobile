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

public class XMPP_Client {
	int packetReplyTimeout = 500;
	ConnectionConfiguration config = null;
	XMPPConnection con = null;
	Roster roster = null;
	
	XMPP_Client(String userName, String password) throws Exception
	{
		SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);
		
		config = new ConnectionConfiguration("10.200.40.153",5222,"roster.org");
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
		config.setSASLAuthenticationEnabled(true);
		config.setSelfSignedCertificateEnabled(true);
        config.setKeystorePath("src/Client/bogus_mina_tls.cert");
        config.setTruststorePath("src/Client/bogus_mina_tls.cert");
		config.setTruststorePassword("boguspw");
		
		con = new XMPPConnection(config);
		
		con.connect();
		con.login(userName, password);		
		roster = con.getRoster();
		
	}
	
	
	public void PrintRoster()
	{
		Collection<RosterEntry> entries = roster.getEntries();
		for(RosterEntry entry: entries)
		{
			 System.out.println(String.format("Buddy:%1$s - Status:%2$s", entry.getName(), entry.getStatus()));

		}
	}
	public void setStatus(boolean available, String status) 
	{
        
        Presence.Type type = available? Type.available: Type.unavailable;
        Presence presence = new Presence(type);
        
        presence.setStatus(status);
        con.sendPacket(presence);
     
    }
	public void closeConnection()
	{
		if (con!=null && con.isConnected()) 
        {
            con.disconnect();
        }
	}
	
	void ReplytoMessage(Chat chat,Message message) throws Throwable
	{
		String reply="";
		
		
		try{
		chat.sendMessage(reply);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	public void sendMessage(String message, String buddyJID) throws XMPPException
	{
		
        System.out.println(String.format("Sending mesage '%1$s' to user %2$s", message, buddyJID));
        ChatManager chatManager = con.getChatManager();
        Chat chat = chatManager.createChat(buddyJID, new MessageListener() {

			@Override
			public void processMessage(Chat arg0, Message arg1) {
				// TODO Auto-generated method stub
				 try {
					ReplytoMessage(arg0,arg1);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
        	
        });
        chat.sendMessage(message);
        
        
    }
	
	public void getRosterDetail(String groupName)
	 {
	    	
	    	RosterGroup rostergroup = roster.getGroup(groupName);
	    	Collection<RosterEntry> entries = rostergroup.getEntries();
	    	Iterator<RosterEntry> iter = entries.iterator();
	    	int buddySize = entries.size();  
	        String[] buddies = new String[buddySize];
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
	    	int buddySize = entries.size();  
	        String[] buddies = new String[buddySize];
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
	    	
	    	int buddySize = entries.size();  
	        String[] buddies = new String[buddySize];
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
	        int buddySize = entries.size();  
	        String[] buddies = new String[buddySize];
	        
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
		 roster = con.getRoster();
	 }
	 
}
