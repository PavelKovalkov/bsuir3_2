package lab2.service.build;

import lab2.db.Report;
import org.springframework.stereotype.Service;

@Service
public class ReportBuilderImpl implements ReportBuilder {
    private String employeeName;
    private int taskId;
    private String timeSpent;
    private String projectName;
    private String date;

    @Override
    public ReportBuilder withEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    @Override
    public ReportBuilder withTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }

    @Override
    public ReportBuilder withTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
        return this;
    }

    @Override
    public ReportBuilder withProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    @Override
    public ReportBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public Report build() {
        Report report = new Report();
        report.setEmployeeName(employeeName);
        report.setProjectName(projectName);
        report.setDate(date);
        report.setTaskId(taskId);
        report.setTimeSpent(timeSpent);
        return report;
    }
}
