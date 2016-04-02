package org.alopex.hyperios.net.api;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class EchoPage extends ServerResource {
	
	@Get
	public String process() {
		return "Hyperios API v1.0";
	}
}