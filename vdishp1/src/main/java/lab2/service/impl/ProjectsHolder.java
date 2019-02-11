package lab2.service.impl;

import lab2.resource.Project;
import lombok.Getter;

import java.util.Date;

public class ProjectsHolder {
    @Getter
    private Project[] projects;
    private Date initTime;

    public void setProjects(Project[] projects) {
        this.projects = projects;
        initTime = new Date();
    }

    public boolean isDataActual() {
        if (projects == null) return false;
        return initTime.getTime() - System.currentTimeMillis() < 100000;
    }
}
