package org.value.commons.mongodb;


import java.util.LinkedList;
import java.util.List;

/**
 * Adapter for converting between wrapper and plain DB objects.
 *
 * @param <T> type of the repository
 * @param <D> type of the wrapper
 * @param <V> type of the wrapper object
 */
public abstract class MongoDbRepositoryAdapter<T extends AbstractMongoDbRepository<D> & MongoDbDocumentAdaptable<D, V>, D extends MongoDbDocument<V>, V> {

	protected final T repository;


	public MongoDbRepositoryAdapter(T repository) {
		this.repository = repository;
	}


	public V findById(String id) {
		return repository.findById(id).getValue();
	}


	public void add(V value) {
		repository.add(repository.createDbDocument(value));
	}


	public void update(V value) {
		D document = repository.findById(repository.getIdForValue(value));
		document.setValue(value);
		repository.update(document);
	}


	public void remove(V value) {
		D document = repository.findById(repository.getIdForValue(value));
		repository.remove(document);
	}


	public List<V> getAll() {
		List<V> values = new LinkedList<>();
		for (D document : repository.getAll()) {
			values.add(document.getValue());
		}
		return values;
	}

}
