package org.jvalue.commons;import org.jvalue.commons.Credentials;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

/**
 * General pair of username + password.
 */
public class Credentials {

	@NotNull
	private final String username, password;

	@JsonCreator
	public Credentials(
			@JsonProperty("username") String username,
			@JsonProperty("password") String password) {

		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Credentials)) return false;
		Credentials credentials = (Credentials) other;
		return Objects.equal(username, credentials.username)
				&& Objects.equal(password, credentials.password);
	}


	@Override
	public int hashCode() {
		return Objects.hashCode(username, password);
	}

}
