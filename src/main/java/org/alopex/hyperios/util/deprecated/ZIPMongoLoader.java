package org.alopex.hyperios.util.deprecated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alopex.hyperios.DB;
import org.bson.Document;

public class ZIPMongoLoader {
	
	private static URL apiUrl;
	
	// Single class implementation to inject ZIP code => population density mappings
	public static void main(String[] args) throws IOException {
		for (int i=2108; i < 2467; i++) {
			String numStr = String.format("%05d", i);
			System.out.println("Attempting to cache " + numStr);
			String density = zipToDensity(numStr);
			if (density != null) {
				System.out.println("\tPASS: " + density);
				Document densityDoc = new Document("zip", "" + numStr)
										   .append("density", density);
				System.out.println("\t" + densityDoc);
				DB.getDatabase().getCollection("density").insertOne(densityDoc);
			} else {
				System.out.println("\tFAIL");
			}
		}
	}
	
	private static String zipToDensity(String zip) {
		try {
			String primaryRegexStr = Pattern.quote("(people per sq. mile)</span></dt><dd>") + "(.*?)" + Pattern.quote("<span");
			Pattern primaryPattern = Pattern.compile(primaryRegexStr);
			
			apiUrl = new URL("http://www.uszip.com/subserp.php?qtype=1&st=" + zip);
			BufferedReader in = new BufferedReader(new InputStreamReader(apiUrl.openStream()));
			
			StringBuilder sb = new StringBuilder();
			String inputLine = "";
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			
			String allPage = sb.toString();
			Matcher matcher = primaryPattern.matcher(allPage);
			while (matcher.find()) {
				String match = "";
				if (!(match = matcher.group(1)).equals("")) {
					return match.replaceAll("[^\\d.]", "");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}	
