package org.jvalue.commons.db.factories;

import org.jvalue.commons.auth.BasicCredentials;
import org.jvalue.commons.auth.User;
import org.jvalue.commons.db.repositories.GenericRepository;
import org.jvalue.commons.db.repositories.GenericUserRepository;

public interface AuthRepositoryFactory {
	GenericUserRepository createUserRepository();

	GenericRepository<BasicCredentials> createBasicCredentialRepository();
}
