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
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Deepak Sattiraju
 */
public class Node 
{
    void sendMessage(String m)
    {
        String ServerName = "10.200.110.41"; // The group Server
        int port = 800;
        try{
        
        Socket client = new Socket(ServerName , port);
        System.out.println("I have just connected to the local host :D");
        OutputStream output = client.getOutputStream();
        DataOutputStream message = new DataOutputStream(output);
        message.writeUTF("Whats up Server How are you??");
        InputStream input = client.getInputStream();
        DataInputStream fromServer = new DataInputStream(input);
        System.out.println(fromServer.readUTF());
        client.close();
        }
        catch(Exception ex)
        {
            
        }
    }
    
    void requestAService(String service)
    {
        
    }
    
    public static void main(String [] args) throws IOException
    {
        Node thisNode = new Node();
        String service = "";
        thisNode.requestAService(service);
        
    }
    
}
