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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    void Register(String service , String IPAddress)
    {
        roster. put(service, IPAddress);
    }
    
    String fetch(String service)
    {
        return roster.get(service);
    }
    
    
    
    
    HashMap<String,String> ProcessMessage(HashMap<String,String> receivedBody) throws Exception 
    {
        HashMap<String, String> reply = new HashMap<>();
        String type = receivedBody.get("Type");
        switch(type)
        {
            case "Login":
            {
                
                Register(receivedBody.get("service"), receivedBody.get("IP"));
                reply.put("Body", "Registered");
            }
                
            case "Request":
            {
                reply.put("RequestedIP", fetch(reply.get("Who")));
            }
                
            case "Logout":
            {
                roster.remove(receivedBody.get("FromService"));
            }
                
            default:{
                
            }
                
        }
        
        return null;
       
    }
    
    public static void main(String [] args) throws IOException, Exception
    {
        
        int port = 5222;
        
        ServerSocket listener = new ServerSocket(port);
        while(true)
        {
            Socket senderSocket =  listener.accept();
            ObjectInputStream in = new ObjectInputStream(senderSocket.getInputStream());
            HashMap<String,String> receivedBody = (HashMap<String,String>)in.readObject();
            System.out.println("Message From : " + Arrays.toString(senderSocket.getLocalAddress().getAddress()) );
            System.out.println(receivedBody);
            
            
            
            HashMap<String,String> reply = new HashMap<>();
             
            
            try{
            reply = thisServer.ProcessMessage(receivedBody);
            }
            catch(Exception e)
            {
                
            }
            System.out.println("Reply Sent .. " );
            System.out.println(reply);
            ObjectOutputStream out = new ObjectOutputStream(senderSocket.getOutputStream());
            out.writeObject(reply);
            senderSocket.close();
        }
        
        
    }
    
    
}
