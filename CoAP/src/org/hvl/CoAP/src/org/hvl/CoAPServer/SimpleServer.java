package org.hvl.CoAPServer;

import java.net.SocketException;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.CoAPOptionRegistry;
import org.hvl.CoAP.Options;
import org.hvl.CoAPClient.DELETERequest;
import org.hvl.CoAPClient.GETRequest;
import org.hvl.CoAPClient.POSTRequest;
import org.hvl.CoAPClient.PUTRequest;
import org.hvl.CoAPClient.Request;
import org.hvl.EndPoint.Local;
import org.hvl.Resource.GetResource;
import org.hvl.Resource.LocalResource;
import org.hvl.EndPoint.Endpoint;




public class SimpleServer extends Local {

	/*
	 * Constructor for a new SampleServer
	 * 
	 */
	public SimpleServer() throws SocketException {
		
		// add resources to the server
		addResource(new HelloWorldResource());
		addResource(new StorageResource());
		addResource(new ToUpperResource());
		addResource(new SeparateResource());
	}

	// Resource definitions ////////////////////////////////////////////////////
	
	

	/*
	 * Defines a resource that returns "Hello World!" on a GET request.
	 * 
	 */
	private class HelloWorldResource extends GetResource {
		
		public HelloWorldResource() {
			super("helloWorld");
			setResourceName("GET a friendly greeting!");
		}
		
		@Override
		public void performGET(GETRequest request) {

			// create response
			Response response = new Response(CoAPCodeRegistries.RESP_CONTENT);
			
			// set payload
			response.setPayload("Hello World!");
			
			// complete the request
			request.respondback(response);
		}
	}
	
	private class ToUpperResource extends LocalResource {
		
		public ToUpperResource() {
			super("toUpper");
			setResourceName("POST text here to convert it to uppercase");
		}
		
		@Override
		public void performPOST(POSTRequest request) {
			
			// retrieve text to convert from payload
			String text = request.getPayloadString();
			
			// complete the request
			request.respondback(CoAPCodeRegistries.RESP_OK, text.toUpperCase());
		}
	}

	/*
	 * Defines a resource that stores POSTed data and that creates new
	 * sub-resources on PUT request where the Uri-Path doesn't yet point
	 * to an existing resource.
	 * 
	 */	
	private class StorageResource extends LocalResource {
		
		public StorageResource(String resourceIdentifier) {
			super(resourceIdentifier);
			setResourceName("POST your data here or PUT new resources!");
		}
		
		public StorageResource() {
			this("storage");
			isRoot = true;
		}
		
		@Override
		public void performGET(GETRequest request) {

			// create response
			Response response = new Response(CoAPCodeRegistries.RESP_CONTENT);
			
			// set payload
			response.setPayload(data);
			
			// set content type
			response.setOption(contentType);
			
			// complete the request
			request.respondback(response);
		}

		@Override
		public void performPOST(POSTRequest request) {

			// store payload
			storeData(request);
			
			// complete the request
			request.respondback(CoAPCodeRegistries.RESP_CHANGED);
		}
		
		@Override
		public void performPUT(PUTRequest request) {

			// store payload
			storeData(request);
			
			// complete the request
			request.respondback(CoAPCodeRegistries.RESP_CHANGED);
		}		
		
		@Override
		public void performDELETE(DELETERequest request) {
			
			// disallow to remove the root "storage" resource
			if (!isRoot) {
				
				// remove this resource
				remove();
				
				request.respondback(CoAPCodeRegistries.RESP_DELETED);
			} else {
				request.respondback(CoAPCodeRegistries.RESP_FORBIDDEN);
			}
		}
		
		@Override
		public void createNew(PUTRequest request, String newIdentifier) {
			
			// create new sub-resource
			StorageResource resource = new StorageResource(newIdentifier);
			addSubResource(resource);
			
			// store payload 
			resource.storeData(request);
			
			// complete the request
			request.respondback(CoAPCodeRegistries.RESP_CREATED);
		}
		
		private void storeData(Request request) {
			
			// set payload and content type
			data = request.getPayloadString();
			contentType = request.getFirstOption(CoAPOptionRegistry.CONTENT_TYPE);
			
			// signal that resource state changed
			changed();
		}
		
		private String data;
		private Options contentType;
		private boolean isRoot;
	}
	
	/*
	 * Defines a resource that returns "Hello World!" on a GET request.
	 * 
	 */
	private class SeparateResource extends GetResource {
		
		public SeparateResource() {
			super("separate");
			setResourceName("GET a response in a separate CoAP Message");
		}
		
		@Override
		public void performGET(GETRequest request) {

			// we know this stuff may take longer...
			// promise the client that this request will be acted upon
			// by sending an Acknowledgement
			request.acceptRequest();
			
			// do the time-consuming computation
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
			// create response
			Response response = new Response(CoAPCodeRegistries.RESP_CONTENT);
			
			// set payload
			response.setPayload("This message was sent by a separate response.\n" +
				"Your client will need to acknowledge it, otherwise it will be retransmitted.");
			
			// complete the request
			request.respondback(response);
		}
	}

	// Logging /////////////////////////////////////////////////////////////////
	
	@Override
	public void HandelRequest(Request request) {
		
		// output the request
		System.out.println("Incoming request:");
		request.log();
		
		// handle the request
		super.HandelRequest(request);
	}

	
	// Application entry point /////////////////////////////////////////////////
	
	public static void main(String[] args) {
		
		// create server
		try {
			
			Endpoint server  = new SimpleServer();
			int DEFAULT_PORT= 5683;
			
			System.out.printf("SimpleServer listening at port %d.\n", +DEFAULT_PORT);
			
		} catch (SocketException e) {

			System.err.printf("Failed to create SimpleServer: %s\n", 
				e.getMessage());
			return;
		}
		
	}

}
