package com.learn.springmvc.exception;

public class IdAlreadyExistsException extends Exception {

  public IdAlreadyExistsException(String message) {
    super(message);
  }
}
