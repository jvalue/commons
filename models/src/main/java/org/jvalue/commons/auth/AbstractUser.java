package org.jvalue.commons.auth;


import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

abstract class AbstractUser {

	@NotNull private final String name;
	@NotNull private final String email;
	@NotNull private final Role role;

	public AbstractUser(
			String name,
			String email,
			Role role) {

		this.name = name;
		this.email = email;
		this.role = role;
	}


	public String getName() {
		return name;
	}


	public String getEmail() {
		return email;
	}


	public Role getRole() {
		return role;
	}


	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof AbstractUser)) return false;
		AbstractUser user = (AbstractUser) other;
		return Objects.equal(name, user.name)
				&& Objects.equal(email, user.email)
				&& Objects.equal(role, user.role);
	}


	@Override
	public int hashCode() {
		return Objects.hashCode(name, email, role);
	}

}
