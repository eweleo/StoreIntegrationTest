package com.example.tests.integration.store.stories.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class User {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String surname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Order> orders;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
