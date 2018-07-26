package org.jvalue.commons.auth;

import org.jvalue.commons.db.repositories.GenericRepository;

public interface UserRepositoryFactory {
	GenericRepository<User> createUserRepository();
}
