package com.epaylater.exceptionmapper;

import com.epaylater.exceptions.ConcurrentTransactionException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ConcurrentTransactionExceptionMapper
    implements ExceptionMapper<ConcurrentTransactionException> {

  @Override public Response toResponse(ConcurrentTransactionException exception) {
    return Response.status(429)
        .entity("Retry after some time")
        .type(MediaType.TEXT_PLAIN)
        .build();
  }
}
