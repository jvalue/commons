package org.jvalue.commons.auth;

import org.jvalue.commons.auth.couchdb.UserRepository;
import org.jvalue.commons.couchdb.RepositoryAdapter;
import org.jvalue.commons.couchdb.test.AbstractRepositoryAdapterTest;
import org.jvalue.commons.db.DbConnectorFactory;

public final class ClientRepositoryTest extends AbstractRepositoryAdapterTest<User> {

	@Override
	protected RepositoryAdapter<?, ?, User> doCreateAdapter(DbConnectorFactory connectorFactory) {
		return new UserRepository((DbConnectorFactory) connectorFactory.createConnector(getClass().getSimpleName(), true));
	}

	@Override
	protected User doCreateValue(String id, String data) {
		return new User(id, data, "someMail", Role.PUBLIC);
	}

}
