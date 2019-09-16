package com.stackroute.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Configuration
public class BaseService {
    String basicAuth="345abcd" ; //some auth key to use the esb services

@Setter @Getter
    RestTemplate restTemplate;
    @Setter
    HttpHeaders httpHeaders;
     public BaseService(){
         httpHeaders=new HttpHeaders();
         //httpHeaders.setAccept();
         //httpHeaders.setContentType(MediaType.APPLICATION_JSON);
         restTemplate=new RestTemplate();

     }

     public HttpHeaders getHttpHeaders(){
         httpHeaders.set(HttpHeaders.AUTHORIZATION,basicAuth);
         return httpHeaders;
     }

}
