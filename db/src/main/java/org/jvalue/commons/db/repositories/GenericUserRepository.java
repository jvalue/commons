package org.jvalue.commons.db.repositories;

import org.jvalue.commons.auth.User;
public interface GenericUserRepository extends GenericRepository<User> {
	User findByEmail(String email);
}
