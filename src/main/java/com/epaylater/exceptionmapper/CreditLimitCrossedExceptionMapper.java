package com.epaylater.exceptionmapper;

import com.epaylater.exceptions.CreditLimitCrossedException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CreditLimitCrossedExceptionMapper
    implements ExceptionMapper<CreditLimitCrossedException> {

  @Override public Response toResponse(CreditLimitCrossedException exception) {
    return Response.status(405)
        .entity("Credit limit crossed")
        .type(MediaType.TEXT_PLAIN)
        .build();
  }
}
