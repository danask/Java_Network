package Basics;

import java.util.*;

class Account
{
    int balance = 1000;

//    public synchronized void withDraw(int money)
    public void withDraw(int money)
    {
        // if the balance is sufficient, withdraw it.
        if(balance >= money)
        {
            try{
                Thread.sleep(1000);
                balance -= money;
                System.out.println("balance - money : " + balance + " "+ money);
            }
            catch (Exception e)
            {
                System.out.println("[E] Error " + balance + " "+ money);
            }
        }
        else
        {
            System.out.println("Not enough balance!!! " + balance + " "+ money);
        }
    }
}

class MyThread implements Runnable
{
    Account acc = new Account();

    public void run()
    {
        while(acc.balance > 0)
        {
            int money = (int)(Math.random()*3+1)*100;
            acc.withDraw(money);
            System.out.println("BALANCE AFTER TRANSACTION : " + acc.balance);
        }
    }
}


public class SynchronizedThread {

    public static void main(String[] args)
    {
	// write your code here

//        System.out.println("hello");
        MyThread thread = new MyThread();

        Thread t1 = new Thread(thread);
        Thread t2 = new Thread(thread);

        t1.start();
        t2.start();
    }
}
