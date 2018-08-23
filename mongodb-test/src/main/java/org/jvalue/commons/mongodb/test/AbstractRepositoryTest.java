package org.jvalue.commons.mongodb.test;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.jongo.Jongo;
import org.junit.After;
import org.junit.Before;
import org.jvalue.commons.db.DbConnectorFactory;

public abstract class AbstractRepositoryTest {

	private static final String DB_PREFIX = "test";

	private MongoClient mongoDatabase;

	DbConnectorFactory dbConnectorFactory;

	private class MongoDbConnectorFactory extends DbConnectorFactory<MongoClient, Jongo> {

		public MongoDbConnectorFactory(MongoClient mongoClient, String dbPrefix) {
			super(mongoClient, dbPrefix);
		}


		@Override
		public Jongo doCreateConnector(String databaseName, boolean createIfNotExists) {
			DB db = dbInstance.getDB(dbPrefix + "-" + databaseName);
			return new Jongo(db);
		}


		@Override
		public void doDeleteDatabase(String databaseName) {
			dbInstance.dropDatabase(dbPrefix + "-" + databaseName);
		}
	}


	@Before
	public final void createDatabase() {
		this.mongoDatabase = DbFactory.createMongoClient();
		dbConnectorFactory = new MongoDbConnectorFactory(mongoDatabase, DB_PREFIX);
		doCreateDatabase(dbConnectorFactory);
	}


	protected abstract void doCreateDatabase(DbConnectorFactory dbConnectorFactory);


	@After
	public final void deleteDatabase() {
		doDeleteDatabase(dbConnectorFactory);
	}


	protected abstract void doDeleteDatabase(DbConnectorFactory dbConnectorFactory);
}
