package org.jvalue.commons.auth.mongodb;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jvalue.commons.auth.BasicCredentials;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.repositories.GenericRepository;
import org.value.commons.mongodb.AbstractMongoDbRepository;
import org.value.commons.mongodb.MongoDbDocument;
import org.value.commons.mongodb.MongoDbDocumentAdaptable;
import org.value.commons.mongodb.MongoDbRepositoryAdapter;

import javax.print.Doc;

public class MongoDbBasicCredentialsRepository extends MongoDbRepositoryAdapter
	<MongoDbBasicCredentialsRepository.MongoDbBasicCredentialsRepositoryImpl,
		MongoDbBasicCredentialsRepository.MongoDbBasicCredentialsDocument,
		BasicCredentials> implements GenericRepository<BasicCredentials> {

	public MongoDbBasicCredentialsRepository(DbConnectorFactory connectorFactory) {
		super(new MongoDbBasicCredentialsRepositoryImpl(connectorFactory, MongoDbUserRepository.DATABASE_NAME, MongoDbUserRepository.COLLECTION_NAME));
	}


	static class MongoDbBasicCredentialsRepositoryImpl extends AbstractMongoDbRepository<MongoDbBasicCredentialsDocument> implements MongoDbDocumentAdaptable<MongoDbBasicCredentialsDocument, BasicCredentials> {

		protected MongoDbBasicCredentialsRepositoryImpl(DbConnectorFactory connectorFactory, String databaseName, String collectionName) {
			super(connectorFactory, databaseName, collectionName, MongoDbBasicCredentialsDocument.class);
		}


		@Override
		protected Bson createIdFilter(String Id) {
			return Filters.eq("value.userId", Id);
		}


		@Override
		protected MongoDbBasicCredentialsDocument createNewDocument(Document document) {
			return new MongoDbBasicCredentialsDocument(document);
		}


		@Override
		public MongoDbBasicCredentialsDocument createDbDocument(BasicCredentials value) {
			return new MongoDbBasicCredentialsDocument(value);
		}


		@Override
		public String getIdForValue(BasicCredentials value) {
			return value.getId();
		}

	}

	static class MongoDbBasicCredentialsDocument extends MongoDbDocument<BasicCredentials> {
		public MongoDbBasicCredentialsDocument(BasicCredentials valueObject) {
			super(valueObject, BasicCredentials.class);
		}


		public MongoDbBasicCredentialsDocument(Document document) {
			super(document, BasicCredentials.class);
		}
	}
}
