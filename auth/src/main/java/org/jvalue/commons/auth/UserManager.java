package org.jvalue.commons.auth;


import org.ektorp.DocumentNotFoundException;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manages lifecycle of user objects (registration, modification, deletion).
 */
@Singleton
public final class UserManager {

	private final UserRepository userRepository;

	// basic auth
	private final BasicCredentialsRepository credentialsRepository;
	private final BasicAuthenticationUtils authenticationUtils;


	@Inject
	UserManager(
			UserRepository userRepository,
			BasicCredentialsRepository credentialsRepository,
			BasicAuthenticationUtils authenticationUtils) {

		this.userRepository = userRepository;
		this.credentialsRepository = credentialsRepository;
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


	public void add(User user, String password) {
		// store user
		userRepository.add(user);

		// store credentials
		byte[] salt = authenticationUtils.generateSalt();
		byte[] encryptedPassword = authenticationUtils.getEncryptedPassword(password, salt);
		BasicCredentials credentials = new BasicCredentials(user.getId(), encryptedPassword, salt);
		credentialsRepository.add(credentials);
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

}
