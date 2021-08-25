package com.example.demo.exception;

public class MyRestResponseException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private String errorCode;
  private String errorMessage;
  private String errorObject;
  private Integer errorObjectId;

  public MyRestResponseException(
      String errorCode, String errorMessage, String errorObject, Integer errorObjectId) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errorObject = errorObject;
    this.errorObjectId = errorObjectId;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorObject() {
    return errorObject;
  }

  public void setErrorObject(String errorObject) {
    this.errorObject = errorObject;
  }

  public Integer getErrorObjectId() {
    return errorObjectId;
  }

  public void setErrorObjectId(Integer errorObjectId) {
    this.errorObjectId = errorObjectId;
  }
}
