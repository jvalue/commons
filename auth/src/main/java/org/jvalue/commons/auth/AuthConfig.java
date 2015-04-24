package org.jvalue.commons.auth;


import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AuthConfig {

	@NotNull @Valid private final List<UserDescription> users;

	@JsonCreator
	public AuthConfig(List<UserDescription> users) {
		this.users = users;
	}


	public List<UserDescription> getUsers() {
		return users;
	}


}
