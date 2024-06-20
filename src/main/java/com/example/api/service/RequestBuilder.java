package com.example.api.service;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
public class RequestBuilder {

    private static boolean filtersAdded = false;

    public static RequestSpecification requestSpec(String url) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON);


        if (!filtersAdded) {
            builder.addFilter(new RequestLoggingFilter());
            builder.addFilter(new ResponseLoggingFilter());
            filtersAdded = true;
        }

        return builder.build();
    }

    public static RequestSpecification requestSpec() {
        return requestSpec("http://localhost:8080/library");
    }

    public static ResponseSpecification responseSpecOK201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }
    public static ResponseSpecification responseSpecError(int statusCode) {
        return new ResponseSpecBuilder().expectStatusCode(statusCode).build();
    }


    public static void installSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}