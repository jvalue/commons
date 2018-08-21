package org.jvalue.commons.auth;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.jvalue.commons.EntityBase;

import java.util.Arrays;

import javax.validation.constraints.NotNull;


/**
 * Set of basic auth credentials (username + password).
 */
public final class BasicCredentials implements EntityBase {

	@NotNull private final String userId;
	@NotNull private final byte[] encryptedPassword;
	@NotNull private final byte[] salt;

	@JsonCreator
	public BasicCredentials(
			@JsonProperty("userId") String userId,
			@JsonProperty("encryptedPassword") byte[] encryptedPassword,
			@JsonProperty("salt") byte[] salt) {

		this.userId = userId;
		this.encryptedPassword = encryptedPassword;
		this.salt = salt;
	}

	@JsonProperty("userId")
	@Override
	public String getId() {
		return userId;
	}


	public byte[] getEncryptedPassword() {
		return encryptedPassword;
	}


	public byte[] getSalt() {
		return salt;
	}


	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof BasicCredentials)) return false;
		BasicCredentials credentials = (BasicCredentials) other;
		return Objects.equal(userId, credentials.userId)
				&& Arrays.equals(encryptedPassword, credentials.encryptedPassword)
				&& Arrays.equals(salt, credentials.salt);
	}


	@Override
	public int hashCode() {
		return Objects.hashCode(userId, Arrays.hashCode(encryptedPassword), Arrays.hashCode(salt));
	}

}
