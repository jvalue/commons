package org.jvalue.commons.auth.couchdb;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.jvalue.commons.auth.User;
import org.jvalue.commons.couchdb.DbDocument;
import org.jvalue.commons.couchdb.DbDocumentAdaptable;
import org.jvalue.commons.couchdb.RepositoryAdapter;
import org.jvalue.commons.db.DbConnectorFactory;
import org.jvalue.commons.db.GenericDocumentNotFoundException;
import org.jvalue.commons.db.repositories.GenericUserRepository;

import java.util.List;

public final class UserRepository extends RepositoryAdapter<
		UserRepository.UserCouchDbRepository,
		UserRepository.UserDocument,
		User> implements GenericUserRepository {

	public static final String DATABASE_NAME = "users";
	private static final String DOCUMENT_ID = "doc.value.id != null && doc.value.role != null";

	public UserRepository(DbConnectorFactory connector) {
		super(new UserCouchDbRepository((CouchDbConnector) connector.createConnector(DATABASE_NAME,true)));
	}


	@Override
	public User findByEmail(String email) {
		try{
			return repository.findByEmail(email).getValue();
		}catch (DocumentNotFoundException e){
			throw new GenericDocumentNotFoundException(e);
		}
	}


	@View( name = "all", map = "function(doc) { if (" + DOCUMENT_ID + ") emit(null, doc)}")
	static final class UserCouchDbRepository
			extends CouchDbRepositorySupport<UserDocument>
			implements DbDocumentAdaptable<UserDocument, User> {


		public UserCouchDbRepository(CouchDbConnector connector) {
			super(UserDocument.class, connector);
			initStandardDesignDocument();
		}


		@Override
		@View(name = "by_id", map = "function(doc) { if (" + DOCUMENT_ID + ") emit(doc.value.id, doc._id)}")
		public UserDocument findById(String userId) {
			List<UserDocument> users = queryView("by_id", userId);
			if (users.isEmpty()) throw new DocumentNotFoundException(userId);
			if (users.size() > 1)
				throw new IllegalStateException("found more than one user for id " + userId);
			return users.get(0);
		}


		@View(name = "by_email", map = "function(doc) { if (" + DOCUMENT_ID + ") emit(doc.value.email, doc._id)}")
		public UserDocument findByEmail(String userEmail) {
			List<UserDocument> users = queryView("by_email", userEmail);
			if (users.isEmpty()) throw new DocumentNotFoundException(userEmail);
			if (users.size() > 1)
				throw new IllegalStateException("found more than one user for email " + userEmail);
			return users.get(0);
		}


		@Override
		public UserDocument createDbDocument(User user) {
			return new UserDocument(user);
		}


		@Override
		public String getIdForValue(User user) {
			return user.getId();
		}
	}


	static final class UserDocument extends DbDocument<User> {

		@JsonCreator
		public UserDocument(@JsonProperty("value") User user) {
			super(user);
		}

	}

}
