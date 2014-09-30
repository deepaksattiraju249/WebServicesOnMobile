package Server;

import org.apache.vysper.mina.S2SEndpoint;
import org.apache.vysper.storage.StorageProviderRegistry;
import org.apache.vysper.storage.inmemory.MemoryStorageProviderRegistry;
import org.apache.vysper.xmpp.authorization.AccountManagement;
import org.apache.vysper.xmpp.modules.roster.Roster;
import org.apache.vysper.xmpp.server.XMPPServer;
import org.jivesoftware.smack.ConnectionConfiguration;

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
