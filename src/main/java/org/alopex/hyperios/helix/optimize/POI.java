package org.alopex.hyperios.helix.optimize;

import java.util.Arrays;

import org.alopex.hyperios.db.DB;
import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;

public class POI {
	private String type;
	private String address;
	private double density;
	
	public POI () {}
	
	public POI(boolean randomize) {
		if (randomize) {
			AggregateIterable<Document> iterable = DB.getDatabase().getCollection("poi").aggregate(Arrays.asList(new Document("$sample", new Document("size", 1))));
			iterable.forEach(new Block<Document> () {
				public void apply(final Document document) {
					type = document.getString("type");
					address = document.getString("address");
					density = Double.parseDouble(document.getString("density"));
				}
			});
		}
	}
	
	public String toString() {
		return "\n\ttype=" + type +
				"\n\taddr=" + address +
				"\n\tdensity=" + density;
	}
	
	public double getDensity() {
		return density;
	}

	public String getAddress() {
		return address;
	}

	public void setDensity(int in) {
		density = 1;
	}
}
