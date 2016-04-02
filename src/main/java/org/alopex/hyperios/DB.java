package org.alopex.hyperios;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DB {
	
	private static MongoClient mongo;
	private static MongoDatabase database;
	
	public static MongoClient getMongoClient() {
		if (mongo == null) {
			mongo = new MongoClient("localhost");
		}
		return mongo;
	}
	
	public static MongoDatabase getDatabase() {
		if (mongo == null) {
			mongo = new MongoClient("localhost");
		}
		
		if (database == null) {
			database = mongo.getDatabase("hyperios");
		}
		
		return database;
	}

}
