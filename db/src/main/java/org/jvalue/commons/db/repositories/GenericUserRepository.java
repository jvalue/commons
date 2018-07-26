package org.jvalue.commons.db.repositories;

import org.jvalue.commons.auth.User;
import org.jvalue.commons.db.GenericRepository;

public interface GenericUserRepository<T> extends GenericRepository<T>{
	User findByEmail(String email);
}
