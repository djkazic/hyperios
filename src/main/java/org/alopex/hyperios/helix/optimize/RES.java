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
	private int count; // Optimized flag, randomize
	
	public RES() {
		AggregateIterable<Document> iterable = DB.getDatabase().getCollection("res").aggregate(Arrays.asList(new Document("$sample", new Document("size", 1))));
		iterable.forEach(new Block<Document> () {
			public void apply(final Document document) {
				name = document.getString("name");
				cost = Double.parseDouble(document.getString("cost"));
				radius = Double.parseDouble(document.getString("radius"));
				count = (int) (Math.random() * 10) + 1;
			}
		});
	}
	
	public String toString() {
		return "\n\tname=" + name +
				"\n\tcost=" + cost +
				"\n\tradius=" + radius +
				"\n\tcount=" + count;
	}
	
	public double getCost() {
		return cost;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public int getCount() {
		return count;
	}
}
