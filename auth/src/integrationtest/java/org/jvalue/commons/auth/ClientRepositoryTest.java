package org.jvalue.commons.auth;

import org.junit.BeforeClass;
import org.jvalue.commons.couchdb.DbConnectorFactory;
import org.jvalue.commons.couchdb.RepositoryAdapter;
import org.jvalue.commons.couchdb.test.AbstractRepositoryAdapterTest;
import org.jvalue.commons.utils.HttpServiceCheck;

public final class ClientRepositoryTest extends AbstractRepositoryAdapterTest<User> {

	@BeforeClass
	public static void setup() {
		HttpServiceCheck.check(HttpServiceCheck.COUCHDB_URL);
	}

	@Override
	protected RepositoryAdapter<?, ?, User> doCreateAdapter(DbConnectorFactory connectorFactory) {
		return new UserRepository(connectorFactory.createConnector(getClass().getSimpleName(), true));
	}

	@Override
	protected User doCreateValue(String id, String data) {
		return new User(id, data, "someMail", Role.PUBLIC);
	}

}
