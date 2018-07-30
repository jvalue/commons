package org.jvalue.commons.auth.mongodb;

import org.jvalue.commons.auth.BasicCredentials;
import org.jvalue.commons.db.DbConnectorFactory;
import org.value.commons.mongodb.AbstractMongoDbRepository;

public class MongoDbBasicCredentialsRepository extends AbstractMongoDbRepository<BasicCredentials> {

	private static final String COLLECTION_NAME = "dataSourceCollection";

	public MongoDbBasicCredentialsRepository(DbConnectorFactory connectorFactory) {
		super(connectorFactory, MongoDbUserRepository.DATABASE_NAME, COLLECTION_NAME, BasicCredentials.class);
	}
}
