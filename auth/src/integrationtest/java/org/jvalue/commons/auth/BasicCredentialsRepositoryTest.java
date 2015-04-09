package org.jvalue.commons.auth;

import org.jvalue.commons.couchdb.DbConnectorFactory;
import org.jvalue.commons.couchdb.RepositoryAdapter;
import org.jvalue.commons.couchdb.test.AbstractRepositoryAdapterTest;

public final class BasicCredentialsRepositoryTest extends AbstractRepositoryAdapterTest<BasicCredentials> {

	@Override
	protected RepositoryAdapter<?, ?, BasicCredentials> doCreateAdapter(DbConnectorFactory connectorFactory) {
		return new BasicCredentialsRepository(connectorFactory.createConnector(getClass().getSimpleName(), true));
	}

	@Override
	protected BasicCredentials doCreateValue(String id, String data) {
		return new BasicCredentials(id, data.getBytes(), data.getBytes());
	}

}
