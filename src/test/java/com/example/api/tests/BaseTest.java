package com.example.api.tests;


import com.example.api.service.TokenService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;




public abstract class BaseTest extends BaseTestDB {

    protected static TokenService tokenService;
    protected static ObjectMapper mapper;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = getBaseURI();
        tokenService = new TokenService();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    protected static String getBaseURI() {
        return "http://localhost:8080/library";
    }
}

