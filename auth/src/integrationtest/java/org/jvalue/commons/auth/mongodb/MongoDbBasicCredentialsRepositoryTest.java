package org.jvalue.commons.auth.mongodb;

import org.junit.BeforeClass;
import org.jvalue.commons.auth.BasicCredentials;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.repositories.GenericRepository;
import org.jvalue.commons.mongodb.test.AbstractRepositoryTestBase;
import org.jvalue.commons.utils.HttpServiceCheck;

public class MongoDbBasicCredentialsRepositoryTest extends AbstractRepositoryTestBase<BasicCredentials> {

	@BeforeClass
	public static void setup() {
		HttpServiceCheck.check(HttpServiceCheck.MONGODB_URL);
	}


	@Override
	protected GenericRepository<BasicCredentials> doCreateRepository(DbConnectorFactory connectorFactory) {
		return new MongoDbBasicCredentialsRepository(connectorFactory);
	}


	@Override
	protected BasicCredentials doCreateValue(String id, String data) {
		return new BasicCredentials(id, data.getBytes(), data.getBytes());
	}


	@Override
	protected void doDeleteDatabase(DbConnectorFactory dbConnectorFactory) {
		dbConnectorFactory.doDeleteDatabase(MongoDbUserRepository.DATABASE_NAME);
	}
}
