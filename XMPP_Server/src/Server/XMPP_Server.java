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



public class XMPP_Server{
	
	XMPPServer thisServer;
	
	
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
		
	}
	
	public static void main(String args[]) throws Exception
	{
		XMPP_Server a = new XMPP_Server("deepak.org",1234);
		
	}
	
	
	
	
	
}
