package org.jvalue.commons.auth;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

/**
 * A registered user.
 */
public final class User extends AbstractUser {

	@NotNull private final String id;

	@JsonCreator
	public User(
			@JsonProperty("id") String id,
			@JsonProperty("name") String name,
			@JsonProperty("email") String email,
			@JsonProperty("role") Role role) {

		super(name, email, role);
		this.id = id;
	}


	public String getId() {
		return id;
	}


	@Override
	public boolean equals(Object other) {
		if (!super.equals(other) || !(other instanceof User)) return false;
		User user = (User) other;
		return Objects.equal(id, user.id);
	}


	@Override
	public int hashCode() {
		return Objects.hashCode(super.hashCode(), id);
	}

}
