package com.n26.core;

public interface Command<Request> {
  public void execute(final Request request);
}
