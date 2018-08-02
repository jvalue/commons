package org.jvalue.commons.auth.mongodb;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jvalue.commons.auth.User;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.repositories.GenericUserRepository;
import org.value.commons.mongodb.AbstractMongoDbRepository;
import org.value.commons.mongodb.MongoDbDocument;
import org.value.commons.mongodb.MongoDbDocumentAdaptable;
import org.value.commons.mongodb.MongoDbRepositoryAdapter;

public class MongoDbUserRepository extends MongoDbRepositoryAdapter<
	MongoDbUserRepository.MongoDbUserRepositoryImpl,
	MongoDbUserRepository.MongoDbUserDocument,
	User>
	implements GenericUserRepository<User> {

	static final String DATABASE_NAME = "users";
	static final String COLLECTION_NAME = "authCollection";


	public MongoDbUserRepository(DbConnectorFactory connectorFactory) {
		super(new MongoDbUserRepositoryImpl(connectorFactory, DATABASE_NAME, COLLECTION_NAME));
	}


	@Override
	public User findByEmail(String email) {
		return repository.findByEmail(email).getValue();
	}


	static class MongoDbUserRepositoryImpl extends AbstractMongoDbRepository<MongoDbUserDocument> implements MongoDbDocumentAdaptable<MongoDbUserDocument, User> {

		protected MongoDbUserRepositoryImpl(DbConnectorFactory connectorFactory, String databaseName, String collectionName) {
			super(connectorFactory, databaseName, collectionName, MongoDbUserDocument.class);
		}


		@Override
		public MongoDbUserDocument createDbDocument(User value) {
			return new MongoDbUserDocument(value);
		}


		@Override
		public String getIdForValue(User value) {
			return value.getId();
		}


		public MongoDbUserDocument findByEmail(String email) {
			return doFindByEmail(email);
		}


		private Bson createByEmailFilter(String email) {
			return Filters.eq("value.email", email);
		}


		private MongoDbUserDocument doFindByEmail(String email) {
			return findDocumentByFilter(createByEmailFilter(email), email);
		}


		@Override
		protected MongoDbUserDocument createNewDocument(Document document) {
			return new MongoDbUserDocument(document);
		}


		@Override
		protected String getValueId(MongoDbUserDocument Value) {
			return Value.getValue().getId();
		}
	}

	static class MongoDbUserDocument extends MongoDbDocument<User> {
		public MongoDbUserDocument(User valueObject) {
			super(valueObject, User.class);
		}
		public MongoDbUserDocument(Document document) {
			super(document, User.class);
		}

	}
}
