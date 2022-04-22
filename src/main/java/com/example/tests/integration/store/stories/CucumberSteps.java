package com.example.tests.integration.store.stories;

import com.example.tests.integration.store.CucumberTestContext;
import com.example.tests.integration.store.stories.model.ChoiceProduct;
import com.example.tests.integration.store.stories.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.http.HttpHeaders.TIMEOUT;


public class CucumberSteps {


    private static final String REMOTE_URL = "http://localhost:8888";
    private CucumberTestContext cucumberTestContext = new CucumberTestContext();

    private ObjectMapper objectMapper = new ObjectMapper();


    @Given("remote service instance")
    public void remoteServiceInstance() {
        cucumberTestContext.setAddressUrl(REMOTE_URL);
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

    @When("sending {string} request to {string} with {string}")
    public void sendingRequestToServiceWithBody(String requestType, String path, String body) throws Exception {
        HttpMethod method = HttpMethod.valueOf(requestType);


        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new
                HttpComponentsClientHttpRequestFactory(httpClient));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity entity = new HttpEntity<>(body, headers);

        if(method.equals(HttpMethod.PATCH)){
            switch (path){
                case  ("products"):
                    Product product = objectMapper.readValue((String) cucumberTestContext.getResponse().getBody(),Product.class);
                    String url = REMOTE_URL + "/" + path + "/" + product.getId();
                    ResponseEntity<Product>response = restTemplate.exchange(url, method, entity, Product.class);
                    cucumberTestContext.setResponse(response);
                    break;

            }

        }else{
        String url = REMOTE_URL + "/" + path;

        ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
        cucumberTestContext.setResponse(response);}
    }


    @When("sending delete request to {string}")
    public void sendingDeleteRequest(String path) throws Exception {
        HttpMethod method = HttpMethod.DELETE;
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity entity = HttpEntity.EMPTY;


        switch (path) {
            case "choice_products":

                ChoiceProduct choiceProduct = objectMapper.readValue((String) cucumberTestContext.getResponse().getBody(), ChoiceProduct.class);
                String urlChoice = REMOTE_URL + "/" + path + "/" + choiceProduct.getId();
                ResponseEntity<ChoiceProduct> choiceProductResponseEntity = restTemplate.exchange(urlChoice, method, entity, ChoiceProduct.class);
                cucumberTestContext.setResponse(choiceProductResponseEntity);
                break;
            case "products":
                Product product = (Product) cucumberTestContext.getResponse().getBody();
                String urlProduct = REMOTE_URL + "/" + path + "/" +product.getId();
                ResponseEntity<Product> productResponseEntity = restTemplate.exchange(urlProduct, method, entity,Product.class);
                cucumberTestContext.setResponse(productResponseEntity);
        }


    }

    @Then("response should be  with body not null")
    public void response_should_be_with_body_not_null() {
        Assert.assertEquals(HttpStatus.OK, cucumberTestContext.getResponse().getStatusCode());
        Assert.assertNotNull(cucumberTestContext.getResponse().getBody());
    }

    @Then("response for post should be ok with body not null")
    public void response_for_post_should_be_ok_with_body_not_null() {
        Assert.assertEquals(HttpStatus.CREATED, cucumberTestContext.getResponse().getStatusCode());
        Assert.assertNotNull(cucumberTestContext.getResponse().getBody());
    }

    @Then("response for delete should be no content")
    public void cleanAfterWhen(){
        Assert.assertEquals(HttpStatus.NO_CONTENT, cucumberTestContext.getResponse().getStatusCode());

    }


}
