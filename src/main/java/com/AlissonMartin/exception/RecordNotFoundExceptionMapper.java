package com.AlissonMartin.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RecordNotFoundExceptionMapper implements ExceptionMapper<RecordNotFoundException> {
  @Override
  public Response toResponse(RecordNotFoundException exception) {

    return Response.status(Response.Status.NOT_FOUND)
            .build();
  }
}