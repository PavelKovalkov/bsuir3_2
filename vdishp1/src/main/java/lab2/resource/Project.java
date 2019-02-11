package lab2.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project {
    @JsonProperty("project_name")
    private String projectName;
    @JsonProperty("tasks")
    private Task[] tasks;
}
