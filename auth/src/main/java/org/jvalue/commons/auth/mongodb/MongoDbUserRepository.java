package org.jvalue.commons.auth.mongodb;

import org.jvalue.commons.auth.User;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.repositories.GenericUserRepository;
import org.value.commons.mongodb.AbstractMongoDbRepository;

public class MongoDbUserRepository extends AbstractMongoDbRepository<User> implements GenericUserRepository {

	static final String DATABASE_NAME = "users";
	static final String COLLECTION_NAME = "authCollection";


	public MongoDbUserRepository(DbConnectorFactory connectorFactory) {
		super(connectorFactory, DATABASE_NAME, COLLECTION_NAME, User.class);
	}


	@Override
	public User findByEmail(String email) {
		return doFindByEmail(email);
	}


	private String createByEmailFilter(String email) {
		return " { email : '" + email + "'}";
	}


	private User doFindByEmail(String email) {
		return findDocumentByFilter(createByEmailFilter(email), email);
	}


	@Override
	protected String getValueId(User Value) {
		return Value.getId();
	}
}
