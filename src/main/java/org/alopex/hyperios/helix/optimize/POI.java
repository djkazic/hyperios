package org.alopex.hyperios.helix.optimize;

import java.util.Arrays;

import org.alopex.hyperios.db.DB;
import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;

public class POI {
	private String type;
	private String address;
	private int density;
	
	public POI() {
		AggregateIterable<Document> iterable = DB.getDatabase().getCollection("poi").aggregate(Arrays.asList(new Document("$sample", new Document("size", 1))));
		iterable.forEach(new Block<Document> () {
			public void apply(final Document document) {
				type = document.getString("type");
				address = document.getString("address");
				density = Integer.parseInt(document.getString("density"));
			}
		});
	}
	
	public String toString() {
		return "\n\ttype=" + type +
				"\n\taddr=" + address +
				"\n\tdensity=" + density;
	}
}
