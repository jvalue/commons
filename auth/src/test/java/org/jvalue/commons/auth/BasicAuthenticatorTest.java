package org.jvalue.commons.auth;


import com.google.common.base.Optional;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvalue.commons.auth.couchdb.BasicCredentialsRepository;
import org.jvalue.commons.db.GenericDocumentNotFoundException;
import org.jvalue.commons.db.factories.AuthRepositoryFactory;

@RunWith(JMockit.class)
public class BasicAuthenticatorTest {

	private final String validAuthHeader = "Basic YWRtaW5NYWlsOmFkbWluUGFzcw==";
	private final User user = new User("adminId", "adminName", "adminMail", Role.PUBLIC);
	private final BasicCredentials credentials = new BasicCredentials("adminId", "admin".getBytes(), "salt".getBytes());

	private BasicAuthenticator authenticator;

	@Mocked
	private AuthRepositoryFactory authRepositoryFactory;


	@Mocked private UserManager userManager;
	@Mocked private BasicCredentialsRepository credentialsRepository;
	@Mocked private BasicAuthUtils authenticationUtils;


	@Before
	public void setup() {
		authenticator = new BasicAuthenticator(userManager, authRepositoryFactory, authenticationUtils);
	}


	@Test
	public void testValidUser() {
		setupMocks(user, credentials, true);
		Optional<User> optionalUser = authenticator.authenticate(validAuthHeader);
		Assert.assertEquals(user, optionalUser.get());
	}


	@Test
	public void testMissingUser() {
		testFailure(new GenericDocumentNotFoundException(user.getId()), credentials, true);
	}


	@Test
	public void testMissingCredentials() {
		testFailure(user, new GenericDocumentNotFoundException(credentials.getUserId()), true);
	}


	@Test
	public void testInvalidCredentials() {
		testFailure(user, credentials, false);
	}


	private void testFailure(final Object user, final Object credentials, final boolean isMatch) {
		setupMocks(user, credentials, isMatch);
		Optional<User> optionalUser = authenticator.authenticate(validAuthHeader);
		Assert.assertFalse(optionalUser.isPresent());
	}


	private void setupMocks(final Object userResult, final Object credentialsResult, final boolean isMatch) {
		new Expectations() {{
			userManager.findByEmail(user.getEmail()); result = userResult; minTimes = 0;
			credentialsRepository.findById(user.getId()); result = credentialsResult; minTimes = 0;
			authenticationUtils.checkPassword("adminPass", credentials); result = isMatch; minTimes = 0;
		}};
	}

}
