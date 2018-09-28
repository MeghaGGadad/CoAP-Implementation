package org.hvl.CoAPClient;

import java.util.TimerTask;

import static java.lang.System.out;

import java.util.Date;

// Create a class extends with TimerTask
public class ScheduledTask extends TimerTask {

	Date now; // to display current time
	String payload = null;
	private String method;
	Request request = newRequest(method);
	// Add your task here
	public void run(){
		now = new Date(); // initialize date
		System.out.println("Time is :" + now); // Display current time
		System.out.println("Receiving response from Server...");
		out.println("Payload: " +payload );
		//out.println("Payload Size: " +request.getpayloadSize());
		out.println("Current Message Type is " +request.getType());
	    out.println("Current Method Code is " +request.getCode());
	    //out.println("Is confirmable message : " +request.isConfirmable());
		//out.println("Is reply : " +request.isReply());
		//out.println("Is Non confirmable message : " +request.isNonConfirmable());
		out.println("Current version is : " +request.getVersion());
		int id = 1234;
		out.println("Current MessageId is : " +request.setID(id));
	}
	
	
	private static Request newRequest(String method) {
		if (method.equals("GET")) {
			return new GETRequest();
		} else if (method.equals("POST")) {
			return new POSTRequest();
		} else if (method.equals("PUT")) {
			return new PUTRequest();
		} else if (method.equals("DELETE")) {
			return new DELETERequest();
		} else {
			return null;
		}
	}
	
}