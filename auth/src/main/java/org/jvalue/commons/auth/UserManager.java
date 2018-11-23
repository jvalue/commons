package org.jvalue.commons.auth;


import org.jvalue.commons.db.GenericDocumentNotFoundException;
import org.jvalue.commons.db.factories.AuthRepositoryFactory;
import org.jvalue.commons.db.repositories.GenericRepository;
import org.jvalue.commons.db.repositories.GenericUserRepository;
import org.jvalue.commons.utils.Assert;
import org.jvalue.commons.utils.Log;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

/**
 * Manages lifecycle of user objects (registration, modification, deletion).
 */
@Singleton
public final class UserManager {

	private final GenericUserRepository userRepository;

	// basic auth
	private final GenericRepository<BasicCredentials> credentialsRepository;
	private final BasicAuthUtils authenticationUtils;

	@Inject
	UserManager(
			AuthRepositoryFactory authRepositoryFactory,
			BasicAuthUtils authenticationUtils) {

		this.userRepository = authRepositoryFactory.createUserRepository();
		this.credentialsRepository = authRepositoryFactory.createBasicCredentialRepository();
		this.authenticationUtils = authenticationUtils;
	}


	public List<User> getAll() {
		return userRepository.getAll();
	}


	public User findById(String userId) {
		return userRepository.findById(userId);
	}


	public User findByEmail(String userEmail) {
		return userRepository.findByEmail(userEmail);
	}


	public User add(BasicAuthUserDescription userDescription) {
		assertUserNotRegistered(userDescription.getEmail());
		Assert.assertTrue(
				authenticationUtils.isPartiallySecurePassword(userDescription.getPassword()),
				"password must have at least 8 characters and contain numbers");


		User user = new User(
			createRandomUserId(),
			userDescription.getName(),
			userDescription.getEmail(),
			userDescription.getRole());
		userRepository.add(user);

		// store credentials
		byte[] salt = authenticationUtils.generateSalt();
		byte[] encryptedPassword = authenticationUtils.getEncryptedPassword(userDescription.getPassword(), salt);
		BasicCredentials credentials = new BasicCredentials(user.getId(), encryptedPassword, salt);
		credentialsRepository.add(credentials);

		Log.info("added user " + user.getId());
		return user;
	}


	public boolean contains(String userEmail) {
		try {
			findByEmail(userEmail);
			return true;
		} catch (GenericDocumentNotFoundException dnfe) {
			return false;
		}
	}


	public User add(OAuthUserDescription userDescription, OAuthUtils.OAuthDetails oAuthDetails) {
		assertUserNotRegistered(oAuthDetails.getEmail());

		User user = new User(
			createRandomUserId(),
			oAuthDetails.getGoogleUserId(),
			oAuthDetails.getEmail(),
			userDescription.getRole());
		userRepository.add(user);

		Log.info("added user " + user.getId());
		return user;
	}


	private String createRandomUserId() {
		return UUID.randomUUID().toString();
	}


	public void remove(User user) {
		userRepository.remove(user);
		try {
			BasicCredentials credentials = credentialsRepository.findById(user.getId());
			credentialsRepository.remove(credentials);
		} catch (GenericDocumentNotFoundException dnfe) {
			// user wasn't using basic auth
		}
	}


	private void assertUserNotRegistered(String email) {
		if (contains(email)) {
			String msg = "User with email '"+ email +"' already registered!";
			Log.error(msg);
			throw new IllegalArgumentException(msg);
		}
	}

}
