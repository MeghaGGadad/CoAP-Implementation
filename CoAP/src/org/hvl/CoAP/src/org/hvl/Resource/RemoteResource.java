package org.hvl.Resource;

import java.util.StringTokenizer;



public class RemoteResource {

	
	
	public static RemoteResource newRoot(String linkFormat) {
		RemoteResource resource = new RemoteResource();
		resource.setResourceId("");
		resource.setResourceName("parent");
		return resource;
	}

	

	private String resourceName;

	
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	private String resourceId;

	private void setResourceId( String resourceId) {
		this.resourceId = resourceId;
		
		
	}

	public void log() {
		// TODO Auto-generated method stub
		
	}

	public void addSubResource(DiscoveryResource discoveryResource) {
		// TODO Auto-generated method stub
		
	}

}
