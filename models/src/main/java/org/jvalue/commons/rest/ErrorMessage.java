package org.jvalue.commons.rest;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

public final class ErrorMessage {

	@NotNull private final int code;
	@NotNull private final String message;

	@JsonCreator
	public ErrorMessage(
			@JsonProperty("code") int code,
			@JsonProperty("message") String message) {

		this.code = code;
		this.message = message;
	}


	public int getCode() {
		return code;
	}


	public String getMessage() {
		return message;
	}


	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ErrorMessage)) return false;
		ErrorMessage error = (ErrorMessage) other;
		return Objects.equal(code, error.code)
				&& Objects.equal(message, error.message);
	}


	@Override
	public int hashCode() {
		return Objects.hashCode(code, message);
	}

}
