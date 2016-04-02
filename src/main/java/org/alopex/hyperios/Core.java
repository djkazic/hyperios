package org.alopex.hyperios;

import java.util.List;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

public class Core {
	public static void main(String[] args) {
		System.out.println("Connecting to Google Places API...");
		GooglePlaces client = new GooglePlaces(Settings.placeApiKey);
		
		System.out.println("Polling hospitals...");
		List<Place> places = client.getPlacesByQuery("Boston hospitals", 40);
		
		System.out.println("Found " + places.size() + " results.");
		
		System.out.println("Listing locations:");
		for (Place place : places) {
			//Place detailPlace = place.getDetails();
			System.out.println("\t" + place.getName() + " location: " + place.getAddress());
		}
		
		//TODO: remake client object
		
		client = new GooglePlaces(Settings.placeApiKey);
		
		System.out.println("Searching for transportation infrastructure...");
		List<Place> placesTransport = client.getPlacesByQuery("Boston subway", 40);
		
		System.out.println("Found " + placesTransport.size() + " results.");
		
		System.out.println("Listing locations:");
		for (Place place : placesTransport) {
			//Place detailPlace = place.getDetails();
			System.out.println("\t" + place.getName() + " location: " + place.getAddress());
		}
	}
}
