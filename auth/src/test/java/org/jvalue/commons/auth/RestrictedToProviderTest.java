package org.jvalue.commons.auth;


import com.google.common.base.Optional;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.annotation.Annotation;

import javax.inject.Provider;
import javax.ws.rs.core.HttpHeaders;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public final class RestrictedToProviderTest {

	@Mocked private MultivaluedParameterExtractorProvider mpep;
	@Mocked private ServiceLocator serviceLocator;

	@Mocked private Parameter parameter;
	@Mocked private Provider<ContainerRequest> requestProvider;
	@Mocked private ContainerRequest containerRequest;

	@Mocked private BasicAuthenticator basicAuthenticator;
	@Mocked private OAuthAuthenticator oAuthAuthenticator;

	private RestrictedToProvider provider;

	private final User adminUser = new User("someId", "admin", "someMail", Role.ADMIN);
	private final User publicUser = new User("someId", "admin", "someMail", Role.PUBLIC);
	private final String basicAuthHeader = "Basic YWRtaW46YWRtaW4="; // admin:admin
	private final String oAuthHeader = "Bearer 12345"; // dummy values


	@Before
	public void setup() {
		provider = new RestrictedToProvider(mpep, serviceLocator, basicAuthenticator, oAuthAuthenticator);
	}


	@Test
	public void testValidBasicAuth() {
		Factory<User> factory = setupMocks(basicAuthenticator, basicAuthHeader, adminUser, false);
		User user = factory.provide();
		Assert.assertEquals(adminUser, user);
	}


	@Test
	public void testValidOauth() {
		Factory<User> factory = setupMocks(oAuthAuthenticator, oAuthHeader, adminUser, false);
		User user = factory.provide();
		Assert.assertEquals(adminUser, user);
	}


	@Test(expected = UnauthorizedException.class)
	public void testMissingHeader() {
		Factory<User> factory = setupMocks(basicAuthenticator, null, adminUser, false);
		factory.provide();
	}


	@Test(expected = UnauthorizedException.class)
	public void testInvalidCredentials() {
		Factory<User> factory = setupMocks(basicAuthenticator, "Basic Zm9vOmJhcg==", null, false);
		factory.provide();
	}


	@Test(expected = UnauthorizedException.class)
	public void testInvalidRole() {
		Factory<User> factory = setupMocks(basicAuthenticator, basicAuthHeader, publicUser, false);
		factory.provide();
	}


	public void testOptional() {
		Factory<User> factory = setupMocks(basicAuthenticator, basicAuthHeader, publicUser, true);
		Assert.assertNull(factory.provide());
	}


	private Factory<User> setupMocks(final Authenticator authenticator, final String authHeader, final User user, final boolean isAuthOptional) {
		new Expectations() {{
			authenticator.authenticate(authHeader);
			result = Optional.fromNullable(user);
			minTimes = 0;

			parameter.getRawType(); result = User.class;
			parameter.getAnnotation(RestrictedTo.class); result = new RestrictedTo() {
				@Override
				public Class<? extends Annotation> annotationType() { return null; }

				@Override
				public Role value() {
					return Role.ADMIN;
				}

				@Override
				public boolean isOptional() {
					return isAuthOptional;
				}
			};
			minTimes = 0;

			requestProvider.get(); result = containerRequest;

			containerRequest.getRequestHeader(HttpHeaders.AUTHORIZATION);
			result = authHeader;
		}};

		Factory<User> factory = provider.createValueFactory(parameter);
		Deencapsulation.setField(factory, requestProvider);
		return factory;
	}

}
