package org.jvalue.commons.auth;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.jvalue.commons.couchdb.DbDocument;
import org.jvalue.commons.couchdb.DbDocumentAdaptable;
import org.jvalue.commons.couchdb.RepositoryAdapter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public final class UserRepository extends RepositoryAdapter<
		UserRepository.UserCouchDbRepository,
		UserRepository.UserDocument,
		User> {

	static final String DATABASE_NAME = "users";
	private static final String DOCUMENT_ID = "doc.value.name != null && doc.value.role != null";

	@Inject
	UserRepository(@Named(DATABASE_NAME) CouchDbConnector connector) {
		super(new UserCouchDbRepository(connector));
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
		@View(name = "by_id", map = "function(doc) { if (" + DOCUMENT_ID + ") emit(doc.value.name, doc._id)}")
		public UserDocument findById(String sourceId) {
			List<UserDocument> sources = queryView("by_id", sourceId);
			if (sources.isEmpty()) throw new DocumentNotFoundException(sourceId);
			if (sources.size() > 1)
				throw new IllegalStateException("found more than one source for id " + sourceId);
			return sources.get(0);
		}


		@Override
		public UserDocument createDbDocument(User user) {
			return new UserDocument(user);
		}


		@Override
		public String getIdForValue(User user) {
			return user.getName();
		}
	}


	static final class UserDocument extends DbDocument<User> {

		@JsonCreator
		public UserDocument(@JsonProperty("value") User user) {
			super(user);
		}

	}

}
