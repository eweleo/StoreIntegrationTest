package com.example.tests.integration.store.stories.model;

import com.example.tests.integration.store.stories.jsonData.LocalDateTimeDeserializer;
import com.example.tests.integration.store.stories.jsonData.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean isDone;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime dateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ChoiceProduct> choiceProducts;

    public List<ChoiceProduct> getChoiceProducts() {
        return choiceProducts;
    }

    public void setChoiceProducts(List<ChoiceProduct> choiceProducts) {
        this.choiceProducts = choiceProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
