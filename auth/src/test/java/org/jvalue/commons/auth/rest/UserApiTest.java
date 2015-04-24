package org.jvalue.commons.auth.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvalue.commons.auth.BasicAuthUtils;
import org.jvalue.commons.auth.OAuthUtils;
import org.jvalue.commons.auth.Role;
import org.jvalue.commons.auth.UnauthorizedException;
import org.jvalue.commons.auth.User;
import org.jvalue.commons.auth.BasicAuthUserDescription;
import org.jvalue.commons.auth.UserManager;

import javax.ws.rs.WebApplicationException;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;


@RunWith(JMockit.class)
public final class UserApiTest {

	@Mocked private UserManager userManager;
	@Mocked private BasicAuthUtils basicAuthUtils;
	@Mocked private OAuthUtils oAuthUtils;
	private UserApi userApi;


	@Before
	public void setupApi() {
		userApi = new UserApi(userManager, basicAuthUtils, oAuthUtils);
	}


	public void testGetAllUsers() {
		userApi.getAllUsers(null);
		new Expectations() {{
			userManager.getAll();
		}};
	}


	@Test(expected = UnauthorizedException.class)
	public void testAddUserFailure() {
		testAddUser(null);
	}


	@Test
	public void testAddUserSuccess() {
		testAddUser(new User("", "", "", Role.ADMIN));
		new Verifications() {{
			userManager.add((BasicAuthUserDescription) any);
		}};
	}


	@Test(expected = WebApplicationException.class)
	public void testAddDuplicateUser() {
		final String mail = "someMail";
		new Expectations() {{
			userManager.contains(mail); result = true;
		}};

		userApi.addUser(null, new BasicAuthUserDescription("", mail, Role.PUBLIC, ""));
	}


	private void testAddUser(User user) {
		BasicAuthUserDescription description = new BasicAuthUserDescription("", "", Role.ADMIN, "");
		userApi.addUser(user, description);
	}


	@Test(expected = UnauthorizedException.class)
	public void testGetUserFailure() {
		userApi.getUser(new User("id1", "", "", Role.PUBLIC), "id2");
	}


	@Test
	public void testGetUserSuccess() {
		userApi.getUser(new User("id1", "", "", Role.ADMIN), "id2");
		new Verifications() {{
			userManager.findById("id2");
		}};
	}


	@Test(expected = UnauthorizedException.class)
	public void testRemoveUserFailure() {
		userApi.removeUser(new User("id1", "", "", Role.PUBLIC), "id2");
	}


	@Test
	public void testRemoveUserSuccess() {
		final User removeUser = new User("id2", "", "", Role.PUBLIC);
		new Expectations() {{
			userManager.findById(removeUser.getId()); result = removeUser;
		}};

		userApi.removeUser(new User("id1", "", "", Role.ADMIN), removeUser.getId());
		new Verifications() {{
			userManager.remove(removeUser);
		}};
	}

}