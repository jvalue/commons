package org.value.commons.mongodb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.jvalue.commons.EntityBase;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.GenericDocumentNotFoundException;
import org.jvalue.commons.db.repositories.GenericRepository;
import org.jvalue.commons.utils.Log;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public abstract class AbstractMongoDbRepository<T extends EntityBase> implements GenericRepository<T> {

	protected String collectionName;
	protected MongoDatabase database;
	protected static final ObjectMapper mapper = new ObjectMapper();
	private Class<T> type;

	protected AbstractMongoDbRepository(DbConnectorFactory connectorFactory, String databaseName, String collectionName, Class<T> type) {
		this.database = (MongoDatabase) connectorFactory.createConnector(databaseName, true);
		this.collectionName = collectionName;
		this.type = type;
	}


	@Override
	public T findById(String Id) {
		//should only return one
		Document document = findDocumentById(Id);
		return deserializeDocument(document);
	}

	protected Document findDocumentById(String Id){
		//should only return one
		Document document = database.getCollection(collectionName).find(eq("value.id", Id)).first();
		if (document == null) {
			throw new GenericDocumentNotFoundException();
		}
		return document;
	}


	protected T deserializeDocument(Document document) {
		//remove first id field
		T entity = null;
		try {
			String s = document.toJson();
			JsonNode node = mapper.readTree(s);
			JsonNode object = node.get("value");
			entity = mapper.readValue(object.toString(), type);
		} catch (IOException e) {
			Log.info("Could not deserialize json:" + document.toJson());
		}
		return entity;
	}


	@Override
	public void add(T Value) {
		String objectAsJsonString;

		try {
			objectAsJsonString = mapper.writeValueAsString(Value);
			Document parse = Document.parse(objectAsJsonString);
			Document objectWrapper = new Document();
			objectWrapper.append("value", parse);

			database.getCollection(collectionName).insertOne(objectWrapper);
		} catch (Exception e) {
			Log.info(e.getMessage());
		}
	}


	@Override
	public void update(T value) {
		String objectAsJsonString;

		try {
			objectAsJsonString = mapper.writeValueAsString(value);

			Document updated = Document.parse(objectAsJsonString);
			Document objectWrapper = new Document();
			objectWrapper.append("value", updated);

			Document searchObject = new Document();
			searchObject.put("value.id", value.getId());

			database.getCollection(collectionName).replaceOne(searchObject, objectWrapper);
		} catch (Exception e) {
			Log.info(e.getMessage());
		}
	}


	@Override
	public void remove(T Value) {
		database.getCollection(collectionName).deleteOne(Filters.eq("value.id", Value.getId()));
	}


	@Override
	public List<T> getAll() {
		FindIterable<Document> documents = database.getCollection(collectionName).find();
		List<T> entityList = new ArrayList<>();
		for (Document doc : documents) {
			T documentAsObject;
			documentAsObject = deserializeDocument(doc);
			entityList.add(documentAsObject);
		}
		return entityList;
	}
}
