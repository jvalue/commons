package org.value.commons.mongodb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jvalue.commons.Credentials;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public final class MongoDbConfig {

	@NotNull private final String url;
	@NotNull private final String dbPrefix;
	@NotNull @Valid private final Credentials admin;
	@Min(1) private final int maxConnections;

	@JsonCreator
	public MongoDbConfig(
			@JsonProperty("url") String url,
			@JsonProperty("dbPrefix") String dbPrefix,
			@JsonProperty("admin") Credentials admin,
			@JsonProperty("maxConnections") int maxConnections) {

		this.url = url;
		this.dbPrefix = dbPrefix;
		this.admin = admin;
		this.maxConnections = maxConnections;
	}


	public String getUrl() {
		return url;
	}


	public String getDbPrefix() {
		return dbPrefix;
	}


	public Credentials getAdmin() {
		return admin;
	}


	public int getMaxConnections() {
		return maxConnections;
	}

}
