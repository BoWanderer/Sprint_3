package com.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierClient extends com.example.RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier/";

    @Step("Создание аккаунта курьера")
    public ValidatableResponse create(com.example.Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login/")
                .then();
    }

    @Step("Удаление аккаунта курьера")
    public boolean delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }
}