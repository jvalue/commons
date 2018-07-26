package org.jvalue.commons.auth;

import org.jvalue.commons.db.GenericRepository;

public interface BasicCredentialsRepositoryFactory {
	GenericRepository<BasicCredentials> createBasicCredentialRepository();
}
