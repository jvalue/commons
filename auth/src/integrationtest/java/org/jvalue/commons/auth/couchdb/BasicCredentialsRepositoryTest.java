package org.jvalue.commons.auth.couchdb;

import org.junit.BeforeClass;
import org.jvalue.commons.auth.BasicCredentials;
import org.jvalue.commons.couchdb.RepositoryAdapter;
import org.jvalue.commons.couchdb.test.AbstractRepositoryAdapterTest;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.utils.HttpServiceCheck;

public final class BasicCredentialsRepositoryTest extends AbstractRepositoryAdapterTest<BasicCredentials> {

	@BeforeClass
	public static void setup() {
		HttpServiceCheck.check(HttpServiceCheck.COUCHDB_URL);
	}

	@Override
	protected RepositoryAdapter<?, ?, BasicCredentials> doCreateAdapter(DbConnectorFactory connectorFactory) {
		return new BasicCredentialsRepository(connectorFactory);
	}

	@Override
	protected BasicCredentials doCreateValue(String id, String data) {
		return new BasicCredentials(id, data.getBytes(), data.getBytes());
	}

}
