package Resource;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAPClient.DELETERequest;
import org.hvl.CoAPClient.POSTRequest;
import org.hvl.CoAPClient.PUTRequest;

/* This class used to define read only resources ie. to perform get operation 
 * on the resources. No other operation allowed
 * */

 

public class GetResource extends LocalResource {
	
	
	
		
		// Constructors 
		
		public GetResource(String resourceId) {
			super(resourceId);
		}
		
		// REST Operations GET, PUT, POST and DELETE
		
		@Override
		public void performPUT(PUTRequest req) {
			req.respondback(CoAPCodeRegistries.RESP_METHOD_NOT_ALLOWED);
		}
		
		@Override
		public void performPOST(POSTRequest req) {
			req.respondback(CoAPCodeRegistries.RESP_METHOD_NOT_ALLOWED);
		}
		
		@Override
		public void performDELETE(DELETERequest req) {
			req.respondback(CoAPCodeRegistries.RESP_METHOD_NOT_ALLOWED);
		}
		
		@Override
		public void createNew(PUTRequest request, String newId) {
			request.respondback(CoAPCodeRegistries.RESP_FORBIDDEN);
		}
		
	}



