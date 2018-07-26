package org.jvalue.commons.auth;


import com.google.common.base.Optional;
import com.google.common.io.BaseEncoding;

import org.ektorp.DocumentNotFoundException;
import org.jvalue.commons.db.GenericRepository;

import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

/**
 * Handles basic (username + password) authentication.
 */
public final class BasicAuthenticator implements Authenticator {

	private final UserManager userManager;
	private final GenericRepository<BasicCredentials> credentialsRepository;
	private final BasicAuthUtils authenticationUtils;

	@Inject
	BasicAuthenticator(
			UserManager userManager,
			BasicCredentialsRepositoryFactory basicCredentialsRepositoryFactory,
			BasicAuthUtils authenticationUtils) {

		this.userManager = userManager;
		this.credentialsRepository = basicCredentialsRepositoryFactory.createBasicCredentialRepository();
		this.authenticationUtils  = authenticationUtils;
	}


	@Override
	public Optional<User> authenticate(String authHeader) {
		// decode credentials
		String token = new String(BaseEncoding.base64().decode(
				authHeader.replaceFirst("Basic ", "")),
				StandardCharsets.UTF_8);

		int colon = token.indexOf(':');
		if (colon < 0) return Optional.absent();
		String email = token.substring(0, colon);
		String password = token.substring(colon + 1);
		return authenticate(email,password);
	}


	public Optional<User> authenticate(String email, String password) {
		try {
			User user = userManager.findByEmail(email);
			BasicCredentials credentials = credentialsRepository.findById(user.getId());
			if (authenticationUtils.checkPassword(password, credentials)) {
				return Optional.of(user);
			} else {
				return Optional.absent();
			}

		} catch (DocumentNotFoundException dnfe) {
			return Optional.absent();
		}
	}

}
