package com.example;

import java.util.List;
import java.time.Instant;
import org.apache.commons.lang3.RandomStringUtils;

public class Order {
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<String> color;
    public static Instant time = Instant.now();

    public Order(String firstName,
                 String lastName,
                 String address,
                 String metroStation,
                 String phone,
                 int rentTime,
                 String deliveryDate,
                 String comment,
                 List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order generateRandomOrderWithDefiniteColor(List<String> color) {
        final String firstName = RandomStringUtils.randomAlphabetic(6);
        final String lastName =  RandomStringUtils.randomAlphabetic(6);
        final String address = RandomStringUtils.randomAlphabetic(6);
        final String metroStation = RandomStringUtils.randomAlphabetic(6);
        final String phone = RandomStringUtils.randomNumeric(11);
        final int rentTime = (int) (Math.random() * 9);
        final String deliveryDate = time.toString();
        final String comment = RandomStringUtils.randomAlphabetic(10);
        return new Order(firstName,
                         lastName,
                         address,
                         metroStation,
                         phone,
                         rentTime,
                         deliveryDate,
                         comment,
                         color);
    }
}