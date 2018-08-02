package org.jvalue.commons.auth.mongodb;

import org.junit.Ignore;
import org.jvalue.commons.auth.BasicCredentials;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.repositories.GenericRepository;
import org.jvalue.commons.mongodb.test.AbstractRepositoryAdapterTest;

@Ignore
public class MongoDbBasicCredentialsRepositoryTest extends AbstractRepositoryAdapterTest<BasicCredentials> {
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
