package org.jvalue.commons.couchdb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jvalue.commons.Credentials;
import org.jvalue.commons.db.DbConfig;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public final class CouchDbConfig extends DbConfig {
	@JsonCreator
	public CouchDbConfig(
			@JsonProperty("url") String url,
			@JsonProperty("dbPrefix") String dbPrefix,
			@JsonProperty("admin") Credentials admin,
			@JsonProperty("maxConnections") int maxConnections) {
		super(url, dbPrefix, admin, maxConnections);
	}
}
