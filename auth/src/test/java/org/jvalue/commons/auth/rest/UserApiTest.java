package org.jvalue.commons.auth.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvalue.commons.auth.Role;
import org.jvalue.commons.auth.UnauthorizedException;
import org.jvalue.commons.auth.User;
import org.jvalue.commons.auth.UserDescription;
import org.jvalue.commons.auth.UserManager;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;


@RunWith(JMockit.class)
public final class UserApiTest {

	@Mocked private UserManager userManager;
	private UserApi userApi;


	@Before
	public void setupApi() {
		userApi = new UserApi(userManager);
	}


	public void testAddUserFailure() {
		userApi.getAllUsers(null);
		new Expectations() {{
			userManager.getAll();
		}};
	}


	@Test(expected = UnauthorizedException.class)
	public void testAddUserSuccess() {
		testAddUser(null);
	}


	public void testAddAdminUser() {
		testAddUser(new User("", "", "", Role.ADMIN));
		new Verifications() {{
			userManager.add((UserDescription) any);
		}};
	}


	private void testAddUser(User user) {
		UserDescription description = new UserDescription("", "", Role.ADMIN, "");
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