package tomNowa.trainIT.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tomNowa.trainIT.be.model.Run;

import java.util.Optional;

public interface RunRepository extends JpaRepository<Run,Integer> {

    @Query(value = "SELECT COUNT(run_id) FROM runs WHERE user_id = :userId GROUP BY run_id", nativeQuery = true)
    Optional<Integer> getTotalRuns(final int userId);
}
