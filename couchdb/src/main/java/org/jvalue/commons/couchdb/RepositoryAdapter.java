package org.jvalue.commons.couchdb;


import org.ektorp.support.CouchDbRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter for converting between wrapper and plain DB objects.
 * @param <T> type of the repository
 * @param <D> type of the wrapper
 * @param <V> type of the wrapper object
 */
public abstract class RepositoryAdapter<T extends CouchDbRepositorySupport<D> & DbDocumentAdaptable<D, V>, D extends DbDocument<V>, V> {

	protected final T repository;

	public RepositoryAdapter(T repository) {
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
		return repository.getAll().stream()
			.map(d -> d.getValue())
			.collect(Collectors.toList());
	}

}
