package lab2.service.impl;

import lab2.resource.Project;
import lab2.service.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ResourceLoaderProxy implements ResourceLoader {
    private ResourceLoader resourceLoader;
    private ProjectsHolder projectsHolder;

    @Autowired
    public ResourceLoaderProxy(ResourceLoader resourceLoader, ProjectsHolder projectsHolder) {
        this.resourceLoader = resourceLoader;
        this.projectsHolder = projectsHolder;
    }

    @Override
    public Project[] readProjectsFromFile() throws IOException {
        Project[] projects;
        if (!projectsHolder.isDataActual()) {
            projects = resourceLoader.readProjectsFromFile();
            projectsHolder.setProjects(projects);
        } else {
            projects = projectsHolder.getProjects();
        }
        return projects;
    }
}
