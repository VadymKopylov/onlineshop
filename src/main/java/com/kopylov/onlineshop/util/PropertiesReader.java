package com.kopylov.onlineshop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
            if(inputStream == null){
                throw new RuntimeException("Wrong path to properties");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public Properties getProperties() {
        return new Properties(properties);
    }
}
