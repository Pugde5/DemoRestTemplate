package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.authentication.JWTToken;
import com.example.demo.authentication.User;
import com.example.demo.entity.MyEntity;
import com.example.demo.exception.MyRestResponseException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController()
@RequestMapping("server")
public class MyController {
    
    @PostMapping("login")
    public ResponseEntity<JWTToken> login(@RequestBody() User user) {
        return ResponseEntity.ok(getJWTToken(user.getUsername()));       
    }

    private JWTToken getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("MyID")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        JWTToken jwtToken = new JWTToken();
        jwtToken.setToken(token);
        return jwtToken;
    }
    
    @RequestMapping(value = "/getMyEntity/{id}", method = RequestMethod.GET)
    public ResponseEntity<MyEntity> getMyEntity(@PathVariable("id") Integer id) {
        if (id == null || id % 2 == 0) {
            throw new MyRestResponseException("002","Entity not found", "MyEntity", id);
        }
        return ResponseEntity.ok(createMyEntity(id));
    }
    
    private MyEntity createMyEntity(Integer id) {
        MyEntity entity = new MyEntity();
        entity.setDescription("WORKED");
        entity.setId(id);
        return entity;
    }
}
