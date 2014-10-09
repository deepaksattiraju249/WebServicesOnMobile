/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.Arrays;
import jdk.internal.org.xml.sax.InputSource;



/**
 *
 * @author Deepak Sattiraju
 */
public class ServerNode {
    
    public static ServerNode thisServer = new ServerNode();
    HashMap<String,String> roster;
    
    
    
    public ServerNode() 
    {
        roster = new HashMap<>();
    }
    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }
    
    void Register(String service , String IPAddress)
    {
        roster. put(service, IPAddress);
    }
    
    String fetch(String service)
    {
        return roster.get(service);
    }
    
    String MakePacket(String type , String Body)
    {
        return "";
    }
    
    String ProcessMessage(String receivedBody) throws Exception 
    {
        Document messageTree = loadXMLFromString(receivedBody);
        
        return " ";
       
    }
    
    public static void main(String [] args) throws IOException, Exception
    {
        
        int port = 5222;
        
        ServerSocket listener = new ServerSocket(port);
        while(true)
        {
            Socket senderSocket =  listener.accept();
            DataInputStream in = new DataInputStream(senderSocket.getInputStream());
            String receivedBody = in.readUTF();
            System.out.println("Message From : " + Arrays.toString(senderSocket.getLocalAddress().getAddress()) );
            System.out.println(receivedBody);
            String reply = "";
             
            
            try{
            reply = thisServer.ProcessMessage(receivedBody);
            }
            catch(Exception e)
            {
                
            }
            System.out.println("Reply Sent .. " );
            System.out.println(reply);
            DataOutputStream out = new DataOutputStream(senderSocket.getOutputStream());
            out.writeUTF(reply);
            senderSocket.close();
        }
        
        
    }
    
    
}
