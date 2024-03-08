package com.example;

import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.extensions.testresources.TestResourcesPropertyProvider;
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import java.util.Map;

import static com.example.common.DatabaseProperties.updateContentPropertyFile;

@MicronautTest
@TestResourcesProperties(
        value = "datasources.default.url",
        //    value = "auto.test.resources.datasources.default.url",
        providers = DemoTest.DatabasePropertiesProvider.class)
class DemoTest {

    @ReflectiveAccess
    public static class DatabasePropertiesProvider implements TestResourcesPropertyProvider {
        @Override
        public Map<String, String> provide(Map<String, Object> testProperties) {
            testProperties.forEach((k, v) -> System.out.println("Value of map: " + k + " " + v));
            String str = (String) testProperties.get("datasources.default.url");
            System.out.println(
                            (String) testProperties.get("datasources.default.url")
                            + " "
                            + (String) testProperties.get("auto.test.resources.datasources.default.url"));
            updateContentPropertyFile(str);
            return Map.of();
        }
    }

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

}
