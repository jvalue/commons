package org.jvalue.commons.auth;


import org.ektorp.DocumentNotFoundException;

import java.util.List;
import java.util.UUID;

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


	public boolean contains(String userEmail) {
		try {
			findByEmail(userEmail);
			return true;
		} catch (DocumentNotFoundException dnfe) {
			return false;
		}
	}


	public User add(UserDescription userDescription) {
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

}
