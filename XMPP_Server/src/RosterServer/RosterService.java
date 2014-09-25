package RosterServer;

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
import org.apache.vysper.xmpp.server.XMPPServer;
import org.apache.vysper.xmpp.server.s2s.XMPPServerConnector;
import org.apache.vysper.xmpp.server.s2s.XMPPServerConnectorRegistry;
import org.apache.vysper.xmpp.stanza.IQStanzaType;
import org.apache.vysper.xmpp.stanza.StanzaBuilder;

public class RosterService 
{
	public XMPPServer myServer=null;
	RosterService()
	{
		myServer = new XMPPServer("RosterService.org");
	}

}
