package com.example.tests.integration.store;

import org.springframework.http.ResponseEntity;

public class CucumberTestContext {
    private String addressUrl;
    private int statusCode;
    private String body;
    private ResponseEntity response;
    private ResponseEntity responseUser;
    private ResponseEntity responseInventory;
    private ResponseEntity responseOrder;

    public ResponseEntity getResponseOrder() {
        return responseOrder;
    }

    public void setResponseOrder(ResponseEntity responseOrder) {
        this.responseOrder = responseOrder;
    }

    public ResponseEntity getResponseUser() {
        return responseUser;
    }

    public void setResponseUser(ResponseEntity responseUser) {
        this.responseUser = responseUser;
    }

    public ResponseEntity getResponseInventory() {
        return responseInventory;
    }

    public void setResponseInventory(ResponseEntity responseInventory) {
        this.responseInventory = responseInventory;
    }

    public String getAddressUrl() {
        return addressUrl;
    }

    public void setAddressUrl(String addressUrl) {
        this.addressUrl = addressUrl;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }
}
