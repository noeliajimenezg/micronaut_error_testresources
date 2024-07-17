package com.example.common;


import static java.util.Objects.isNull;

import com.example.DemoTest;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.io.file.DefaultFileSystemResourceLoader;
import io.micronaut.flyway.event.MigrationFinishedEvent;
import jakarta.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@Singleton
public class JdbcPropertiesListener implements ApplicationEventListener<MigrationFinishedEvent> {

    public static final String SERVER_JDBC_URL = "MY_SERVER.JdbcUrl=";

    @Value("${datasources.default.url}")
    String jdbcUrl;

    @Value("${app.config}")
    String appConfig;

    @Override
    public boolean supports(MigrationFinishedEvent event) {
        return ApplicationEventListener.super.supports(event);
    }

    @Override
    public void onApplicationEvent(MigrationFinishedEvent event) {
        updateJdbcUrlProperty(jdbcUrl);
    }

    private void updateJdbcUrlProperty(String replaceValue) {
        try {
            // updating
            DefaultFileSystemResourceLoader loader =
                    new DefaultFileSystemResourceLoader("src/test/resources");
            File fileClasspath =
                    new File(
                            Objects.requireNonNull(
                                            DemoTest.class.getClassLoader().getResource(appConfig))
                                    .toURI());
            Charset charset = StandardCharsets.UTF_8;
            String contentClasspath;
            contentClasspath = Files.readString(fileClasspath.toPath(), charset);
            if (!isNull(replaceValue)) {
                contentClasspath =
                        contentClasspath.replaceFirst(
                                "MY_SERVER.JdbcUrl=.*", SERVER_JDBC_URL + replaceValue);
                Files.writeString(fileClasspath.toPath(), contentClasspath, charset);
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
