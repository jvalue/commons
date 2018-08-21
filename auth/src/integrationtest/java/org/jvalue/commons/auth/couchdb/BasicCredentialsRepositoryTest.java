package org.jvalue.commons.auth.couchdb;


import org.jvalue.commons.auth.BasicCredentials;
import org.jvalue.commons.auth.couchdb.BasicCredentialsRepository;
import org.jvalue.commons.couchdb.RepositoryAdapter;
import org.jvalue.commons.couchdb.test.AbstractRepositoryAdapterTest;
import org.jvalue.commons.db.DbConnectorFactory;

public final class BasicCredentialsRepositoryTest extends AbstractRepositoryAdapterTest<BasicCredentials> {

	@Override
	protected RepositoryAdapter<?, ?, BasicCredentials> doCreateAdapter(DbConnectorFactory connectorFactory) {
		return new BasicCredentialsRepository((DbConnectorFactory) connectorFactory.createConnector(getClass().getSimpleName(), true));
	}

	@Override
	protected BasicCredentials doCreateValue(String id, String data) {
		return new BasicCredentials(id, data.getBytes(), data.getBytes());
	}

}
