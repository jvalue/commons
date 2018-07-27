package org.jvalue.commons.auth;

import org.jvalue.commons.EntityBase;
import org.jvalue.commons.db.repositories.GenericRepository;

public interface GenericUserRepository<T extends EntityBase> extends GenericRepository<T> {
	User findByEmail(String email);
}
