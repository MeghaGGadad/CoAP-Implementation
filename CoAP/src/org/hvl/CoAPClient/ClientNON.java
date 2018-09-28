package org.hvl.CoAPClient;

import java.io.IOException;
import java.net.InetAddress;

import org.hvl.CoAP.CoAPCodeRegistries.Code;
import org.hvl.CoAPServer.Response;


public class ClientNON {
	
	
	public static void main(String[] args) throws InterruptedException,IllegalArgumentException, IOException
	{
		while(true) 
		{		
			InetAddress IPAddress = InetAddress.getByName("localhost");
			 
		     String test="192.168.0.1";
			 //InetAddress inet = InetAddress.getByName(ipAddress);
		     InetAddress testinet = InetAddress.getByName(test);
		     //InetAddress inet_bouder_router = InetAddress.getByName(border_router);
	         System.out.println("Sending Ping Request to " + IPAddress+" "+testinet);
	         //System.out.println(inet.isSiteLocalAddress());
	         System.out.println(testinet.isReachable(0) ? "Gateway is reachable" : "Gateway is NOT reachable");
	         System.out.println(IPAddress.isReachable(3000) ? "Server is reachable" : "Server is NOT reachable");
	       if(IPAddress.isReachable(3000) && testinet.isReachable(3000))
            {		
				 Request request=new Request(Code.PUTRequest);
  		         request.setURI("coap://example.com:5683/sensors/temperature/large-update?mode=on");
				 //request.setURI("coap://example.net/.well-known/core");
  		     	 //request.send().setConfirmable(false);
  		         Response response;
		         //response = request.waitForResponse();
		         response = request.responseReceive();
		         response = request.waitForResponse(0);
	             //response.setType(Type.NON);
	             System.out.println("YOU REQUESTED FOR PUT Request-1 METHOD");
	         if(response != null)
  		     {
	              System.out.println(response.getPayloadString());
			      Thread.sleep(5000);
             }
			 else
			 {
				  System.out.println("Some Error Encountered 1");
				  Thread.sleep(5000);
			 }
							
		   }
	    
	     else 
		 {
			System.out.println("NETWORK ERROR , Will try in 5 seconds again");
			Thread.sleep(5000);
		 }

}
	}
}
