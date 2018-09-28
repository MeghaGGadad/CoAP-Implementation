package org.hvl.CoAPClient;

import java.net.URI;


import org.hvl.CoAP.MessageFormat;
import org.hvl.CoAPClient.Request;
import org.hvl.CoAPServer.Response;
import org.hvl.EndPoint.Endpoint;

public class CoapClient {

	

	private String uri;
	
	/**
	 * Constructs a new CoapClient that sends requests to the specified URI.
	 *
	 * @param uri the uri
	 */
	public CoapClient(String uri) {
		this.uri = uri;
	}
	
	/**
	 * Constructs a new CoapClient that sends request to the specified URI.
	 * 
	 * @param uri the uri
	 */
	public CoapClient(URI uri) {
		this(uri.toString());
	}
	
	/**
	 * Sends a GET request and blocks until the response is available.
	 * 
	 * @return the CoAP response
	 */
	public Response get() {
		return synchronous(Request.newGet().setURI(uri));
	}

	
	
	/*
	 * Synchronously sends the specified request.
	 *
	 * @param request the request
	 * @return the CoAP response
	 */
	private Response synchronous(Request request) {
		try {
			return synchronous(request, getEffectiveEndpoint(request));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	
	
	/*
	 * Synchronously sends the specified request over the specified endpoint.
	 *
	 * @param request the request
	 * @param endpoint the endpoint
	 * @return the CoAP response
	 */
	private Response synchronous(Request request, Endpoint Endpoint) throws InterruptedException {
		Response response = send(request, Endpoint).waitForResponse(getTimeout());
		if (response == null) return null;
		else return new Response(response);
	}



	private Request send(Request request, Endpoint endpoint) {
		endpoint.sendRequest(request);
		return request;
	}



	
	/**
	 * Gets the timeout.
	 *
	 * @return the timeout
	 */
	public long getTimeout() {
		long timeout= 10000;
		return timeout;
	}


	private Endpoint endpoint;
	
	/**
	 * Gets the endpoint this client uses.
	 *
	 * @return the endpoint
	 */
	public Endpoint getEndpoint() {
		return endpoint;
	}
	
	protected Endpoint getEffectiveEndpoint(Request request) {
		Endpoint endpoint = getEndpoint();
		
		// custom endpoint
		if (endpoint != null) return endpoint;
		
		// default endpoints
		if (MessageFormat.COAP_URI_SCHEME.equals(request.getScheme())) {
			
			return Endpoint.getEndpointManager().getDefaultEndpoint();
		} /*else (MessageFormat.COAP_SECURE_URI_SCHEME.equals(request.getScheme())) {
			
			return Endpoint.getEndpointManager().getDefaultSecureEndpoint();
		}*/ 
		
	      return Endpoint.getEndpointManager().getDefaultEndpoint();
     
}
}


	/*public Response post(String str1, int i) {
		// TODO Auto-generated method stub
		return null;
	}*/


