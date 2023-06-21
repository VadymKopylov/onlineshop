package com.kopylov.onlineshop.back.util;

import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Setter
public class PropertiesReader {
    private final String path;
    private Properties properties;

    public PropertiesReader(String path) {
        this.path = path;
        properties = readProperties();
    }

    private Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new RuntimeException("Wrong path to properties");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading properties file: " + e.getMessage());
        }
        return properties;
    }

    public Properties getProperties() {
        return new Properties(properties);
    }

    public Long getProperties(String name) {
        String sessionTimeToLive = properties.getProperty(name);
        return Long.parseLong(sessionTimeToLive);
    }
}
