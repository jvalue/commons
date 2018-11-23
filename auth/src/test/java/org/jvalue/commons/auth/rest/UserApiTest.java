package org.jvalue.commons.auth.rest;

import com.google.common.base.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvalue.commons.auth.BasicAuthUserDescription;
import org.jvalue.commons.auth.BasicAuthUtils;
import org.jvalue.commons.auth.BasicAuthenticator;
import org.jvalue.commons.auth.OAuthUtils;
import org.jvalue.commons.auth.Role;
import org.jvalue.commons.auth.UnauthorizedException;
import org.jvalue.commons.auth.User;
import org.jvalue.commons.auth.UserManager;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;


@RunWith(JMockit.class)
public final class UserApiTest {

	@Mocked private UserManager userManager;
	@Mocked private BasicAuthenticator basicAuthenticator;
	@Mocked private BasicAuthUtils basicAuthUtils;
	@Mocked private OAuthUtils oAuthUtils;
	private UserApi userApi;


	@Before
	public void setupApi() {
		userApi = new UserApi(userManager, basicAuthenticator, basicAuthUtils, oAuthUtils);
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


	public void testAddDuplicateUser() {
		final String mail = "someMail";
		final String password = "password123";
		final BasicAuthUserDescription userDescription = new BasicAuthUserDescription("", mail, Role.PUBLIC, password);
		final User user = new User("", "", userDescription.getEmail(), Role.PUBLIC);

		new Expectations() {{
			basicAuthenticator.authenticate(mail, password);
			result = Optional.of(userDescription);
		}};

		Assert.assertEquals(user, userApi.addUser(null, userDescription));
	}


	private void testAddUser(User user) {
		new Expectations() {{
			basicAuthUtils.isPartiallySecurePassword(anyString);
			result = true;
			minTimes = 0;
		}};
		BasicAuthUserDescription description = new BasicAuthUserDescription("", "", Role.ADMIN, "password123");
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
