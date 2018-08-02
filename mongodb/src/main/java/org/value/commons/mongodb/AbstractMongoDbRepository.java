package org.value.commons.mongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.GenericDocumentNotFoundException;
import org.jvalue.commons.db.repositories.GenericRepository;

import java.util.LinkedList;
import java.util.List;


public abstract class AbstractMongoDbRepository<D extends MongoDbDocument> implements GenericRepository<D> {

	private final Class<D> documentType;
	protected String collectionName;
	protected MongoDatabase database;
	protected static final ObjectMapper mapper = new ObjectMapper();

	protected AbstractMongoDbRepository(DbConnectorFactory connectorFactory, String databaseName, String collectionName, Class<D> type) {
		this.database = (MongoDatabase) connectorFactory.createConnector(databaseName, true);
		this.collectionName = collectionName;
		this.documentType = type;
	}

	protected Bson createIdFilter(String Id){
		return Filters.eq("value.id", Id);
	}

	@Override
	public D findById(String Id) {
		return findDocumentById(Id);
	}

	private D findDocumentById(String Id){
		return findDocumentByFilter(createIdFilter(Id), Id);
	}

	protected abstract D createNewDocument(Document document);

	protected D findDocumentByFilter(Bson filter, String value){
		long count = database.getCollection(collectionName).countDocuments(filter);
		if(count > 1) {
			throw new IllegalStateException("More than 1 documents have the same Id:" + value);
		}
		Document document = database.getCollection(collectionName).find(filter).first();
		if (document == null) {
			throw new GenericDocumentNotFoundException();
		}

		D newDocument = createNewDocument(document);

		return newDocument;
	}

	@Override
	public void add(D Value) {
		addOrUpdate(Value);
	}

	private void addOrUpdate(D Value){
		Bson filter = createIdFilter(getValueId(Value));
		long count = database.getCollection(collectionName).countDocuments(filter);
		if(count == 0){
			//add new
			database.getCollection(collectionName).insertOne(Value);
		}else if(count == 1){
			// already exist -> update
			database.getCollection(collectionName).replaceOne(filter, Value);
		}
	}

	@Override
	public void update(D value) {
		addOrUpdate(value);
	}


	@Override
	public void remove(D Value) {
		database.getCollection(collectionName).deleteOne(createIdFilter(getValueId(Value)));
	}

	protected abstract String getValueId(D Value);

	@Override
	public List<D> getAll() {
		FindIterable<Document> documents = database.getCollection(collectionName).find();
		List<D> list = new LinkedList<>();

		for(Document doc : documents){
			list.add(createNewDocument(doc));
		}

		return list;
	}
}
