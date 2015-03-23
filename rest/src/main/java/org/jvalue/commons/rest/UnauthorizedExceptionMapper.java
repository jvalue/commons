package org.jvalue.commons.rest;


import org.jvalue.commons.auth.UnauthorizedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public final class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

	@Override
	public Response toResponse(UnauthorizedException exception) {
		return RestUtils.createJsonFormattedException("not authorized", 401).getResponse();
	}

}
