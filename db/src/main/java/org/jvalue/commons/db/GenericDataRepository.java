package org.jvalue.commons.db;

import com.fasterxml.jackson.databind.JsonNode;
import org.jvalue.ods.api.data.Data;

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
