package org.alopex.hyperios.util.loaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alopex.hyperios.db.DB;
import org.alopex.hyperios.util.Utils;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

public class ZIPMongoLoader {
	
	private static URL apiUrl;
	
	// Single class implementation to inject ZIP code => population density mappings
	public static void execute() {
		Utils.log("ZipMongoLoader", "Setting timeout global var...");
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		try {
			for (int i=2108; i < 2467; i++) {
				String numStr = String.format("%05d", i);
				long dupeCheck = DB.getDatabase().getCollection("density").count(new BsonDocument("zip", new BsonString(numStr)));
				if (dupeCheck == 0) {
					String density = zipToDensity(numStr);
					if (density != null) {
						System.out.println("Attempting to cache " + numStr);
						System.out.println("\t" + numStr+ " Passed: " + density);
						Document densityDoc = new Document("zip", "" + numStr)
												   .append("density", density);
						System.out.println("\t" + densityDoc + "\n");
						DB.getDatabase().getCollection("density").insertOne(densityDoc);	
					} else {
						System.out.println("\t" + numStr + " Failed");
					}
				} else {
					System.out.println("\tSkipping " + numStr);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
