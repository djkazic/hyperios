package org.alopex.hyperios;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.alopex.hyperios.util.GeoUtils;

public class Core {
	
	public static void main(String[] args) {
		System.out.println("Silencing MongoDB debug output...\n");
		suppressMongoDB();
		
		GeoUtils.doSearch("Hospitals in Boston", "medical");
		GeoUtils.doSearch("Subways in Boston", "transportation");
		
		System.out.println();		
	}
	
	private static void suppressMongoDB() {
		System.setProperty("DEBUG.MONGO", "false");          
		System.setProperty("DB.TRACE", "false");             
		Logger.getLogger("com.mongodb").setLevel(Level.OFF);
	}
}
