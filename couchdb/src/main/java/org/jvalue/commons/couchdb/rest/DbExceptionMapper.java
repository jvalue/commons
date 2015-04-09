package org.jvalue.commons.couchdb.rest;


import org.ektorp.DocumentNotFoundException;
import org.jvalue.commons.rest.RestUtils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public final class DbExceptionMapper implements ExceptionMapper<DocumentNotFoundException> {

	@Override
	public Response toResponse(DocumentNotFoundException dnfe) {
		return RestUtils.createNotFoundException().getResponse();
	}

}
