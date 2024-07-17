package com.example.common;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import static java.util.Objects.isNull;

public class DatabaseProperties {
    public static void updateContentPropertyFile(String replaceValue) {
        try {
            File file =
                    new File(
                            Objects.requireNonNull(
                                            DatabaseProperties.class
                                                    .getClassLoader()
                                                    .getResource("config/UnitTestNew.properties"))
                                    .toURI());
            Charset charset = StandardCharsets.UTF_8;
            String content;
            content = Files.readString(file.toPath(), charset);
            if (!isNull(replaceValue)) {
                content =
                        content.replaceFirst(
                                "(?:JdbcUrl=.*)", "JdbcUrl=" + replaceValue);
            }
            Files.writeString(file.toPath(), content, charset);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
