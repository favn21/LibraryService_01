package com.example.api.tests;

import com.example.api.service.RequestBuilder;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {
    private static final int PORT = 8080;
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = getBaseURI();

    }

    protected static String getBaseURI() {
        return "http://localhost:" + PORT;
    }
}
