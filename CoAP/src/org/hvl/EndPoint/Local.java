package org.hvl.EndPoint;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.List;

import org.hvl.CoAP.CoAPCodeRegistries;
import org.hvl.CoAP.CoAPOptionRegistry;
//import org.hvl.CoAP.Communicator;
import org.hvl.CoAP.Options;
//import org.hvl.CoAP.RemoteResource;
import org.hvl.CoAPClient.GETRequest;
import org.hvl.CoAPClient.PUTRequest;
import org.hvl.CoAPClient.Request;
import org.hvl.CoAPServer.Response;


import Resource.LocalResource;
import Resource.GetResource;
import Resource.Resource;

import Resource.*;



public class Local extends Endpoint{
	
		private class ParentResource extends GetResource {

			public ParentResource() {
				super("");
				setResourceName("parent");
			}
			
			@Override
			public void performGET(GETRequest request) {
				
				// create response
				Response response = new Response(CoAPCodeRegistries.RESP_CONTENT);
				ByteArrayOutputStream data = new ByteArrayOutputStream();
				PrintStream out = new PrintStream(data);
				
				printEndpointInfo(out);
				
				response.setPayload(data.toByteArray());
				
				// complete the request
				request.respondback(response);
			}		
		}

		private ParentResource parentResource;
		
		public Local(int port) throws SocketException {
			
			// initialize communicator
			//this.communicator = new Communicator(port, false);
			//this.communicator.registerReceiver(this);
			
			// initialize resources
			this.parentResource = new ParentResource();
			
			this.wellKnownResource = new LocalResource(".well-known", true);
			this.wellKnownResource.setResourceName("");
			
			this.discoveryResource = new DiscoveryResource(parentResource);
			
			parentResource.addSubResource(wellKnownResource);
			wellKnownResource.addSubResource(discoveryResource);
		
		}
		
		public Local() throws SocketException {
			this(DEFAULT_PORT);
		}
		
		@Override
		public void execute(Request request) {
			
			// check if request exists
			if (request != null) {
				
				// retrieve resource identifier
				String resourceIdentifier = getResourceIdentifier(request);
				
				// lookup resource
				LocalResource resource = getResource(resourceIdentifier);
				
				// check if resource available
				if (resource != null) {
					
					// invoke request handler of the resource
					request.dispatch(resource);
					
					// check if resource is to be observed
					if (
						request instanceof GETRequest && 
						request.hasOption(CoAPOptionRegistry.OBSERVE)
					) {
						
						// establish new observation relationship
						resource.addObserveRequest((GETRequest) request);

					} else if (resource.isObserved(request.endpointID())){
					
						// terminate observation relationship on that resource
						resource.deleteObserveRequest(request.endpointID());
					}
					
				} else if (request instanceof PUTRequest) {
					
					createByPUT((PUTRequest) request);
				} else {
					
					// resource does not exist
					System.out.printf("[%s] Resource not found: '%s'\n",
						getClass().getName(), resourceIdentifier);
					
					request.respondback(CoAPCodeRegistries.RESP_NOT_FOUND);
				}
			}
		}
		
		private void createByPUT(PUTRequest request) {
			
			String identifier = getResourceIdentifier(request);
			int pos = identifier.lastIndexOf('/');
			if (pos != -1 && pos < identifier.length()-1) {
				String parentId = identifier.substring(0, pos);
				String newId = identifier.substring(pos+1);
				Resource parent = getResource(parentId);
				if (parent != null) {
					parent.createNew(request, newId);
				} else {
					request.respondback(CoAPCodeRegistries.RESP_NOT_FOUND, 
						String.format("Unable to create '%s' in '%s': Parent does not exist.",
						newId, parentId));
				}
			} else {
				// not allowed to create new root resources
				request.respondback(CoAPCodeRegistries.RESP_FORBIDDEN);
			}
		}
		
		public void addResource(LocalResource resource) {
			if (parentResource != null) {
				parentResource.addSubResource(resource);
			}
		}
		
		public void removeResource(String resourceIdentifier) {
			if (parentResource != null) {
				parentResource.removeSubResource(resourceIdentifier);
			}
		}
		
		public LocalResource getResource(String resourceIdentifier) {
			if (parentResource != null) {
				return (LocalResource)parentResource.getResource(resourceIdentifier);
			} else {
				return null;
			}
		}
		
		private static String[] getResourcePath(Request request) {
			List<Options> options = request.getOptions(CoAPOptionRegistry.URI_PATH);
			if (options != null && options.size() > 0) {
				String first = options.get(0).getStringVal();
				// for backwards compability with draft 3 where
				// there is only one Uri-Path option containing slashes
				if (first.indexOf('/') >= 0) {
					return first.split("/");
				} else {
					String[] path = new String[options.size()];
					for (int i = 0; i < path.length; i++) {
						path[i] = options.get(i).getStringVal();
					}
					return path;
				}
			} else {
				return null;
			}
		}
		
		private static String getResourceIdentifier(Request request) {
			
			List<Options> uriPaths = request.getOptions(CoAPOptionRegistry.URI_PATH);
			
			if (uriPaths == null) return "";
			
			StringBuilder builder = new StringBuilder();
			for (Options uriPath : uriPaths) {
				builder.append('/');
				builder.append(uriPath.getStringVal());
			}
			return builder.toString();
		}

		private Resource wellKnownResource;
		private DiscoveryResource discoveryResource;

		@Override
		public void HandelRequest(Request request) {
			execute(request);
		}

		@Override
		public void HandelResponse(Response response) {
			//response.handle();
		}
		
		protected void printEndpointInfo(PrintStream out) {
			
			
			out.println("************************************************************");
			out.println("This CoAP endpoint");
			
		}

	}

	      