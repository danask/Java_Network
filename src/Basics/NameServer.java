package Basics;

import java.io.*;
import java.net.*;
import java.util.*;


// SocketServer : NameServer(8988)

public class NameServer 
{
    public static void main(String[] args)
    {
        ServerSocket ss = null;
        Socket s = null;
        OutputStream os = null;
        PrintStream ps = null;

        try{
            ss = new ServerSocket(8988); // open and waiting for clients
            s = ss.accept(); // waiting
            os = s.getOutputStream();
            ps = new PrintStream(os);
            ps.println("Welcome to NameServer via Socket!!");
            ps.flush();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(ss != null)
                    ss.close();
                if(os != null)
                    os.close();
                if(ps != null)
                    ps.close();
                if(s != null)
                    s.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}