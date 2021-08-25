package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import com.example.demo.resttemplate.MyRestErrorHandler;
import com.example.demo.resttemplate.MyRestTemplateLogger;

@Configuration
public class MyConfig {
    
    @Bean
    @RequestScope
    public RestTemplate myRestTemplate(HttpServletRequest inReq) {
      final String authHeader = 
        inReq.getHeader(HttpHeaders.AUTHORIZATION);
      //needed to buffer the requests. Otherwise the logging will read the incoming request
      //and it will not be available to the obejct Mapper
      ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
      final RestTemplate restTemplate = new RestTemplate(factory);
      
      restTemplate.setErrorHandler(new MyRestErrorHandler());
      //outgoing authentication (adds jwt token) interceptor
      if (authHeader != null && !authHeader.isEmpty()) {
        restTemplate.getInterceptors().add(
          (outReq, bytes, clientHttpReqExec) -> {
            outReq.getHeaders().set(
              HttpHeaders.AUTHORIZATION, authHeader
            );
            return clientHttpReqExec.execute(outReq, bytes);
          });
      }
      // in/out  logging interceptor
      restTemplate.getInterceptors().add(new MyRestTemplateLogger());
      return restTemplate;
    }
    
}

