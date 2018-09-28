package org.hvl.Resource;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.MediaTypeRegistery;
import org.hvl.CoAPClient.GETRequest;
import org.hvl.CoAPServer.Response;

public class DiscoveryResource extends GetResource {
	
	    private Resource parent;
		// Constants 
		// the default value for resource discovery resource
		public static final String DEFAULT_IDENTIFIER = "core";
		
		// Constructors 
		
		/*
		 * Constructor for a new DiscoveryResource
		 * 
		 * @param resources The resources used for the discovery
		 */
		public DiscoveryResource(Resource parent) {
			super(DEFAULT_IDENTIFIER);
			
			this.parent = parent;
			
			setContentTypeCode(MediaTypeRegistery.LINK_FORMAT);
		}
		
		// REST Operations 
		
		@Override
		public void performGET(GETRequest request) {
			
			// create response object
			Response response = new Response(CoAPCodeRegistries.RESP_CONTENT);
			
			// finishes the request by sending response back
			request.respondback(response);
		}
		
		
		
	}



