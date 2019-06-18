package org.jvalue.commons.auth;

import org.junit.BeforeClass;
import org.jvalue.commons.couchdb.DbConnectorFactory;
import org.jvalue.commons.couchdb.RepositoryAdapter;
import org.jvalue.commons.couchdb.test.AbstractRepositoryAdapterTest;
import org.jvalue.commons.utils.HttpServiceCheck;

import java.util.Optional;

public final class ClientRepositoryTest extends AbstractRepositoryAdapterTest<User> {

	private static final String COUCHDB_HOST = Optional.ofNullable(System.getenv("ODS_IT_COUCHDB_HOST")).orElse("localhost");

	@BeforeClass
	public static void setup() {
		String couchDbUrl = "http://" + COUCHDB_HOST + ":5984/";
		HttpServiceCheck.check(couchDbUrl);
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
