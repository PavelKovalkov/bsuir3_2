package lab2.controller;

import lab2.db.Report;
import lab2.db.repo.ReportsRepository;
import lab2.resource.Project;
import lab2.resource.Task;
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
import java.util.Arrays;
import java.util.Set;

@RestController
public class ProjectController {
    private final Set<String> employees;
    private final Set<String> readWriteAccessEmployees;
    private final ResourceLoader resourceLoader;
    private final ProjectsHolder projectsHolder;
    private final ReportsRepository repository;

    @Autowired
    public ProjectController(Set<String> readWriteAccessEmployees,
                             Set<String> employees,
                             ResourceLoader resourceLoader,
                             ProjectsHolder projectsHolder, ReportsRepository repository) {
        this.readWriteAccessEmployees = readWriteAccessEmployees;
        this.employees = employees;
        this.resourceLoader = resourceLoader;
        this.projectsHolder = projectsHolder;
        this.repository = repository;
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

    @PostMapping("/report")
    public ResponseEntity<Object> addRecord(@RequestBody Report report) throws IOException {
        Middleware middleware = new UserExistsMiddleware(employees)
            .linkWith(new ReadWriteAccessesMiddleware(readWriteAccessEmployees));

        if (!middleware.check(report.getEmployeeName())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Project[] projects = resourceLoader.readProjectsFromFile();
        Task[] tasks = Arrays.stream(projects).filter(project -> project.getProjectName().equalsIgnoreCase(report.getProjectName())).findFirst().orElseThrow(() -> {
            throw new RuntimeException("Incorrect project name");
        }).getTasks();
        Task currentTask = Arrays.stream(tasks).filter(task -> task.getId() == report.getTaskId()).findFirst().orElseThrow(() -> {
            throw new RuntimeException("Incorrect project's task name");
        });
        Iterable<Report> allByTaskId = repository.findAllByTaskId(report.getTaskId());
        double time = 0;
        for (Report rp : allByTaskId) {
            String[] timeArray = rp.getTimeSpent().split(":");
            time += Integer.valueOf(timeArray[0].substring(0, timeArray[0].length() - 1)) * 24 * 60
                + Integer.valueOf(timeArray[1].substring(0, timeArray[1].length() - 1)) * 60
                + Integer.valueOf(timeArray[2].substring(0, timeArray[2].length() - 1));
        }
        String[] estimateArray = currentTask.getEstimate().split(":");
        double estimate = Integer.valueOf(estimateArray[0].substring(0, estimateArray[0].length() - 1)) * 24 * 60
            + Integer.valueOf(estimateArray[1].substring(0, estimateArray[1].length() - 1)) * 60
            + Integer.valueOf(estimateArray[2].substring(0, estimateArray[2].length() - 1));

        String[] spentTimeArray = report.getTimeSpent().split(":");
        double spentTime = Integer.valueOf(spentTimeArray[0].substring(0, spentTimeArray[0].length() - 1)) * 24 * 60
            + Integer.valueOf(spentTimeArray[1].substring(0, spentTimeArray[1].length() - 1)) * 60
            + Integer.valueOf(spentTimeArray[2].substring(0, spentTimeArray[2].length() - 1));
        if (spentTime + time > estimate) {
            return new ResponseEntity<>("Time spent exceeded", HttpStatus.BAD_REQUEST);
        }

        repository.save(report);

        return new ResponseEntity<>("Work was successfully logged", HttpStatus.OK);
    }

    @GetMapping("/get-task-time-left")
    public ResponseEntity<Object> getTaskTimeLeft(@RequestBody String body) throws IOException {
        JSONObject object = new JSONObject(body);
        Middleware middleware = new UserExistsMiddleware(employees);

        if (!middleware.check(object.getString("employee_name"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Project[] projects = resourceLoader.readProjectsFromFile();
        Task[] tasks = Arrays.stream(projects).filter(project -> project.getProjectName().equalsIgnoreCase(object.getString("project_name"))).findFirst().orElseThrow(() -> {
            throw new RuntimeException("Incorrect project name");
        }).getTasks();
        Task currentTask = Arrays.stream(tasks).filter(task -> task.getId() == object.getInt("task_id")).findFirst().orElseThrow(() -> {
            throw new RuntimeException("Incorrect project's task name");
        });

        Iterable<Report> reports = repository.findAllByProjectNameAndTaskId(object.getString("project_name"), object.getInt("task_id"));
        double time = 0;
        for (Report rp : reports) {
            String[] timeArray = rp.getTimeSpent().split(":");
            time += Integer.valueOf(timeArray[0].substring(0, timeArray[0].length() - 1)) * 24 * 60
                + Integer.valueOf(timeArray[1].substring(0, timeArray[1].length() - 1)) * 60
                + Integer.valueOf(timeArray[2].substring(0, timeArray[2].length() - 1));
        }
        String[] estimateArray = currentTask.getEstimate().split(":");
        double estimate = Integer.valueOf(estimateArray[0].substring(0, estimateArray[0].length() - 1)) * 24 * 60
            + Integer.valueOf(estimateArray[1].substring(0, estimateArray[1].length() - 1)) * 60
            + Integer.valueOf(estimateArray[2].substring(0, estimateArray[2].length() - 1));

        double timeLeft = estimate-time;
        return new ResponseEntity<>("Time remaining: "+timeLeft+" min", HttpStatus.OK);
    }
}
