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

    public static RequestSpecification requestSpecCreateBook(CreateBookRequest body) {
        return new RequestSpecBuilder()
                .setBasePath("/books/save")
                .setBody(body)
                .build();
    }

    public static RequestSpecification requestSpecGetBooksByAuthor(Long authorId) {
        return new RequestSpecBuilder()
                .setBasePath("/authors/" + authorId + "/books")
                .build();
    }

    public static ResponseSpecification responseStatusCode(int statusCode) {
        return new ResponseSpecBuilder().expectStatusCode(statusCode).build();
    }

    public static void installSpecification(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
