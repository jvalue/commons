package org.jvalue.commons.auth;

import org.junit.Assert;
import org.junit.Test;


public final class BasicAuthenticationUtilsTest {

	private final BasicAuthenticationUtils utils = new BasicAuthenticationUtils();

	@Test
	public void testIsPartiallySecurePassword() {
		Assert.assertFalse(utils.isPartiallySecurePassword(null));
		Assert.assertFalse(utils.isPartiallySecurePassword("0123456"));
		Assert.assertFalse(utils.isPartiallySecurePassword("hello world"));
		Assert.assertTrue(utils.isPartiallySecurePassword("hello world 42"));
	}
}