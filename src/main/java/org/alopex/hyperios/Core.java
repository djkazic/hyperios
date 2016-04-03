package org.alopex.hyperios;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.alopex.hyperios.net.api.APIRouter;
import org.alopex.hyperios.util.GeoUtils;
import org.alopex.hyperios.util.Utils;

public class Core {
	
	private static GeoUtils gutils;
	
	//TODO: make an invoke method with City var
	public static void main(String[] args) {
		Utils.log("Core", "Initializing API endpoint...");
		APIRouter.init();
		
		System.out.println();
		Utils.log("Core", "Silencing MongoDB debug output...\n");
		suppressMongoDB();
		
		Utils.log("Core", "Creating instance of GeoUtils...\n");
		gutils = new GeoUtils();
		
		//TODO: zip code cache check
		
		//TODO: automated searches to API = [medical, transportation, power]
		// Medical
		gutils.googlePlacesSearch("Hospitals in Boston", "medical");
		System.out.println();
		
		// Transport
		gutils.googlePlacesSearch("Subway stops in Boston", "transportation");
		System.out.println();
		
		// Power
		gutils.googlePlacesSearch("Power plants in Boston", "power");
		System.out.println();
	}
	
	private static void suppressMongoDB() {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
	    mongoLogger.setLevel(Level.SEVERE);
	    System.err.close();
	}
}
