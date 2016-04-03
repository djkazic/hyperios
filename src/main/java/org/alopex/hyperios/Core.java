package org.alopex.hyperios;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.alopex.hyperios.net.api.APIRouter;
import org.alopex.hyperios.util.GeoUtils;
import org.alopex.hyperios.util.Utils;
import org.alopex.hyperios.util.loaders.LoaderSettings;
import org.alopex.hyperios.util.loaders.ZIPMongoLoader;
import org.json.JSONObject;
import org.alopex.hyperios.helix.GACore;

public class Core {
	
	private static GeoUtils gutils;
	
	//TODO: make an invoke method with City var
	public static void main(String[] args) {
		for (String str : args) {
			if (str.equalsIgnoreCase("-genetic")) {
				GACore.execute();
			}
		}
		
		Utils.log("Core", "Initializing API endpoint...");
		APIRouter.init();
		
		System.out.println();
		Utils.log("Core", "Silencing MongoDB debug output...\n");
		suppressMongoDB();
		
		if (!LoaderSettings.skipZipVerify) {
			Utils.log("Core", "Scanning ZIP code => density cache pool...");
			ZIPMongoLoader.execute();
		} else {
			Utils.log("Core", "Skipping ZIP code => density cache pool checks...");
		}
		
		Utils.log("Core", "Creating instance of GeoUtils...\n");
		gutils = new GeoUtils();
		
		Utils.log("Core", "Standing by for API invocation...");
	}
	
	public static JSONObject simulate(String city) {
		//[medical, transportation, power]
		// DEBUG: skip (due to caching)
		if (!city.contains("Boston")) {
			// Medical
			gutils.googlePlacesSearch("Hospitals in " + city, "medical");
			System.out.println();
			
			// Transport
			gutils.googlePlacesSearch("Subway stops in " + city, "transportation");
			System.out.println();
			
			// Power
			gutils.googlePlacesSearch("Power plants in " + city, "power");
			System.out.println();
		}
		
		return GACore.execute();
	}
	
	private static void suppressMongoDB() {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
	    mongoLogger.setLevel(Level.SEVERE);
	    System.err.close();
	}
}
