package org.jvalue.commons.auth.mongodb;

import org.bson.Document;
import org.jvalue.commons.auth.User;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.repositories.GenericUserRepository;
import org.value.commons.mongodb.AbstractMongoDbRepository;
import org.value.commons.mongodb.MongoDocumentNotFoundException;

import static com.mongodb.client.model.Filters.eq;

public class MongoDbUserRepository extends AbstractMongoDbRepository<User> implements GenericUserRepository<User> {

	static final String DATABASE_NAME = "dataSources";
	private static final String COLLECTION_NAME = "dataSourceCollection";


	public MongoDbUserRepository(DbConnectorFactory connectorFactory) {
		super(connectorFactory, DATABASE_NAME, COLLECTION_NAME, User.class);
	}


	private Document doFindByEmail(String email) {
		//should only return one
		Document document = collection.find(eq("email", email)).first();
		if (document == null) {
			throw new MongoDocumentNotFoundException();
		}
		return document;
	}


	@Override
	public User findByEmail(String email) {
		//should only return one
		Document document = doFindByEmail(email);
		return deserializeDocument(document);
	}
}
