package org.alopex.hyperios.util;

import java.util.List;

import org.alopex.hyperios.DB;
import org.alopex.hyperios.Settings;
import org.bson.Document;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

public class GeoUtils {
	
	private static GooglePlaces client;
	
	public void doSearch(String query, String type) {
		client = new GooglePlaces(Settings.placeApiKey);
		
		System.out.println("Searching for [" + query + "]...");
		List<Place> placesTransport = client.getPlacesByQuery(query, 40);
		System.out.println("Found " + placesTransport.size() + " results.");
		System.out.println("Listing locations:");
		for (Place place : placesTransport) {
			String address = place.getAddress();
			if (!address.equals("United States")) {
				System.out.println("\t" + place.getName() + " location: " + address);
				Document poiDoc = new Document("type", type)
						   .append("address", address)
						   .append("density", "-1");
				DB.getDatabase().getCollection("poi").insertOne(poiDoc);
			}
		}
	}
}
