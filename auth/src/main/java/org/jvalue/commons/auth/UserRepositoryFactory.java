package org.jvalue.commons.auth;

public interface UserRepositoryFactory {
	GenericUserRepository<User> createUserRepository();
}
