package com.epaylater.exceptionmapper;

import com.epaylater.exceptions.PhoneNumberNotExistException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class PhoneNumberNotExistExceptionMapper
    implements ExceptionMapper<PhoneNumberNotExistException> {

  @Override
  public Response toResponse(PhoneNumberNotExistException exception) {
    return Response.status(404)
        .entity("Account doesn't exists")
        .type(MediaType.TEXT_PLAIN)
        .build();

  }
}
