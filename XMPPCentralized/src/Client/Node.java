/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 *
 * @author Deepak Sattiraju
 */
public class Node 
{
    static Node thisNode = null;
    String service;
    String groupServer = "10.200.110.41"; // The group Server
    String MyIP;
    
    Node() throws UnknownHostException
    {
        InetAddress addr = InetAddress.getLocalHost();
	MyIP = addr.getHostAddress();

    }
    
    
    HashMap<String,String> makeMessage(String body , String type)
    {
        HashMap<String,String> message = new HashMap<>();
        message.put("From", service);
        message.put("To",groupServer);
        message.put("Body",body);
        message.put("Type", type);
        return message;
    }
    
    
    
    void sendMessage(HashMap<String,String> m)
    {
        
        int port = 5222;
        try{
        
        Socket client = new Socket(groupServer , port);
               
        
        OutputStream output = client.getOutputStream();
        ObjectOutputStream Channel = new ObjectOutputStream(output);
        Channel.writeObject(m);
        InputStream input = client.getInputStream();
        ObjectInputStream inp = new ObjectInputStream(input);
        
        HashMap<String,String> reply = (HashMap<String,String>)inp.readObject();
        System.out.println(reply.get("Body"));
        client.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    void Login()
    {
        HashMap<String,String> message = makeMessage(null, "Login");
        message.put("IP", MyIP);
        message.put("Service",service);
        sendMessage(message);
        
    }
    
    
    
    void requestAService(String service)
    {
        HashMap<String,String> message = makeMessage(service, "Request");
        sendMessage(message);
    }
    
    void LogOut()
    {
        HashMap<String,String> message = makeMessage(null, "Logout");
        sendMessage(message);
    }
    
    public static void main(String [] args) throws IOException
    {
        
        String service = null;
        thisNode = new Node();
        thisNode.service = "";
        thisNode.requestAService(service);
        
        
    }
    
}
