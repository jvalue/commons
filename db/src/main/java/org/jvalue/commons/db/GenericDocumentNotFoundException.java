package org.jvalue.commons.db;

public class GenericDocumentNotFoundException extends RuntimeException {

		private static final long serialVersionUID = -1817230646884819428L;

		public GenericDocumentNotFoundException(Throwable t) {
			super(t);
		}

		public GenericDocumentNotFoundException(String message) {
			super(message);
		}

		public GenericDocumentNotFoundException() {}
}
