package Resource;


import java.util.HashMap;
import java.util.Map;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAPClient.DELETERequest;
import org.hvl.CoAPClient.GETRequest;
import org.hvl.CoAPClient.POSTRequest;
import org.hvl.CoAPClient.PUTRequest;


	public class LocalResource extends Resource {

		String resourceId;
		private Map<String, GETRequest> requestsObserve;
		
		// Constructors 
		
		public LocalResource(String resourceId, boolean hidden) {
			super(resourceId, hidden);
		}
		public LocalResource(String resourceId) {
			super(resourceId, false);
		}
		
		// Observing ///////////////////////////////////////////////////////////////
		
		public void addObserveRequest(GETRequest request) {
			
			if (request != null) {
			
				// lazy creation
				if (requestsObserve == null) {
					requestsObserve = new HashMap<String, GETRequest>();
				}
				
				requestsObserve.put(request.endpointID(), request);
				
				System.out.printf("Observation relationship between %s and %s established.\n",
					request.endpointID(), getResourceId());

			}
		}
		
		public void deleteObserveRequest(String endpointID) {
			
			if (requestsObserve != null) {
				if (requestsObserve.remove(endpointID) != null) {
					System.out.printf("Observation relationship between %s and %s terminated.\n",
						endpointID, getResourceId());
				}
			}
		}

		public boolean isObserved(String endpointID) {
			return requestsObserve != null && requestsObserve.containsKey(endpointID);
		}
		
		protected void executeObserveRequests() {
			if (requestsObserve != null) {
				for (GETRequest request : requestsObserve.values()) {
					performGET(request);
				}
			}
		}
		
		protected void changed() {
			executeObserveRequests();
		}
		
		// REST Operations 
		
		@Override
		public void performGET(GETRequest request) {
			request.respondback(CoAPCodeRegistries.RESP_NOT_IMPLEMENTED);
		}

		@Override
		public void performPUT(PUTRequest request) {
			request.respondback(CoAPCodeRegistries.RESP_NOT_IMPLEMENTED);
		}
		
		@Override
		public void performPOST(POSTRequest request) {
			request.respondback(CoAPCodeRegistries.RESP_NOT_IMPLEMENTED);
		}
		
		@Override
		public void performDELETE(DELETERequest request) {
			request.respondback(CoAPCodeRegistries.RESP_NOT_IMPLEMENTED);
		}

		@Override
		public void createNew(PUTRequest request, String newIdentifier) {
			request.respondback(CoAPCodeRegistries.RESP_NOT_IMPLEMENTED);
		}	
		
		
		
	}


