package org.jvalue.commons.auth.mongodb;

import org.jvalue.commons.auth.BasicCredentials;
import org.jvalue.commons.db.DbConnectorFactory;
import org.value.commons.mongodb.AbstractMongoDbRepository;


public class MongoDbBasicCredentialsRepository extends AbstractMongoDbRepository<BasicCredentials> {

	public MongoDbBasicCredentialsRepository(DbConnectorFactory connectorFactory) {
		super(connectorFactory, MongoDbUserRepository.DATABASE_NAME, MongoDbUserRepository.COLLECTION_NAME, BasicCredentials.class);
	}


	@Override
	protected String createIdFilter(String Id) {
		return " { userId: '" + Id + "'}";
	}


	@Override
	protected String getValueId(BasicCredentials Value) {
		return Value.getUserId();
	}
}
