package com.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CourierCreateTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = Courier.getRandom();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Создание аккаунта курьера")
    @Description("При успехе должно вернуться ok: true")
    public void checkCourierCanBeCreated() {
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response
                .extract()
                .statusCode();
        boolean isCourierCreated = response
                .extract()
                .path("ok");
        courierId = courierClient
                .login(CourierCredentials.from(courier))
                .extract()
                .path("id");
        assertThat(statusCode, equalTo(201));
        assertTrue(isCourierCreated);
    }

    @Test
    @DisplayName("Проверка невозможности создания курьера с уже существующими кредами")
    @Description("Получаем ошибку при попытке создания курьера с уже существующим логином")
    public void checkCourierWithExistingLoginCannotBeCreated() {
        courierClient.create(courier);
        ValidatableResponse negativeResponse = courierClient.create(courier);
        int statusCodeNegativeResponse = negativeResponse
                .extract()
                .statusCode();
        String errorMessage = negativeResponse
                .extract()
                .path("message");
        courierId = courierClient
                .login(CourierCredentials.from(courier))
                .extract()
                .path("id");
        assertThat("Error status code",
                statusCodeNegativeResponse, equalTo(409));
        assertThat("Wrong body - massage",
                errorMessage, (equalTo("Этот логин уже используется. Попробуйте другой.")));
    }
}
