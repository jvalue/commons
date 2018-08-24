package org.jvalue.commons.auth.mongodb;

import org.junit.BeforeClass;
import org.jvalue.commons.auth.Role;
import org.jvalue.commons.auth.User;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.repositories.GenericRepository;
import org.jvalue.commons.mongodb.test.AbstractRepositoryTestBase;
import org.jvalue.commons.utils.HttpServiceCheck;

public class MongoDbUserRepositoryTest extends AbstractRepositoryTestBase<User> {

	@BeforeClass
	public static void setup() {
		HttpServiceCheck.check(HttpServiceCheck.MONGODB_URL);
	}


	@Override
	protected GenericRepository<User> doCreateRepository(DbConnectorFactory connectorFactory) {
		return new MongoDbUserRepository(connectorFactory);
	}


	@Override
	protected User doCreateValue(String id, String data) {
		return new User(id, data, "someMail", Role.PUBLIC);
	}


	@Override
	protected void doDeleteDatabase(DbConnectorFactory dbConnectorFactory) {
		dbConnectorFactory.doDeleteDatabase(MongoDbUserRepository.DATABASE_NAME);
	}
}
