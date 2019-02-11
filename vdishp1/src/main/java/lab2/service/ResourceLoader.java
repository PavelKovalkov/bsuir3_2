package lab2.service;

import lab2.resource.Project;

import java.io.IOException;

public interface ResourceLoader {
    Project[] readProjectsFromFile() throws IOException;
}
