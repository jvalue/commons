package org.jvalue.commons.db.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import org.jvalue.commons.EntityBase;
import org.jvalue.commons.db.GenericDocumentOperationResult;
import org.jvalue.commons.db.data.Data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GenericDataRepository<V extends EntityBase, R> {
	R findByDomainId(String domainId);

	List<JsonNode> executeQuery(V view, String param);

	void addQuery(V view);

	void removeQuery(V view);

	boolean containsQuery(V view);

	Map<String, R> getBulk(Collection<String> ids);

	Collection<GenericDocumentOperationResult> writeBulk(Collection<R> data);

	Data getPaginatedData(String startDomainId, int count);

	void removeAll();

	void compact();
}
