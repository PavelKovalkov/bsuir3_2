package lab2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab2.service.ResourceLoader;
import lab2.service.impl.JsonResourceLoader;
import lab2.service.impl.ProjectsHolder;
import lab2.service.impl.ResourceLoaderProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public ResourceLoader resourceLoader() {
        return new ResourceLoaderProxy(
                new JsonResourceLoader("/json/projects.json", new ObjectMapper()),
                new ProjectsHolder());
    }

    @Bean
    public Set<String> readWriteAccessEmployees() {
        Set<String> readWriteAccessEmployees = new HashSet<>();
        readWriteAccessEmployees.add("Человек 1");
        readWriteAccessEmployees.add("Человек 2");
        return readWriteAccessEmployees;
    }

    @Bean
    public Set<String> employees() {
        Set<String> employees = new HashSet<>();
        employees.add("Человек 1");
        employees.add("Человек 2");
        employees.add("Наблюдатель");
        return employees;
    }
}
