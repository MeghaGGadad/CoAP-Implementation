package org.hvl.CoAPClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
	
	
	
	 public static void main(String args[]){
	 // args give message contents and server hostname
	 DatagramSocket aSocket = null;
	 if (args.length < 3) {
	 //System.out.println(� Usage: java UDPClient <message> <Host name> <Port number> �);
	 System.exit(1);
	 }

	 try {
	 aSocket = new DatagramSocket();
	 byte [] m = args[0].getBytes();
	 InetAddress aHost = InetAddress.getByName(args[1]);
	 int serverPort = Integer.valueOf(args[2]).intValue();
	 DatagramPacket request =
	 new DatagramPacket(m, args[0].length(), aHost, serverPort);
	 aSocket.send(request);
	 byte[] buffer = new byte[1000];
	 DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
	 aSocket.receive(reply);
	 System.out.println(new String(reply.getData()));
	 }
	 catch (SocketException e) {
	 System.out.println(e.getMessage());
	 }
	 catch (IOException e) {
	 System.out.println(e.getMessage());
	 }
	 finally {
	 if (aSocket != null)
	 aSocket.close();
	 }
	 }
	}