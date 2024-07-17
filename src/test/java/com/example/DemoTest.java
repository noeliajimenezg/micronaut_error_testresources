package com.example;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;
import org.junit.jupiter.api.TestInstance;

@Requires(property = "micronaut.test.resources.enabled", value = "true")
@MicronautTest(environments = "test")
@TestResourcesProperties(
        value = {
                "datasources.default.url",
                "datasources.default.username",
                "datasources.default.password"
        })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DemoTest {

    @Value("${datasources.default.url}")
    String jdbcUrl;

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
        System.out.println("jdbcUrl: " + jdbcUrl);
    }

}