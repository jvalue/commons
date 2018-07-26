package org.jvalue.commons.auth;

import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.GenericRepository;

import javax.inject.Inject;

public class CouchDbUserRepositoryFactory implements UserRepositoryFactory {
	private final DbConnectorFactory dbConnectorFactory;


	@Inject
	public CouchDbUserRepositoryFactory(DbConnectorFactory dbConnectorFactory){
		this.dbConnectorFactory = dbConnectorFactory;
	}

	@Override
	public GenericRepository<User> createUserRepository() {
		return new UserRepository(dbConnectorFactory);
	}
}
