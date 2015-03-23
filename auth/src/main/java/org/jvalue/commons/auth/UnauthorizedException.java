package org.jvalue.commons.auth;


/**
 * Nope, you didn't have those rights ...
 */
public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException(String message) {
		super(message);
	}


	public UnauthorizedException() { }

}
