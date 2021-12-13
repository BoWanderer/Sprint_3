package com.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.response.ValidatableResponse;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierLoginParameterizedNegativeTest {

    private static final Courier courier = Courier.getRandom();
    private static final CourierClient courierClient = new CourierClient();
    private int courierId;

    private final CourierCredentials courierCredentials;
    private final int expectedStatus;
    private final String expectedErrorMessage;

    public CourierLoginParameterizedNegativeTest(CourierCredentials courierCredentials,
                                                 int expectedStatus,
                                                 String expectedErrorMessage) {
        this.courierCredentials = courierCredentials;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
            return new Object[][]{
                {CourierCredentials.getCredentialsWithPasswordOnly(courier), 400, "Недостаточно данных для входа"},
                {CourierCredentials.getCredentialsWithLoginOnly(courier), 400, "Недостаточно данных для входа"},
                {CourierCredentials.getCredentialsWithRandomLogin(courier), 404 , "Учетная запись не найдена"},
                {CourierCredentials.getCredentialsWithRandomPassword(courier), 404 , "Учетная запись не найдена"},
            };
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Проверка ответов на различные некорректные варианты запроса логина")
    @Description("- Проверка авторизации с запросом из одного пароля\n" +
                 "- Проверка авторизации с запросом из одного логина\n" +
                 "- Проверка авторизации со случайным логином\n" +
                 "- Проверка авторизации со случайным паролем")
    public void courierLoginValidationTest() {
        courierClient.create(courier);
        ValidatableResponse login = new CourierClient().login(courierCredentials);
        courierId = courierClient
                .login(CourierCredentials.from(courier))
                .extract()
                .path("id");
        int ActualStatusCode = login
                .extract()
                .statusCode();
        assertEquals("Status code is incorrect",
                expectedStatus, ActualStatusCode);
        String actualMessage = login
                .extract()
                .path("message");
        assertEquals("Courier ID is incorrect",
                expectedErrorMessage, actualMessage);
    }
}
