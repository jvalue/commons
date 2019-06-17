package org.jvalue.commons.auth;

import org.junit.BeforeClass;
import org.jvalue.commons.couchdb.DbConnectorFactory;
import org.jvalue.commons.couchdb.RepositoryAdapter;
import org.jvalue.commons.couchdb.test.AbstractRepositoryAdapterTest;
import org.jvalue.commons.utils.HttpServiceCheck;

import java.util.Optional;

public final class BasicCredentialsRepositoryTest extends AbstractRepositoryAdapterTest<BasicCredentials> {

	private static final String COUCHDB_HOST = Optional.ofNullable(System.getenv("ODS_IT_COUCHDB_HOST")).orElse("localhost");

	@BeforeClass
	public static void setup() {
		String couchDbUrl = "http://" + COUCHDB_HOST + ":5984/";
		HttpServiceCheck.check(couchDbUrl);
	}

	@Override
	protected RepositoryAdapter<?, ?, BasicCredentials> doCreateAdapter(DbConnectorFactory connectorFactory) {
		return new BasicCredentialsRepository(connectorFactory.createConnector(getClass().getSimpleName(), true));
	}

	@Override
	protected BasicCredentials doCreateValue(String id, String data) {
		return new BasicCredentials(id, data.getBytes(), data.getBytes());
	}

}
