package com.example.api.service;

import com.example.api.models.request.CreateBookRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;


public class RequestBuilder {

    private static boolean filtersAdded = false;

    public static RequestSpecification requestSpec(String baseUrl) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
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

    public static RequestSpecification requestSpecCreateBook(CreateBookRequest body, String authToken) {
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080/library")
                .setBasePath("/books/save")
                .addHeader("Authorization", "Bearer " + authToken)
                .setContentType(ContentType.JSON)
                .setBody(body)
                .build();
    }

    public static RequestSpecification requestSpecGetBooksByAuthor(Long authorId, String authToken) {
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:8080/library")
                .setBasePath("/authors/" + authorId + "/books")
                .addHeader("Authorization", "Bearer " + authToken)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getRequestSpec(Object request, String authToken) {
        return given()
                .baseUri("http://localhost:8080/library")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken)
                .body(request);
    }

    public static RequestSpecification getRequestSpec(String authToken) {
        return given()
                .baseUri("http://localhost:8080/library")
                .header("Authorization", "Bearer " + authToken);
    }

    public static ResponseSpecification responseStatusCode(int statusCode) {
        return new ResponseSpecBuilder().expectStatusCode(statusCode).build();
    }

    public static void installSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
