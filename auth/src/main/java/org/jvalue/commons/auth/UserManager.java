package org.jvalue.commons.auth;


import org.ektorp.DocumentNotFoundException;
import org.jvalue.commons.db.repositories.GenericRepository;
import org.jvalue.commons.utils.Assert;
import org.jvalue.commons.utils.Log;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manages lifecycle of user objects (registration, modification, deletion).
 */
@Singleton
public final class UserManager {

	private final GenericRepository<User> userRepository;

	// basic auth
	private final GenericRepository<BasicCredentials> credentialsRepository;
	private final BasicAuthUtils authenticationUtils;

	@Inject
	UserManager(
			UserRepositoryFactory userRepositoryFactory,
			BasicCredentialsRepositoryFactory basicCredentialsRepositoryFactory,
			BasicAuthUtils authenticationUtils) {

		this.userRepository = userRepositoryFactory.createUserRepository();
		this.credentialsRepository = basicCredentialsRepositoryFactory.createBasicCredentialRepository();
		this.authenticationUtils = authenticationUtils;
	}


	public List<User> getAll() {
		return userRepository.getAll();
	}


	public User findById(String userId) {
		return userRepository.findById(userId);
	}


	public User findByEmail(String userEmail) {
		return ((GenericUserRepository<User>) userRepository).findByEmail(userEmail);
	}


	public boolean contains(String userEmail) {
		try {
			findByEmail(userEmail);
			return true;
		} catch (DocumentNotFoundException dnfe) {
			return false;
		}
	}


	public User add(BasicAuthUserDescription userDescription) {
		assertUserNotRegistered(userDescription.getEmail());
		Assert.assertTrue(
				authenticationUtils.isPartiallySecurePassword(userDescription.getPassword()),
				"password must have at least 8 characters and contain numbers");

		// store new user
		String userId = UUID.randomUUID().toString();
		User user = new User(userId, userDescription.getName(), userDescription.getEmail(), userDescription.getRole());

		// store user
		userRepository.add(user);

		// store credentials
		byte[] salt = authenticationUtils.generateSalt();
		byte[] encryptedPassword = authenticationUtils.getEncryptedPassword(userDescription.getPassword(), salt);
		BasicCredentials credentials = new BasicCredentials(user.getId(), encryptedPassword, salt);
		credentialsRepository.add(credentials);

		Log.info("added user " + user.getId());
		return user;
	}


	public User add(OAuthUserDescription userDescription, OAuthUtils.OAuthDetails oAuthDetails) {
		assertUserNotRegistered(oAuthDetails.getEmail());

		// store new user
		String userId = UUID.randomUUID().toString();
		User user = new User(userId, oAuthDetails.getGoogleUserId(), oAuthDetails.getEmail(), userDescription.getRole());

		// store user
		userRepository.add(user);

		Log.info("added user " + user.getId());
		return user;
	}


	public void remove(User user) {
		userRepository.remove(user);
		try {
			BasicCredentials credentials = credentialsRepository.findById(user.getId());
			credentialsRepository.remove(credentials);
		} catch (DocumentNotFoundException dnfe) {
			// user wasn't using basic auth
		}
	}


	private void assertUserNotRegistered(String email) {
		Assert.assertFalse(contains(email), "already registered user with email " + email);
	}

}
