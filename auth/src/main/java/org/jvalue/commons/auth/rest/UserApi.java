package org.jvalue.commons.auth.rest;


import org.jvalue.commons.auth.BasicAuthenticationUtils;
import org.jvalue.commons.auth.RestrictedTo;
import org.jvalue.commons.auth.Role;
import org.jvalue.commons.auth.UnauthorizedException;
import org.jvalue.commons.auth.User;
import org.jvalue.commons.auth.UserDescription;
import org.jvalue.commons.auth.UserManager;
import org.jvalue.commons.rest.RestUtils;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserApi {

	private final UserManager userManager;
	private final BasicAuthenticationUtils authenticationUtils;

	@Inject
	UserApi(UserManager userManager, BasicAuthenticationUtils authenticationUtils) {
		this.userManager = userManager;
		this.authenticationUtils = authenticationUtils;
	}


	@GET
	public List<User> getAllUsers(@RestrictedTo(Role.ADMIN) User user) {
		return userManager.getAll();
	}


	@POST
	public User addUser(@RestrictedTo(value = Role.ADMIN, isOptional = true) User user, UserDescription userDescription) {
		// check for valid role (only admins can add admins)
		if (userDescription.getRole().equals(Role.ADMIN) && user == null) throw new UnauthorizedException("missing admin privileges");

		// check if user already exists
		if (userManager.contains(userDescription.getEmail()))
			throw RestUtils.createJsonFormattedException("user already registered", 409);

		// check for partially secure password
		if (authenticationUtils.isPartiallySecurePassword(userDescription.getPassword()))
			throw RestUtils.createJsonFormattedException("password must be at least 8 characters and contain numbers", 409);

		return userManager.add(userDescription);
	}


	@GET
	@Path("/{userId}")
	public User getUser(@RestrictedTo(Role.PUBLIC) User user, @PathParam("userId") String userId) {
		if (!userId.equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) throw new UnauthorizedException();
		return userManager.findById(userId);
	}


	@DELETE
	@Path("/{userId}")
	public void removeUser(@RestrictedTo(Role.PUBLIC) User user, @PathParam("userId") String userId) {
		if (!userId.equals(user.getId()) && !user.getRole().equals(Role.ADMIN)) throw new UnauthorizedException();
		userManager.remove(userManager.findById(userId));
	}

}
