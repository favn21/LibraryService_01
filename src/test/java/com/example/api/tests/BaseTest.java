package com.example.api.tests;


import com.example.api.config.JacksonConfig;
import com.example.api.service.TokenService;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        mapper = JacksonConfig.objectMapper();
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

