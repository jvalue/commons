package org.jvalue.commons.mongodb.test;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public final class DbFactory {

	private DbFactory() {
	}


	public static MongoClient createMongoClient() {
		return new MongoClient(new MongoClientURI("mongodb://localhost:27017/"));
	}
}
