package com.example.tests.integration.store.stories;


import com.example.tests.integration.store.InterfaceTests;
import io.cucumber.junit.CucumberOptions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;

@Category(InterfaceTests.class)
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:/stories/feature")
public class CucumberInterfaceTest {
}
