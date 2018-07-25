package org.jvalue.commons.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GenericDocumentOperationResult implements Serializable {

	private String id;
	private String rev;
	private String error;
	private String reason;

	public static GenericDocumentOperationResult newInstance(String id, String error, String reason) {
		GenericDocumentOperationResult r = new GenericDocumentOperationResult();
		r.setId(id);
		r.setError(error);
		r.setReason(reason);
		return r;
	}

	@JsonProperty
	void setError(String error) {
		this.error = error;
	}

	@JsonProperty
	void setReason(String reason) {
		this.reason = reason;
	}

	public String getId() {
		return id;
	}

	@JsonProperty("id")
	void setId(String id) {
		this.id = id;
	}

	public String getRevision() {
		return rev;
	}
	@JsonProperty("rev")
	void setRev(String rev) {
		this.rev = rev;
	}

	public String getError() {
		return error;
	}

	public String getReason() {
		return reason;
	}

	public boolean isErroneous() {
		return error != null;
	}

	@Override
	public String toString() {
		return "DocumentOperationResult [id=" + id + ", rev=" + rev
			+ ", error=" + error + ", reason=" + reason + "]";
	}
}
