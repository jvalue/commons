package org.jvalue.commons.auth;


import com.google.common.base.Optional;

/**
 * Tries to match {@link User} objects to credentials.
 */
public interface Authenticator {

	/**
	 * @return the authenticated user if any.
	 */
	public Optional<User> authenticate(String authHeader);

}
