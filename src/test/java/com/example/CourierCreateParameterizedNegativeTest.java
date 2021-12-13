package com.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.response.ValidatableResponse;

import static org.junit.Assert.assertEquals;

@RunWith (Parameterized.class)
public class CourierCreateParameterizedNegativeTest {

    private final Courier courier;
    private final int expectedStatus;
    private final String expectedErrorMessage;

    public CourierCreateParameterizedNegativeTest(Courier courier, int expectedStatus, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {Courier.getCourierWithLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"}
        };
    }

    @Test
    @DisplayName("Проверка невозможности создания аккаунта курьера с неполными данными")
    @Description("Тест пытается создать курьера, передав лишь одно из полей: только логин или только пароль")
    public void sendRequestWithIncompleteData() {
        ValidatableResponse response = new CourierClient().create(courier);
        String actualMessage = response
                .extract()
                .path("message");
        int code = response
                .extract()
                .path("code");
        assertEquals(expectedErrorMessage, actualMessage);
        assertEquals(expectedStatus, code);
    }
}
