package com.brotherhui;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;

@RestController
class ClientController {
	
    @Value("${ssltest.trust-store-password}")
    private String trustStorePassword;
    
    @Value("${ssltest.trust-store}")
    private Resource trustStore;
    
	@Value("${ssltest.key-store-password}")
	private String keyStorePassword;
	
	@Value("${ssltest.key-password}")
	private String keyPassword;
	
	@Value("${ssltest.key-store}")
	private Resource keyStore; 

	private static final Logger log = LoggerFactory.getLogger(ClientApplication.class);
    
    @RequestMapping("/restassure")
    public String restassure(@RequestParam String uri) throws IOException {
    	log.info("I am calling resource by RestAssured");
    	
    	//please refer to 
    	//https://github.com/rest-assured/rest-assured/wiki/Usage#ssl
    	
    	RestAssured.baseURI = uri;
        Response response = RestAssured.given().config(RestAssuredConfig.newConfig().sslConfig(SSLConfig.sslConfig().allowAllHostnames())).trustStore(trustStore.getURL().getFile(), trustStorePassword).keystore(keyStore.getURL().getFile(), keyStorePassword).get();
//        Response response = RestAssured.given().config(RestAssuredConfig.newConfig().sslConfig(SSLConfig.sslConfig().allowAllHostnames())).trustStore("client.jks", trustStorePassword).keystore("client.jks", keyStorePassword).get();

        return response.asString();
    }
}