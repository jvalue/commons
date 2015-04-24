package org.jvalue.commons.auth;


import com.google.common.base.Optional;

import javax.inject.Inject;

/**
 * Performs authentication using Google OAuth.
 */
public class OAuthAuthenticator implements Authenticator {

	private final UserManager userManager;
	private final OAuthUtils authUtils;

	@Inject
	OAuthAuthenticator(UserManager userManager, OAuthUtils authUtils) {
		this.userManager = userManager;
		this.authUtils = authUtils;
	}


	@Override
	public Optional<User> authenticate(String tokenString) {
		Optional<OAuthUtils.OAuthDetails> authDetails = authUtils.checkAuthHeader(tokenString);
		if (!authDetails.isPresent()) return Optional.absent();
		if (userManager.contains(authDetails.get().getEmail())) return Optional.absent();
		return Optional.of(userManager.findByEmail(authDetails.get().getEmail()));
	}

}
