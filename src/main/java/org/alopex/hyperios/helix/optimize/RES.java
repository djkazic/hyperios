package org.alopex.hyperios.helix.optimize;

import java.util.Arrays;

import org.alopex.hyperios.db.DB;
import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;

public class RES {
	private String name;
	private double cost;
	private double radius;
	private int count; // Optimized flag, do NOT set
	
	public RES() {
		AggregateIterable<Document> iterable = DB.getDatabase().getCollection("res").aggregate(Arrays.asList(new Document("$sample", new Document("size", 1))));
		iterable.forEach(new Block<Document> () {
			public void apply(final Document document) {
				name = document.getString("name");
				cost = Double.parseDouble(document.getString("address"));
				radius = Double.parseDouble(document.getString("density"));
			}
		});
		// Default count is 0
		count = 0;
	}
	
	public String toString() {
		return "\n\tname=" + name +
				"\n\tcost=" + cost +
				"\n\tradius=" + radius +
				"\n\tcount=" + count;
	}
}
