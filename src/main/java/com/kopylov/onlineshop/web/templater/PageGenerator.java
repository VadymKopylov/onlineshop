package com.kopylov.onlineshop.web.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Collections;
import java.util.Map;


import freemarker.cache.ClassTemplateLoader;

public class PageGenerator {
    private static final String TEMPLATE_DIR = "/templates";

    private static PageGenerator pageGenerator;
    private final Configuration configuration;

    private PageGenerator() {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setTemplateLoader(new ClassTemplateLoader(PageGenerator.class, TEMPLATE_DIR));
    }

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename) {
        return getPage(filename, Collections.emptyMap());
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = configuration.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }
}