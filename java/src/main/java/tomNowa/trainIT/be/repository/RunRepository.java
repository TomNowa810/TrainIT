package tomNowa.trainIT.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tomNowa.trainIT.be.model.Run;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface RunRepository extends JpaRepository<Run,Integer> {

    @Query(value = "SELECT COUNT(run_id) FROM runs WHERE user_id = :userId GROUP BY user_id", nativeQuery = true)
    Optional<Integer> getTotalRuns(final int userId);

    @Query(value = "SELECT * FROM runs WHERE user_id = :userId AND date >= :fromDate AND date <= :toDate", nativeQuery = true)
    List<Run> runsInTimerange(final int userId, final Date fromDate, final Date toDate);

    @Query(value = "SELECT * FROM runs WHERE user_id = :userId ORDER BY date DESC LIMIT :numberOfLastRuns", nativeQuery = true)
    List<Run> lastRuns(final int userId, final int numberOfLastRuns);
}
