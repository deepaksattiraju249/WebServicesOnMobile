/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperPeer;

import java.io.BufferedReader;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    String service = "GroupServer";
    public void FileUpdate() throws FileNotFoundException, IOException 
    {
        FileReader peers = new FileReader("peers.txt");
        BufferedReader reader = new BufferedReader(peers);
        String peer = "";
        String IP = "";
        while((peer = reader.readLine())!= null)
        {
            IP = reader.readLine();
            roster.put(peer, IP);
        }
        
    }
    
    HashMap<String,String> makeMessage(String body , String type,String to)
    {
        HashMap<String,String> message = new HashMap<>();
        message.put("From", service);
        message.put("To",to);
        message.put("Body",body);
        message.put("Type", type);
        return message;
    }
    
    
    
    
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
                
                Register(receivedBody.get("Service"), receivedBody.get("IP"));
                reply.put("Body", "Successfuly Registered");
            }
                
            case "Request":
            {
                reply.put("RequestedIP", fetch(receivedBody.get("Body")));
            }
            case "Roster":
            {
                reply = roster;
                reply.put("Type", "Roster");
            }
            case "Logout":
            {
                roster.remove(receivedBody.get("FromService"));
                reply.put("Body", "Removed");
            }
                
            default:
            {
                reply.put("Type", "Error");
                reply.put("Body","No type like the one's you have requested");
            }
                
        }
        
        return reply;
       
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
