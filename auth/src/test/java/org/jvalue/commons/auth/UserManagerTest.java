package org.jvalue.commons.auth;

import org.ektorp.DocumentNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public final class UserManagerTest {

	@Mocked private UserRepository userRepository;
	@Mocked private BasicCredentialsRepository credentialsRepository;
	private final BasicAuthenticationUtils authenticationUtils = new BasicAuthenticationUtils();

	private UserManager userManager;

	@Before
	public void setupManager() {
		userManager = new UserManager(userRepository, credentialsRepository, authenticationUtils);
	}


	@Test
	public void testContains() throws Exception {
		final String mail1 = "someMail1";
		final String mail2 = "someMail2";

		new Expectations() {{
			userRepository.findByEmail(mail1); result = new User("", "", "", Role.ADMIN);
			userRepository.findByEmail(mail2); result = new DocumentNotFoundException("");
		}};

		Assert.assertTrue(userManager.contains(mail1));
		Assert.assertFalse(userManager.contains(mail2));
	}


	@Test
	public void testAdd() throws Exception {
		final UserDescription description = new UserDescription("someName", "someMail", Role.ADMIN, "somePass42");
		new Expectations() {{
			userRepository.findByEmail(description.getEmail()); result = new DocumentNotFoundException("");
		}};

		final User user = userManager.add(description);

		Assert.assertEquals(user.getName(), description.getName());
		Assert.assertEquals(user.getEmail(), description.getEmail());
		Assert.assertEquals(user.getRole(), description.getRole());

		new Verifications() {{
			userRepository.add(user);
			credentialsRepository.add((BasicCredentials) any);
		}};
	}

}