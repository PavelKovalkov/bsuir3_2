package lab2.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Report {
    @Id
    @JsonProperty("employee_name")
    private String employeeName;
    @JsonProperty("task_id")
    private int taskId;
    @JsonProperty("time_spent")
    private String timeSpent;
    @JsonProperty("project_name")
    private String projectName;
    @JsonProperty("date")
    private String date;
}
