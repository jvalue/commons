package org.jvalue.commons.db.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jvalue.commons.EntityBase;

import java.util.List;

public interface GenericRepository<V extends EntityBase> {
	V findById(String Id);
	void add(V Value);
	void update(V value);
	void remove(V Value);
	List<V> getAll();
}
