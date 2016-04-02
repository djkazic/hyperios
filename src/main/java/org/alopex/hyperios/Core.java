package org.alopex.hyperios;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.alopex.hyperios.net.api.APIRouter;
import org.alopex.hyperios.util.GeoUtils;

public class Core {
	
	private static GeoUtils gutils;
	
	//TODO: make an invoke method with City var
	public static void main(String[] args) {
		System.out.println("Initializing API endpoint...\n");
		APIRouter.init();
		
		System.out.println("Silencing MongoDB debug output...\n");
		suppressMongoDB();
		
		System.out.println("Creating instance of GeoUtils...\n");
		gutils = new GeoUtils();
		
		gutils.doSearch("Hospitals in Boston", "medical");
		System.out.println();
		gutils.doSearch("Subway stops in Boston", "transportation");
		System.out.println();
	}
	
	private static void suppressMongoDB() {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
	    mongoLogger.setLevel(Level.SEVERE); 
	}
}
