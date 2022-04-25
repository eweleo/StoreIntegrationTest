package com.example.tests.integration.store.stories.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Product {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;
    private String name;

    public Product(String name) {
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product() {

    }

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
}
