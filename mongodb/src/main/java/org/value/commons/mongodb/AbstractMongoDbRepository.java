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
import org.jvalue.commons.utils.Log;

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
		return findDocumentByFilter(createIdFilter(Id));
	}

	protected abstract D createNewDocument(Document document);

	protected D findDocumentByFilter(Bson filter){
		Document document = database.getCollection(collectionName).find(filter).first();
		if (document == null) {
			throw new GenericDocumentNotFoundException();
		}

		D newDocument = createNewDocument(document);

		return newDocument;
	}

//	private V entityFromDocument(Document document) {
//		V entity = null;
//		try {
//			String s = document.toJson();
//			JsonNode node = mapper.readTree(s);
//			JsonNode object = node.get("value");
//			entity = mapper.readValue(object.toString(), entityType);
//		} catch (IOException e) {
//			Log.info("Could not deserialize json:" + document.toJson());
//		}
//		return entity;
//	}

	@Override
	public void add(D Value) {
		try {
			database.getCollection(collectionName).insertOne(Value);
		} catch (Exception e) {
			Log.info(e.getMessage());
		}
	}

	@Override
	public void update(D value) {
		try {
			database.getCollection(collectionName).replaceOne(createIdFilter(value.getId()), value);
		} catch (Exception e) {
			Log.info(e.getMessage());
		}
	}


	@Override
	public void remove(D Value) {
		database.getCollection(collectionName).deleteOne(createIdFilter(Value.getId()));
	}


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
