package org.jvalue.commons.auth;

import org.jvalue.commons.auth.couchdb.BasicCredentialsRepository;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.GenericRepository;

import javax.inject.Inject;

public class CouchDbBasicCredentialsRepositoryFactory implements BasicCredentialsRepositoryFactory {
	private final DbConnectorFactory dbConnectorFactory;


	@Inject
	public CouchDbBasicCredentialsRepositoryFactory(DbConnectorFactory dbConnectorFactory){
		this.dbConnectorFactory = dbConnectorFactory;
	}
	@Override
	public GenericRepository<BasicCredentials> createBasicCredentialRepository() {
		return new BasicCredentialsRepository(dbConnectorFactory);
	}
}
