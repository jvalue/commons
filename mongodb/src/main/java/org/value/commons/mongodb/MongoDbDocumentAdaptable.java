package org.value.commons.mongodb;


import org.jvalue.commons.EntityBase;

/**
 * Describes a repository capable of dealing with wrapper
 * Java objects.
 * @param <D> type of the wrapper class
 * @param <V> type of the object contained within the wrapper
 */
public interface MongoDbDocumentAdaptable<D , V > {

	public D findById(String id);
	public D createDbDocument(V value);
	public String getIdForValue(V value);

}
