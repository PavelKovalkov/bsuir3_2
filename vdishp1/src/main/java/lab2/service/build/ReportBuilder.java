package lab2.service.build;

import lab2.db.Report;

public interface ReportBuilder {
    ReportBuilder withEmployeeName(String employeeName);

    ReportBuilder withTaskId(int taskId);

    ReportBuilder withTimeSpent(String timeSpent);

    ReportBuilder withProjectName(String projectName);

    ReportBuilder withDate(String date);

    Report build();
}
