/* Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved. */ 

import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastServerThread extends Thread {

	private long FIVE_SECONDS	= 3000;
	private int castPort		= 4445; // <- port
	
    protected MulticastSocket socket = null;
	protected InputStream fi = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    public MulticastServerThread() throws IOException {
        super("MulticastServerThread");
        socket = new MulticastSocket(castPort);
        
        try {
			fi = new FileInputStream("/Users/suhyeongcho/eclipse-workspace/10주차/bin/data/ssu-intro.txt");
            in = new BufferedReader(new InputStreamReader(fi, "MS949"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

    public void run() {
    	while(moreQuotes) {
    		try {
    			byte[] buf = new byte[256];
    			
    			String dString = null;
    			if(in == null) dString = new Date().toString();
    			else dString = getNextQuote();
    			
    			System.out.println("read : " + dString);
    			buf = dString.getBytes();
    			
    			InetAddress group = InetAddress.getByName("230.0.0.1");
    			DatagramPacket packet = new DatagramPacket(buf,buf.length,group,castPort);
    			socket.send(packet);
    			
    			try {
    				System.out.println("sleep time : " + (long)(Math.random() * FIVE_SECONDS));
    				sleep((long)(Math.random() * FIVE_SECONDS));
    			}catch(InterruptedException e) {}
    		}catch(IOException e) {
    			e.printStackTrace();
    			moreQuotes = false;
    		}
    	}
    	socket.close();
    }
    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}
