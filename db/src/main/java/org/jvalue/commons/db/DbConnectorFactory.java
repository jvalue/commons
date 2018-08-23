package org.jvalue.commons.db;


/**
 * Responsible for creating database instances and prefixing the DB names.
 */
public abstract class DbConnectorFactory<T, C> {

	protected final T dbInstance;
	protected final String dbPrefix;


	public DbConnectorFactory(T dbInstance, String dbPrefix) {
		this.dbInstance = dbInstance;
		this.dbPrefix = dbPrefix;
	}


	public C createConnector(String databaseName, boolean createIfNotExists) {
		return doCreateConnector(databaseName, createIfNotExists);
	}


	public abstract C doCreateConnector(String databaseName, boolean createIfNotExists);


	public void deleteDatabase(String databaseName) {
		doDeleteDatabase(databaseName);
	}


	public abstract void doDeleteDatabase(String databaseName);
}
