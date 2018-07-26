package org.jvalue.commons.db.repositories;

import org.jvalue.ods.api.processors.PluginMetaData;

import java.io.InputStream;

public interface GenericPluginMetaDataRepository<T> extends GenericRepository<T> {
	void addAttachment(PluginMetaData metaData, InputStream stream, String contentType);
	InputStream getAttachment(PluginMetaData metaData);
}
