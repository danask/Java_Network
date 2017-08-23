package Basics;


import java.net.*;
import java.util.*;


public class InetCheck {

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        InetAddress inet[] = null; // static -> new X, naver -> multiple domains-> array

        System.out.println("Address");

        String str = scan.nextLine();

        try {
            inet = InetAddress.getAllByName(str);
        } catch (UnknownHostException e){
            e.printStackTrace();
        }

        for(int i = 0; i< inet.length; i++)
        {
            System.out.println("Host Name by getHostName : " + inet[i].getHostName());
            System.out.println("Host Name by getHostAddress : " + inet[i].getHostAddress());
            System.out.println("toString = " + inet[i].toString());
            System.out.println("---------------------");
        }
    }
}