package tomNowa.trainIT.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tomNowa.trainIT.be.model.Run;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RunRepository extends JpaRepository<Run, Integer> {

    @Query(value = "SELECT COUNT(run_id) FROM runs WHERE user_id = :userId GROUP BY user_id", nativeQuery = true)
    Optional<Integer> getTotalRuns(final int userId);

    @Query("SELECT r FROM Run r WHERE r.userId = :userId AND r.date <= :fromDate AND r.date >= :toDate")
    List<Run> runsInTimerange(final int userId, final Date fromDate, final Date toDate);

   // @Query("SELECT new tomNowa.trainIT.be.model.Run(run_id, user_id, km_number, seconds, date) " +
   //         "FROM Run WHERE user_id = :userId ORDER BY date DESC LIMIT :numberOfLastRuns")
   // List<Run> lastRuns(final int userId, final int numberOfLastRuns);
}
