package org.jvalue.commons.auth;

import org.jvalue.commons.auth.couchdb.UserRepository;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.repositories.GenericRepository;

import javax.inject.Inject;

public class CouchDbUserRepositoryFactory implements UserRepositoryFactory {
	private final DbConnectorFactory dbConnectorFactory;


	@Inject
	public CouchDbUserRepositoryFactory(DbConnectorFactory dbConnectorFactory){
		this.dbConnectorFactory = dbConnectorFactory;
	}

	@Override
	public GenericUserRepository<User> createUserRepository() {
		return new UserRepository(dbConnectorFactory);
	}
}
