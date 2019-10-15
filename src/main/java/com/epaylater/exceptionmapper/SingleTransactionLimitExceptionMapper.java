package com.epaylater.exceptionmapper;

import com.epaylater.exceptions.SingleTransactionLimitException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SingleTransactionLimitExceptionMapper implements ExceptionMapper<SingleTransactionLimitException> {

	@Override
	public Response toResponse(SingleTransactionLimitException exception) {
		return Response.status(420)
				.entity("Maximum transaction allowed is 100000")
				.type(MediaType.TEXT_PLAIN)
				.build();
	}

}
