package org.hvl.EndPoint;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hvl.CoAP.HandelMessage;
import org.hvl.CoAP.MessageFormat;
import org.hvl.CoAP.ReceiveMessage;
import org.hvl.CoAPClient.Request;

import Resource.Resource;


//import org.hvl.CoAP.Communicator;


public abstract class Endpoint implements ReceiveMessage, HandelMessage {

		public static final int DEFAULT_PORT = 5683;
		
		/** The default endpoint for CoAP */
		private Endpoint default_endpoint;

		/** The default endpoint for secure CoAP */
		private Endpoint default_secure_endpoint;
		
		//protected Communicator communicator;
		protected Resource parentResource;
		
		
		/** The logger */
		private final static Logger LOGGER = Logger.getLogger(Endpoint.class.getCanonicalName());

		
		
		
		public abstract void execute(Request request) throws IOException;
		
		private static Endpoint endpoint;
		
		public static Endpoint getEndpointManager() {
			return endpoint;
		}
		
		@Override
		public void receiveMessage(MessageFormat msg) {
			msg.handleBy(this);
		}

		public void sendRequest(Request request){
			
		}

		/*public int port() {
			Communicator communicator = null;
			return communicator != null ? communicator.port() : -1;
		}*/
		
		public synchronized Endpoint getDefaultEndpoint() {
			if (default_endpoint == null) {
				createDefaultEndpoint();
			}
			return default_endpoint;
		}
		
		private synchronized void createDefaultEndpoint() {
			if (default_endpoint != null)
				return;

			default_endpoint = new CoAPEndpoint();

			default_endpoint.start();
			LOGGER.log(Level.INFO, "Created implicit default endpoint {0}", default_endpoint.getAdress());
		}

		public synchronized Endpoint getDefaultSecureEndpoint() {
			try {
				if (default_secure_endpoint == null) {
					createDefaultSecureEndpoint();
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Exception while getting the default secure endpoint", e);
			}
			return default_secure_endpoint;
		}
		
		private synchronized void createDefaultSecureEndpoint() {
			if (default_secure_endpoint != null)
				return;

			LOGGER.config("Secure endpoint must be injected via setDefaultSecureEndpoint()");
		}
			

		private Object getAdress() {
			// TODO Auto-generated method stub
			return null;
		}

		private void start() {
			// TODO Auto-generated method stub
			
		}


}
