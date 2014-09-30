package Server;

import java.io.File;

import org.apache.vysper.mina.S2SEndpoint;
import org.apache.vysper.mina.TCPEndpoint;
import org.apache.vysper.storage.StorageProviderRegistry;
import org.apache.vysper.storage.inmemory.MemoryStorageProviderRegistry;
import org.apache.vysper.xmpp.addressing.Entity;
import org.apache.vysper.xmpp.addressing.EntityImpl;
import org.apache.vysper.xmpp.authorization.AccountManagement;
import org.apache.vysper.xmpp.extension.websockets.WebSocketEndpoint;
import org.apache.vysper.xmpp.modules.extension.xep0049_privatedata.PrivateDataModule;
import org.apache.vysper.xmpp.modules.extension.xep0054_vcardtemp.VcardTempModule;
import org.apache.vysper.xmpp.modules.extension.xep0119_xmppping.XmppPingModule;
import org.apache.vysper.xmpp.modules.extension.xep0202_entity_time.EntityTimeModule;
import org.apache.vysper.xmpp.modules.roster.Roster;
import org.apache.vysper.xmpp.server.XMPPServer;
import org.apache.vysper.xmpp.server.s2s.XMPPServerConnector;
import org.apache.vysper.xmpp.server.s2s.XMPPServerConnectorRegistry;
import org.apache.vysper.xmpp.stanza.IQStanzaType;
import org.apache.vysper.xmpp.stanza.StanzaBuilder;

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

public class XMPP_Server{
	
	XMPPServer thisServer;
	private Roster roster;
	
	XMPP_Server(String service, int PORT) throws Exception
	{
		thisServer = new XMPPServer(service);
		
		StorageProviderRegistry providerRegistry = new MemoryStorageProviderRegistry();
		thisServer.setStorageProviderRegistry(providerRegistry);
		
		AccountManagement accountManagement = (AccountManagement) providerRegistry.retrieve(AccountManagement.class);
		S2SEndpoint s2sEndpoint = new S2SEndpoint();
		s2sEndpoint.setPort(PORT);
		thisServer.addEndpoint(s2sEndpoint);
		thisServer.start();
		update_Roster();
	}
	
	
	void update_Roster()
	{
		// Getting the roster of the central roster server
		ConnectionConfiguration config = new ConnectionConfiguration("10.200.40.153",5222,"roster.org");
		
		
	}
	
	
	
	
	
	public static void main(String args[]) throws Exception
	{
		XMPP_Server a = new XMPP_Server("deepak.org",1234);
		
	}
	
	
	
	
	
}
