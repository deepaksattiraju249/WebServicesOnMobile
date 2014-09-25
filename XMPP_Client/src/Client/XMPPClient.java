package Client;

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
	
	ConnectionConfiguration config=null;
	XMPPConnection xmppCon=null;
	
	XMPPClient()
	{			
			config = new ConnectionConfiguration("10.200.40.153",5222,"vysper.org");
			
	
		//config = new ConnectionConfiguration("vysper.org");
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
		config.setSASLAuthenticationEnabled(true);
		config.setSelfSignedCertificateEnabled(true);
        config.setKeystorePath("/XMPP_Client/src/Client/bogus_mina_tls.cert");
        config.setTruststorePath("/XMPP_Client/src/Client/bogus_mina_tls.cert");
		config.setTruststorePassword("boguspw");
		xmppCon = new XMPPConnection(config);
		
		try
		{
			xmppCon.connect();
			xmppCon.login("user1@vysper.org","pass1");
			
			
		}
		catch(XMPPException ex)
		{
			System.out.println("=============Error=============");
			System.out.println(ex);
			System.out.println("Check the connection or Credentials");
			System.out.println("===============================");			
		}
		
		
		
		
		
		
		
		
	}

}
