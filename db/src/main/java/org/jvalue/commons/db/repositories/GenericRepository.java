package org.jvalue.commons.db.repositories;

import java.util.List;

public interface GenericRepository<V> {
	V findById(String Id);
	void add(V Value);
	void update(V value);
	void remove(V Value);
	List<V> getAll();
}
