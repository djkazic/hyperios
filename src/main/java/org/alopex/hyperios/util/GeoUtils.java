package org.alopex.hyperios.util;

import java.util.List;

import org.alopex.hyperios.APISettings;
import org.alopex.hyperios.db.DB;
import org.bson.Document;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

public class GeoUtils {

	private static GooglePlaces client;

	public void googlePlacesSearch(String query, String type) {
		client = new GooglePlaces(APISettings.placeApiKey);

		System.out.println("Searching for [" + query + "]...");
		List<Place> placesTransport = client.getPlacesByQuery(query, 40);
		System.out.println("Found " + placesTransport.size() + " results.");
		System.out.println("Listing locations:");
		int counter = 1;
		for (Place place : placesTransport) {
			String address = place.getAddress();
			String[] commaSplit = address.split(",");
			if (commaSplit != null && commaSplit.length > 2) {
				String[] spaceSplit = commaSplit[0].split(" ");
				if (spaceSplit != null && spaceSplit.length > 1) {
					if (!address.equals("United States")) {
						System.out.println("\t" + counter + " " + place.getName() + " location: " + address);
						Document poiDoc = new Document("type", type)
								.append("address", address)
								.append("density", "-1");
						DB.getDatabase().getCollection("poi").insertOne(poiDoc);
						counter++;
					}
				}
			}
		}
	}
}
