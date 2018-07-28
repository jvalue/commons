package org.jvalue.commons.mongodb.test;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Running integration tests against CouchDB requires a admin account to be setup on that
 * CouchDB instance with "admin" "admin" credentials. This class provides easier access to
 * creating connector instances.
 */
public final class DbFactory {

	private DbFactory() { }


	public static MongoClient createMongoClient() {
		return new MongoClient(new MongoClientURI("mongodb://localhost:27017/"));
	}
}
