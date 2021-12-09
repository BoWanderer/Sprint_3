package com.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Проверка авторизации курьера с корректными кредами")
    @Description("При успехе возвращается id")
    public void courierCanLogIn() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.login(CourierCredentials.from(courier));
        courierId = response
                .extract()
                .path ("id");
        int statusCodeSuccessfulLogin = response
                .extract()
                .statusCode();
        assertThat("Courier ID is incorrect",
                courierId, is(not(0)));
        assertThat(statusCodeSuccessfulLogin, equalTo(200));
    }
}
