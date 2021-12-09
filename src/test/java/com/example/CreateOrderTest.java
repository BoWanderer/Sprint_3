package com.example;

import java.util.List;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorType() {
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK","GREY")},
                {List.of()}
        };
    }

    @Test
    @DisplayName("Проверка создания заказа")
    @Description("- С цветом BLACK\n" +
                 "- С цветом GREY\n" +
                 "- С цветами BLACK,GREY\n" +
                 "- Значение цвета не передаётся в запросе")
    public void orderCanBeCreatedTest() {
        Order order = Order.generateRandomOrderWithDefiniteColor(color);
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.createOrder(order);
        int statusCode = response
                .extract()
                .statusCode();
        int trackId = response
                .extract()
                .path("track");
        assertThat("Status code is incorrect",
                statusCode, equalTo(201));
        assertThat("Track number not created",
                trackId, is(not(0)));
    }
}