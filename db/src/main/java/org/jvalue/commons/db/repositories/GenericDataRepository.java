package org.jvalue.commons.db.repositories;

import org.jvalue.commons.db.GenericDocumentOperationResult;
import org.jvalue.commons.db.data.Data;

import java.util.Collection;
import java.util.Map;

public interface GenericDataRepository<R> {
	R findByDomainId(String domainId);

	Map<String, R> getBulk(Collection<String> ids);

	Collection<GenericDocumentOperationResult> writeBulk(Collection<R> data);

	Data getPaginatedData(String startDomainId, int count);

	Data getAllDocuments();

	void removeAll();

	void compact();
}
