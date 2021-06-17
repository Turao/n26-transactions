package com.n26.core;

public interface Query<Request, Response> {
  public Response execute(final Request request);
}
