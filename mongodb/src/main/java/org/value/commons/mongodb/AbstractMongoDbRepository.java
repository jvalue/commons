package org.value.commons.mongodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jongo.Jongo;
import org.jongo.MongoCursor;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.GenericDocumentNotFoundException;
import org.jvalue.commons.db.repositories.GenericRepository;

import java.util.LinkedList;
import java.util.List;


public abstract class AbstractMongoDbRepository<T> implements GenericRepository<T> {

	private final Class<T> documentType;
	protected String collectionName;
	protected Jongo jongo;
	protected static final ObjectMapper mapper = new ObjectMapper();


	protected AbstractMongoDbRepository(DbConnectorFactory connectorFactory, String databaseName, String collectionName, Class<T> type) {
		this.jongo = (Jongo) connectorFactory.createConnector(databaseName, true);
		this.collectionName = collectionName;
		this.documentType = type;
	}


	protected String createIdFilter(String Id) {
		return "{ id : '" + Id + "' }";
	}


	@Override
	public T findById(String Id) {
		return findDocumentById(Id);
	}


	private T findDocumentById(String Id) {
		return findDocumentByFilter(createIdFilter(Id), Id);
	}


	protected T findDocumentByFilter(String filter, String value) {
		long count = jongo.getCollection(collectionName).count(filter);
		if (count > 1) {
			throw new IllegalStateException("More than 1 documents have the same Id:" + value);
		}
		T document = jongo.getCollection(collectionName).findOne(filter).as(documentType);
		if (document == null) {
			throw new GenericDocumentNotFoundException();
		}

		return document;
	}


	@Override
	public void add(T Value) {
		addOrUpdate(Value);
	}


	private void addOrUpdate(T Value) {
		String filter = createIdFilter(getValueId(Value));
		long count = jongo.getCollection(collectionName).count(filter);
		if (count == 0) {
			//add new
			jongo.getCollection(collectionName).insert(Value);
		} else if (count == 1) {
			// already exist -> update
			jongo.getCollection(collectionName).update(filter).with(Value);
		}
	}


	@Override
	public void update(T value) {
		addOrUpdate(value);
	}


	@Override
	public void remove(T Value) {
		jongo.getCollection(collectionName).remove(createIdFilter(getValueId(Value)));
	}


	protected abstract String getValueId(T Value);


	@Override
	public List<T> getAll() {
		MongoCursor<T> documents = jongo.getCollection(collectionName).find().as(documentType);
		List<T> list = new LinkedList<>();

		for (T doc : documents) {
			list.add(doc);
		}

		return list;
	}
}
