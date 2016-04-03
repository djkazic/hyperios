package org.alopex.hyperios.net.api;

import org.alopex.hyperios.Core;
import org.alopex.hyperios.util.Utils;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class Search extends ServerResource {
	
	@Post("application/text")
	public String process(JsonRepresentation entity) {
		this.getResponse().setAccessControlAllowOrigin("*");
		JSONObject responseJSON = new JSONObject();
		try {
			JSONObject json = entity.getJsonObject();
			if(json.length() > 0) {
				try {
					try {
						String city = json.getString("city");
						if (city.equals("")) {
							responseJSON.put("error", "City not specified");
						} else {
							responseJSON.put("value", Core.simulate(city));
						}
					} catch (Exception ex) {
						Utils.log(this, "Search API error: " + ex.getMessage());
						responseJSON.put("error", ex.getMessage());
					}
				} catch (Exception ex) {
					Utils.log(this, "Outer executive error: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			Utils.log(this, "Search API error: " + ex.getMessage());
			ex.printStackTrace();
		}
		return responseJSON.toString();
	}
}
