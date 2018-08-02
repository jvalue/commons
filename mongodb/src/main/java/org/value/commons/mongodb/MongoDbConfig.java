package org.value.commons.mongodb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jvalue.commons.db.DbConfig;


public final class MongoDbConfig extends DbConfig {

	@JsonCreator
	public MongoDbConfig(
		@JsonProperty("url") String url,
		@JsonProperty("dbPrefix") String dbPrefix,
		@JsonProperty("maxConnections") int maxConnections) {
		super(url, dbPrefix, null, maxConnections);
	}
}
