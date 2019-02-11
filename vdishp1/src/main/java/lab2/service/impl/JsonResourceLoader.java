package lab2.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab1.service.impl.BusTripFactory;
import lab2.resource.Project;
import lab2.service.ResourceLoader;

import java.io.IOException;
import java.net.URL;

public class JsonResourceLoader implements ResourceLoader {
    private String resourcePath;
    private ObjectMapper mapper;

    public JsonResourceLoader(String resourcePath, ObjectMapper objectMapper) {
        this.resourcePath = resourcePath;
        this.mapper = objectMapper;
    }

    @Override
    public Project[] readProjectsFromFile() throws IOException {
        URL resourceUrl = BusTripFactory.class.getResource(resourcePath);
        return mapper.readValue(resourceUrl, Project[].class);
    }
}
