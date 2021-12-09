package com.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

public class OrderTest {

    @Test
    @DisplayName("Проверка наличия списка заказов в ответе")
    @Description("Возвращаемый список должен быть не пуст")
    public void getOrdersReturnsTest() {
        OrderClient ordersClient = new OrderClient();
        ValidatableResponse response = ordersClient.orderList();
        response.assertThat()
                .body("orders.size()", is(not(0)));
    }
}
