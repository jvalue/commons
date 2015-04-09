package org.jvalue.commons.auth;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

/**
 * A registered user.
 */
public final class UserDescription extends AbstractUser {

	@NotNull private final String password;

	@JsonCreator
	public UserDescription(
			@JsonProperty("name") String name,
			@JsonProperty("email") String email,
			@JsonProperty("role") Role role,
			@JsonProperty("password") String password) {

		super(name, email, role);
		this.password = password;
	}


	public String getPassword() {
		return password;
	}


	@Override
	public boolean equals(Object other) {
		if (!super.equals(other) || !(other instanceof UserDescription)) return false;
		UserDescription user = (UserDescription) other;
		return Objects.equal(password, user.password);
	}


	@Override
	public int hashCode() {
		return Objects.hashCode(super.hashCode(), password);
	}

}
