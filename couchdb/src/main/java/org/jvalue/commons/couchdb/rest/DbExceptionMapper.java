package org.jvalue.commons.couchdb.rest;


import org.jvalue.commons.db.GenericDocumentNotFoundException;
import org.jvalue.commons.rest.RestUtils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public final class DbExceptionMapper implements ExceptionMapper<GenericDocumentNotFoundException> {

	@Override
	public Response toResponse(GenericDocumentNotFoundException dnfe) {
		return RestUtils.createNotFoundException().getResponse();
	}

}
