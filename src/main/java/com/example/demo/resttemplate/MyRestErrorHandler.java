package com.example.demo.resttemplate;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.example.demo.exception.MyRestResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MyRestErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
    return (httpResponse.getStatusCode().series() == CLIENT_ERROR
        || httpResponse.getStatusCode().series() == SERVER_ERROR);
  }

  @Override
  public void handleError(ClientHttpResponse httpResponse) throws IOException {

    if (httpResponse.getStatusCode().series() == SERVER_ERROR) {
      // handle SERVER_ERROR
    } else if (httpResponse.getStatusCode().series() == CLIENT_ERROR) {
      // handle CLIENT_ERROR
      if (httpResponse.getStatusCode() == NOT_FOUND) {
        String errorMessage =
            new String(httpResponse.getBody().readAllBytes(), StandardCharsets.UTF_8);
        MyRestResponseException exception = parseMyException(errorMessage);
        if (exception != null) {
            throw exception;
        } else {
            throw new MyRestResponseException(null,"Did not work, we should have caught and rethown the Server exception", null, -1 );
        }
      }
    }
  }

  public MyRestResponseException parseMyException(String message) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(message, MyRestResponseException.class);
    } catch (JsonProcessingException e) {
      return new MyRestResponseException("001", "Object Not Found, unknown error", "Unknown", -1);
    }
  }
}
