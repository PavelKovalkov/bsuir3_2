package lab2.controller;

import lab2.resource.Project;
import lab2.service.ResourceLoader;
import lab2.service.chain.Middleware;
import lab2.service.chain.impl.ReadWriteAccessesMiddleware;
import lab2.service.chain.impl.UserExistsMiddleware;
import lab2.service.impl.ProjectsHolder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
public class ProjectController {
    private final Set<String> employees;
    private final Set<String> readWriteAccessEmployees;
    private final ResourceLoader resourceLoader;
    private final ProjectsHolder projectsHolder;

    @Autowired
    public ProjectController(Set<String> readWriteAccessEmployees,
                             Set<String> employees,
                             ResourceLoader resourceLoader,
                             ProjectsHolder projectsHolder) {
        this.readWriteAccessEmployees = readWriteAccessEmployees;
        this.employees = employees;
        this.resourceLoader = resourceLoader;
        this.projectsHolder = projectsHolder;
    }

    @GetMapping(path = "/get/projects")
    public ResponseEntity<Object> getInfo(@RequestBody String employeeName) throws IOException {
        Middleware middleware = new UserExistsMiddleware(employees);

        if (!middleware.check(employeeName)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Project[] projects = resourceLoader.readProjectsFromFile();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addRecord(@RequestBody String body) throws IOException {
        JSONObject object = new JSONObject(body);

        Middleware middleware = new UserExistsMiddleware(employees)
                .linkWith(new ReadWriteAccessesMiddleware(readWriteAccessEmployees));

        if (!middleware.check(object.getString("employee_name"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        resourceLoader.readProjectsFromFile();
        return null;
    }
}
