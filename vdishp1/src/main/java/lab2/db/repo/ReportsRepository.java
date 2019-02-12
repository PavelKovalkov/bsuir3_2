package lab2.db.repo;

import lab2.db.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportsRepository extends CrudRepository<Report, Integer> {
    Iterable<Report> findAllByTaskId(int taskId);

    Iterable<Report> findAllByProjectNameAndTaskId(String projectName, int taskId);
}
