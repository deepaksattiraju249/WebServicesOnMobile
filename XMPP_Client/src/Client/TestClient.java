package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;

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


public class TestClient implements Runnable, MessageListener {
   
    ConnectionConfiguration connConfig = null;
    XMPPConnection xMPPConnection = null;
    BufferedReader readFromKeyboard = null;
    String toAddresss = null;
    String[] buddies = null;
    int buddySize = 0;


    public TestClient(){
        connConfig = new ConnectionConfiguration("10.200.40.153", 5222, "vysper.org");
        connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
//        connConfig.setSocketFactory(new DummySSLSocketFactory());
//        connConfig.setSocketFactory(SSLSocketFactory.getDefault());
        ////connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.required);
        connConfig.setSASLAuthenticationEnabled(true);
        connConfig.setSelfSignedCertificateEnabled(true);
        connConfig.setKeystorePath("bogus_mina_tls.cert");
        connConfig.setTruststorePath("bogus_mina_tls.cert");
        connConfig.setTruststorePassword("boguspw");

        xMPPConnection = new XMPPConnection(connConfig);      
        try {
        	xMPPConnection.connect();
            xMPPConnection.login("user1@vysper.org", "user1");
        } catch (XMPPException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));
       
        displayBuddyList();
       
        System.out.println("\n\n Enter the recipient's Email Id " + "buddy id in the list");
        try {
            String temp = readFromKeyboard.readLine();
            try {
                int j = Integer.parseInt(temp);
                toAddresss = getBuddy(j);
                System.out.println("Buddy <" + toAddresss + "> selected!");
            } catch(NumberFormatException exp) {
                toAddresss = temp;
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
              
        System.out.println("Enter your chat messages one by one!");
        System.out.println("[Enter \"quit\" to end the chat!]");
       
        String msg = "";
        while(true) {
            try {
                msg = readFromKeyboard.readLine();
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
           
            if(msg.equalsIgnoreCase("quit")) {
                System.out.println("--Chat Ended--");
                //xMPPConnection.disconnect();
                break;
            }
            if(msg.equalsIgnoreCase("presence")){
            	System.out.println("--Change Presence--");
            	changePresence();
            }
            if(msg.equalsIgnoreCase("absent")){
            	System.out.println("--Change Absence--");
            	changeAway();
            }
            if(msg.equalsIgnoreCase("add")){
            	System.out.println("--Add friend--");
            	setBuddy();
            }
            
            else {
                sendMessage(toAddresss, msg);
            }
        }
    }


    /**
     *
     * @param recipient
     * @param message
     * @throws NotConnectedException 
     */
    private void sendMessage(String recipient, String message){
            	
//    	ChatManager chatmanager = ChatManager.getInstanceFor(xMPPConnection);
//    	Chat chat = chatmanager.createChat(recipient, this);
        Chat chat = xMPPConnection.getChatManager().createChat(recipient, this);
        try {
        
            chat.sendMessage(message);
        } catch (XMPPException ex) {
            System.out.println("Error: " + ex.getMessage());
        } 
    }
    
    @Override

    public void processMessage(Chat chat, Message msg) {       
        String msgStr = msg.getBody();
        System.out.println("<" + chat.getParticipant() + ">  says " + msgStr);
    }
   
    public static void main(String[] args){
        TestClient testClient = new TestClient();      
    }

    @Override
    public void run() {      
    }
   
    private void displayBuddyList() {
        Roster roster = xMPPConnection.getRoster();
        Collection entries = roster.getEntries();

        System.out.println("\n\n Your Friends!!");
        System.out.println(entries.size() + " Friends \n");
        Iterator iter = entries.iterator();
        buddySize = entries.size();  
        buddies = new String[buddySize];
        int i = 0;
        while (iter.hasNext()) {
            RosterEntry rosterEntry = (RosterEntry) iter.next();
            buddies[i] = rosterEntry.getUser();
            i++;
            System.out.println(i + ". " + rosterEntry.getUser());          
        }
        System.out.println("----------");
    }
   
    private String getBuddy(int i) {
        String buddy = "";
        if(i > 0 && i <= buddySize) {
            buddy = buddies[i-1];
        } else {
            System.out.println("Invalid Buddy Id!! Selected default one!!");
            buddy = buddies[0];
        }
        return buddy;
    }
    
    public void setBuddy(){
	    Roster roster = xMPPConnection.getRoster();
	    try {
			roster.createEntry("rohit@vysper.org", "Rohit", null);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void getPresence(){
	    Roster roster = xMPPConnection.getRoster();
	    roster.addRosterListener(new RosterListener() {
	    	// Ignored events 
	    	public void entriesAdded(Collection<String> addresses) {}
	    	public void entriesDeleted(Collection<String> addresses) {}
	    	public void entriesUpdated(Collection<String> addresses) {}
	    	public void presenceChanged(Presence presence) {
	    		System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
	    	}
	    });
    }
    
    public void changePresence(){
    	Presence presence = new Presence(Presence.Type.available);
    	presence.setStatus("I'm Back Man!!");
    	xMPPConnection.sendPacket(presence);
    }
    
    public void changeAway(){
    	Presence presence = new Presence(Presence.Type.unavailable);
    	presence.setStatus("Away Man Away!!");
    	xMPPConnection.sendPacket(presence);
    }
}