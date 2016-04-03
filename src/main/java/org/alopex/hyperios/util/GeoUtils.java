package org.alopex.hyperios.util;

import java.util.List;

import org.alopex.hyperios.APISettings;
import org.alopex.hyperios.db.DB;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

public class GeoUtils {

	private static GooglePlaces client;

	public void googlePlacesSearch(String query, String type) {
		client = new GooglePlaces(APISettings.placeApiKey);

		System.out.println("Searching for [" + query + "]...");
		List<Place> placesTransport = client.getPlacesByQuery(query, 40);
		// System.out.println("> Found " + placesTransport.size() + " results.");
		// System.out.println("> Listing locations:");
		int counter = 1;
		for (Place place : placesTransport) {
			String address = place.getAddress();
			String[] commaSplit = address.split(",");
			if (commaSplit != null && commaSplit.length > 2) {
				String[] spaceSplit = commaSplit[0].split(" ");
				if (spaceSplit != null && spaceSplit.length > 1) {
					if (!address.equals("United States")) {
						// Passes generic location check
						long dupeCheck = DB.getDatabase().getCollection("poi").count(new BsonDocument("address", new BsonString(address)));
						if (dupeCheck == 0) {
							Document poiDoc = new Document("type", type)
								                   .append("address", address)
							                       .append("density", "-1");
							System.out.println("\t" + counter + " " + place.getName() + " location: " + address);
							DB.getDatabase().getCollection("poi").insertOne(poiDoc);
							counter++;
						}
					}
				}
			}
		}
	}
}
