package org.alopex.hyperios.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alopex.hyperios.APISettings;
import org.alopex.hyperios.db.DB;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

public class GeoUtils {

	private static GooglePlaces client;

	public void googlePlacesSearch(String query, String type) {
		client = new GooglePlaces(APISettings.placeApiKey);

		System.out.println("Searching for [" + query + "]...");
		List<Place> placesTransport = client.getPlacesByQuery(query, 40);
		System.out.println("> Found " + placesTransport.size() + " results.");
		System.out.println("> Listing locations:");
		int counter = 1;
		Pattern p = Pattern.compile("\\d{5}");
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

							// Check for zip
							String cont = "";
							
							String[] words = address.split(" ");
							for (String piece : words) {
								String replPiece = piece.replaceAll(",", "");
								try {
									if (replPiece.length() == 5) {
										Integer.parseInt(replPiece);
										cont = replPiece;
										
										// Attempt lookup of zip density
										String zip = cont;
										System.out.println("\t\tAttempting lookup of ZIP " + zip);
										final Document poiDoc = new Document("type", type)
					                                           .append("address", address);
										long zipCount = DB.getDatabase().getCollection("density").count(new BsonDocument("zip", new BsonString(zip)));
										if (zipCount > 0) {
											FindIterable<Document> iterable = DB.getDatabase().getCollection("density").find(new BsonDocument("zip", new BsonString(zip)));
											iterable.forEach(new Block<Document> () {
												public void apply(final Document document) {
													poiDoc.append("density", document.getString("density"));
												}
											});
										} else {
											poiDoc.append("density", "0");
										}

										System.out.println("\t" + counter + " " + place.getName() + " location: " + address);
										DB.getDatabase().getCollection("poi").insertOne(poiDoc);
										counter++;
										// System.out.println("Piece " + cont + " SUCCESS | len = " + replPiece.length());
									} else {
										// System.out.println("Piece " + cont + " FAIL | len = " + replPiece.length());
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
}
