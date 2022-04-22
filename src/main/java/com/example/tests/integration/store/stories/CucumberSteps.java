package com.example.tests.integration.store.stories;

import com.example.tests.integration.store.CucumberTestContext;
import com.example.tests.integration.store.stories.model.ChoiceProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.net.URL;


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
        HttpEntity entity = HttpEntity.EMPTY;
        String url =REMOTE_URL+"/"+path;


        ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
        cucumberTestContext.setResponse(response);
    }

    @When("sending {string} request to {string} with {string}")
    public void sendingRequestToServiceWithBody(String requestType, String path, String body){
        HttpMethod method = HttpMethod.valueOf(requestType);

        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);

        restTemplate.setRequestFactory(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity entity = new HttpEntity<>(body,headers);

        String url = REMOTE_URL + "/" + path;

        ResponseEntity<String> response = restTemplate.exchange(url,method,entity, String.class);
        cucumberTestContext.setResponse(response);
    }



    @When("sending delete request to {string}")
    public void sendingDeleteRequest( String path) throws Exception {
        HttpMethod method = HttpMethod.DELETE;
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity entity = HttpEntity.EMPTY;
        String body = (String) cucumberTestContext.getResponse().getBody();

        switch (path) {
            case "choice_products":

                ChoiceProduct choiceProduct = objectMapper.readValue((String) cucumberTestContext.getResponse().getBody(), ChoiceProduct.class);
                String url = REMOTE_URL + "/" + path + "/" + choiceProduct.getId();
                ResponseEntity<ChoiceProduct> response = restTemplate.exchange(url, method, entity, ChoiceProduct.class);
                cucumberTestContext.setResponse(response);
                break;
        }


    }

    @Then("response should be  with body not null")
    public void response_should_be_with_body_not_null() {
        Assert.assertEquals(HttpStatus.OK, cucumberTestContext.getResponse().getStatusCode());
        Assert.assertNotNull(cucumberTestContext.getResponse().getBody());
    }

    @Then("response for post should be ok with body not null")
    public void response_for_post_should_be_ok_with_body_not_null(){
        Assert.assertEquals(HttpStatus.CREATED, cucumberTestContext.getResponse().getStatusCode());
        Assert.assertNotNull(cucumberTestContext.getResponse().getBody());
    }

//    @Then("clean after when")
//    public void cleanAfterWhen(){
//        Assert.assertEquals(HttpEntity);
//
//    }


}
