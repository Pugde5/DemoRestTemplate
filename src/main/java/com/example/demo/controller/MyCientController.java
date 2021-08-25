package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.authentication.JWTToken;
import com.example.demo.authentication.User;
import com.example.demo.entity.MyEntity;
import com.example.demo.exception.MyRestResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController()
@RequestMapping("client")
public class MyCientController {
    
    @Autowired
    private RestTemplate myRestTemplate;
    
    private static final String AUTHENTICATION_URL = "http://localhost:8080/server/login";
    private static final String GET_URL = "http://localhost:8080/server/getMyEntity/";
    
    @RequestMapping(value = "myentity/{id}", method = RequestMethod.GET)
    public ResponseEntity<MyEntity> getMyEntity(@PathVariable("id") Integer id) {
        return myRestTemplate.getForEntity(GET_URL + id , MyEntity.class);
    }
    
    private MyEntity createMyEntity(Integer id) {
        MyEntity entity = new MyEntity();
        entity.setDescription("WORKED");
        entity.setId(id);
        return entity;
    }
}
