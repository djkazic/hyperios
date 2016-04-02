package org.alopex.hyperios.db;

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
		mongo = getMongoClient();
		
		if (database == null) {
			database = mongo.getDatabase("hyperios");
		}
		return database;
	}

}
