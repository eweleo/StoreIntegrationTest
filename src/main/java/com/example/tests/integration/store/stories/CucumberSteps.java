package com.example.tests.integration.store.stories;

import com.example.tests.integration.store.CucumberTestContext;
import com.example.tests.integration.store.stories.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


public class CucumberSteps {


    private static final String REMOTE_URL = "http://localhost:8888";
    private CucumberTestContext cucumberTestContext = new CucumberTestContext();

    private ObjectMapper objectMapper = new ObjectMapper();


    @Given("remote service instance")
    public void remoteServiceInstance() {
        cucumberTestContext.setAddressUrl(REMOTE_URL);
    }

    @Given("product's body, name: {string}")
    public void productBody(String name) throws Exception {
        Product product = new Product();
        product.setName(name);
        String body = objectMapper.writeValueAsString(product);
        cucumberTestContext.setBody(body);
    }

    @Given("inventory's body, name: {string}, quantity: {int}")
    public void inventoryBody(String name, int quantity) {

        String body = "{\"name\": \"" + name + "\" ,\"quantity\": " + quantity + "}";
        cucumberTestContext.setBody(body);
    }

    @Given("user's body, name: {string}, surname: {string}")
    public void userBody(String name, String surname) throws Exception {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        String body = objectMapper.writeValueAsString(user);
        cucumberTestContext.setBody(body);
    }

    @Given("order's body")
    public void orderBody() throws Exception {
        User user = objectMapper.readValue((String) cucumberTestContext.getResponseUser().getBody(), User.class);
        String body = "{\"id\": " + user.getId() + "}";
        cucumberTestContext.setBody(body);
    }

    @Given("choice_product's body, quantity {int}")
    public void choiceProductBody(int quantity) throws Exception {
        Inventory inventory = objectMapper.readValue((String) cucumberTestContext.getResponseInventory().getBody(), Inventory.class);
        User user = objectMapper.readValue((String) cucumberTestContext.getResponseUser().getBody(), User.class);
        String body = "{\"userId\": " + user.getId() + ", \"productId\": " + inventory.getProduct().getId()
                + ", \"quantity\": " + quantity + "}";
        cucumberTestContext.setBody(body);

    }

