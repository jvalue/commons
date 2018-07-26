package org.jvalue.commons.auth;

import org.jvalue.commons.auth.couchdb.UserRepository;
import org.jvalue.commons.couchdb.DbConnectorFactory;
import org.jvalue.commons.couchdb.RepositoryAdapter;
import org.jvalue.commons.couchdb.test.AbstractRepositoryAdapterTest;

public final class ClientRepositoryTest extends AbstractRepositoryAdapterTest<User> {

	@Override
	protected RepositoryAdapter<?, ?, User> doCreateAdapter(DbConnectorFactory connectorFactory) {
		return new UserRepository(connectorFactory.createConnector(getClass().getSimpleName(), true));
	}

	@Override
	protected User doCreateValue(String id, String data) {
		return new User(id, data, "someMail", Role.PUBLIC);
	}

}
