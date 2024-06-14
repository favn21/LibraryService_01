package com.example.api.tests;

import com.example.api.service.RequestBuilder;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = getBaseURI();
        RequestBuilder.installSpecification(RequestBuilder.requestSpec(), RequestBuilder.responseSpecOK201());
    }

    protected static String getBaseURI() {
        return "http://localhost:8080";
    }
}