    @When("sending {string} request to {string}")
    public void sendingRequestToService(String requestType, String path) {
        HttpMethod method = HttpMethod.valueOf(requestType);
        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new
                HttpComponentsClientHttpRequestFactory(httpClient));
        HttpEntity entity = HttpEntity.EMPTY;
        String url = REMOTE_URL + "/" + path;

        ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
        cucumberTestContext.setResponse(response);
    }

    @When("sending {string} request to {string} with body")
    public void sendingRequestToServiceWithBody(String requestType, String path) throws Exception {
        HttpMethod method = HttpMethod.valueOf(requestType);

        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new
                HttpComponentsClientHttpRequestFactory(httpClient));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = cucumberTestContext.getBody();
        HttpEntity entity = new HttpEntity<>(body, headers);
        String url;
        ResponseEntity<?> response = null;

        if (method.equals(HttpMethod.PATCH)) {
            switch (path) {
                case "products":
                    Product product = objectMapper.readValue((String) cucumberTestContext.getResponse().getBody(), Product.class);
                    url = REMOTE_URL + "/" + path + "/" + product.getId();
                    response = restTemplate.exchange(url, method, entity, Product.class);
                    break;
                case "inventory":
                    Inventory inventory = objectMapper.readValue((String) cucumberTestContext.getResponse().getBody(), Inventory.class);
                    url = REMOTE_URL + "/" + path + "/" + inventory.getId();
                    response = restTemplate.exchange(url, method, entity, Inventory.class);
                    break;
                case "users":
                    User user = objectMapper.readValue((String) cucumberTestContext.getResponse().getBody(), User.class);
                    url = REMOTE_URL + "/" + path + "/" + user.getId();
                    response = restTemplate.exchange(url, method, entity, User.class);
                    break;
                case "orders":
                    Order order = objectMapper.readValue((String) cucumberTestContext.getResponse().getBody(), Order.class);
                    url = REMOTE_URL + "/" + path + "/" + order.getId();
                    response = restTemplate.exchange(url, method, entity, Order.class);
                    break;
                case "choice_products":
                    ChoiceProduct choiceProduct = objectMapper.readValue((String) cucumberTestContext.getResponse().getBody(),ChoiceProduct.class);
                    url = REMOTE_URL + "/" + path + "/" + choiceProduct.getId();
                    response = restTemplate.exchange(url, method, entity, ChoiceProduct.class);
                    break;
            }

        } else {
            url = REMOTE_URL + "/" + path;

            response = restTemplate.exchange(url, method, entity, String.class);

            switch (path) {
                case "users":
                    cucumberTestContext.setResponseUser(response);
                    break;
                case "inventory":
                    cucumberTestContext.setResponseInventory(response);
                    break;
                case "orders":
                    cucumberTestContext.setResponseOrder(response);
            }
        }
        cucumberTestContext.setResponse(response);
    }


    @When("sending {string} request to {string} with parameter")
    public void sendingDeleteRequest(String requestType, String path) throws Exception {
        HttpMethod method = HttpMethod.valueOf(requestType);

        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new
                HttpComponentsClientHttpRequestFactory(httpClient));

        HttpEntity entity = HttpEntity.EMPTY;
        String url;
        ResponseEntity<?> response = null;


        switch (path) {
            case "choice_products":
                ChoiceProduct choiceProduct = (ChoiceProduct) cucumberTestContext.getResponse().getBody();
                url = REMOTE_URL + "/" + path + "/" + choiceProduct.getId();
                response = restTemplate.exchange(url, method, entity, ChoiceProduct.class);
                break;
            case "products":
                Product product = (Product) cucumberTestContext.getResponse().getBody();
                url = REMOTE_URL + "/" + path + "/" + product.getId();
                response = restTemplate.exchange(url, method, entity, Product.class);
                break;
            case "inventory":
                Inventory inventory = objectMapper.readValue((String) cucumberTestContext.getResponseInventory().getBody(),Inventory.class);
                url = REMOTE_URL + "/" + path + "/" + inventory.getId();
                response = restTemplate.exchange(url, method, entity, Inventory.class);
                break;
            case "users":
                User user = objectMapper.readValue((String) cucumberTestContext.getResponseUser().getBody(), User.class);
                url = REMOTE_URL + "/" + path + "/" + user.getId();
                response = restTemplate.exchange(url, method, entity, User.class);
                break;
            case "orders":
                Order order;
                if (method.equals(HttpMethod.PATCH)) {
                    order = objectMapper.readValue((String) cucumberTestContext.getResponseOrder().getBody(), Order.class);
                } else {
                    order = (Order) cucumberTestContext.getResponse().getBody();
                }
                url = REMOTE_URL + "/" + path + "/" + order.getId();
                response = restTemplate.exchange(url, method, entity, Order.class);
                break;
        }
        cucumberTestContext.setResponse(response);
    }

    @Then("response should be {int}  with body not null")
    public void response_should_be_with_body_not_null(int expended) {
        switch (expended) {
            case 200:
                Assert.assertEquals(HttpStatus.OK, cucumberTestContext.getResponse().getStatusCode());
                break;
            case 201:
                Assert.assertEquals(HttpStatus.CREATED, cucumberTestContext.getResponse().getStatusCode());
                break;
        }

        Assert.assertNotNull(cucumberTestContext.getResponse().getBody());
    }


    @Then("response for delete should be no content")
    public void cleanAfterWhen() {
        Assert.assertEquals(HttpStatus.NO_CONTENT, cucumberTestContext.getResponse().getStatusCode());

    }


}
