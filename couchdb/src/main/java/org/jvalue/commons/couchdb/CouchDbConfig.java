package org.jvalue.commons.couchdb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.jvalue.commons.auth.BasicCredentials;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public final class CouchDbConfig {

	@NotNull private final String url;
	@NotNull private final String dbPrefix;
	@NotNull @Valid BasicCredentials admin;

	@JsonCreator
	public CouchDbConfig(
			@JsonProperty("url") String url,
			@JsonProperty("dbPrefix") String dbPrefix,
			@JsonProperty("admin") BasicCredentials admin) {

		this.url = url;
		this.dbPrefix = dbPrefix;
		this.admin = admin;
	}


	public String getUrl() {
		return url;
	}


	public String getDbPrefix() {
		return dbPrefix;
	}


	public BasicCredentials getAdmin() {
		return admin;
	}

}