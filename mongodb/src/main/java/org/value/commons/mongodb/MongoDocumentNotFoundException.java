package org.value.commons.mongodb;

public class MongoDocumentNotFoundException extends RuntimeException {

		private static final long serialVersionUID = -1817230646884819428L;

		public MongoDocumentNotFoundException(Throwable t) {
			super(t);
		}

		public MongoDocumentNotFoundException(String message) {
			super(message);
		}

		public MongoDocumentNotFoundException() {}
}
