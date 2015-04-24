package org.jvalue.commons;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Test;
import org.jvalue.commons.auth.AbstractUserDescription;
import org.jvalue.commons.auth.BasicAuthUserDescription;
import org.jvalue.commons.auth.OAuthUserDescription;
import org.jvalue.commons.auth.Role;

public class UserDescriptionTest {

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testJson() throws Exception {
		testJson(new OAuthUserDescription(Role.PUBLIC, "token"));
		testJson(new BasicAuthUserDescription("name", "email", Role.ADMIN, "password"));
	}


	private void testJson(AbstractUserDescription user) throws Exception {
		JsonNode json = mapper.valueToTree(user);
		Assert.assertEquals(user, mapper.treeToValue(json, AbstractUserDescription.class));
	}

}
