package org.jvalue.commons.db.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import org.jvalue.commons.db.GenericDocumentOperationResult;
import org.jvalue.commons.db.data.Data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GenericDataRepository<View, V> extends GenericRepository<V> {
	JsonNode findByDomainId(String domainId);

	List<JsonNode> executeQuery(View view, String param);

	void addQuery(View view);

	void removeQuery(View view);

	boolean containsQuery(View view);

	Map<String, JsonNode> getData(Collection<String> ids);

	Collection<GenericDocumentOperationResult> writeData(Collection<JsonNode> data);

	Data getPaginatedData(String startDomainId, int count);

	void removeAll();

	void compact();
}
