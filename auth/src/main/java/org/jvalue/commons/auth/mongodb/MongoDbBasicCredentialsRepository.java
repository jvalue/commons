package org.jvalue.commons.auth.mongodb;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jvalue.commons.auth.BasicCredentials;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.GenericDocumentNotFoundException;
import org.value.commons.mongodb.AbstractMongoDbRepository;

import static com.mongodb.client.model.Filters.eq;

public class MongoDbBasicCredentialsRepository extends AbstractMongoDbRepository<BasicCredentials> {

	public MongoDbBasicCredentialsRepository(DbConnectorFactory connectorFactory) {
		super(connectorFactory, MongoDbUserRepository.DATABASE_NAME, MongoDbUserRepository.COLLECTION_NAME, BasicCredentials.class);
	}

	@Override
	public BasicCredentials findById(String Id) {
		//should only return one
		Document document = findCredentialDocumentById(Id);
		return deserializeDocument(document);
	}

	private Document findCredentialDocumentById(String Id){
		//search for credentials object with user Id
		Bson filter = Filters.and(Filters.eq("value.userId", Id), Filters.exists("value.encryptedPassword"));
		Document document = database.getCollection(collectionName).find(filter).first();
		if (document == null) {
			throw new GenericDocumentNotFoundException();
		}
		return document;
	}
}
