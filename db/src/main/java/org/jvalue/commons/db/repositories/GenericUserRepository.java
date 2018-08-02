package org.jvalue.commons.db.repositories;

import org.jvalue.commons.EntityBase;
import org.jvalue.commons.auth.User;

public interface GenericUserRepository<T extends EntityBase> extends GenericRepository<T> {
	User findByEmail(String email);
}
