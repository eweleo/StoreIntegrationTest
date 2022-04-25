package com.example.tests.integration.store.stories.model;

import com.example.tests.integration.store.stories.jsonData.LocalDateTimeDeserializer;
import com.example.tests.integration.store.stories.jsonData.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class Inventory {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Product product;
    private int quantity;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime dateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
