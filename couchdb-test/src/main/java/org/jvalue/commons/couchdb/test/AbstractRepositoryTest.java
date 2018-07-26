package org.jvalue.commons.couchdb.test;


import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.DbAccessException;
import org.junit.After;
import org.junit.Before;
import org.jvalue.commons.db.DbConnectorFactory;

import java.util.List;

public abstract class AbstractRepositoryTest {

	private static final String DB_PREFIX = "test";

	private CouchDbInstance couchDbInstance;

	private class CouchDbConnectorFactory extends DbConnectorFactory<CouchDbInstance, CouchDbConnector> {

		public CouchDbConnectorFactory(CouchDbInstance couchDbInstance, String dbPrefix) {
			super(couchDbInstance, dbPrefix);
		}


		@Override
		public CouchDbConnector doCreateConnector(String databaseName, boolean createIfNotExists) throws DbAccessException {
			return dbInstance.createConnector(dbPrefix + "-" + databaseName, createIfNotExists);
		}


		@Override
		public void doDeleteDatabase(String databaseName) {
			dbInstance.deleteDatabase(dbPrefix + "-" + databaseName);
		}
	}


	@Before
	public final void createDatabase() {
		this.couchDbInstance = DbFactory.createCouchDbInstance();
		DbConnectorFactory dbConnectorFactory = new CouchDbConnectorFactory(couchDbInstance, DB_PREFIX);
		doCreateDatabase(dbConnectorFactory);
	}


	protected abstract void doCreateDatabase(DbConnectorFactory dbConnectorFactory);


	@After
	public final void deleteDatabase() {
		List<String> databaseNames = couchDbInstance.getAllDatabases();
		for (String name : databaseNames) {
			if (name.startsWith(DB_PREFIX)) couchDbInstance.deleteDatabase(name);
		}
	}


}
