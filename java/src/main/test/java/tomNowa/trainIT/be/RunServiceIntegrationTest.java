package tomNowa.trainIT.be;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tomNowa.trainIT.be.model.Run;
import tomNowa.trainIT.be.model.dto.RunDto;
import tomNowa.trainIT.be.repository.RunRepository;
import tomNowa.trainIT.be.service.RunService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RunServiceIntegrationTest extends IntegrationTestSetup{

    @Autowired
    private RunRepository repo;

    private RunService sut;

    // Runs always refer to only one User
    private final int USER_ID = 1;

    @BeforeEach
    void setup() {
        this.sut = new RunService(repo);
    }

    @Test
    @Transactional // To avoid Session-Exceptions
    void integrationTest() {
        final RunDto runInputEntity = createEntity();

        final int numberOfRuns_beforeCall = repo.getTotalRuns(USER_ID).get();
        assertThat(numberOfRuns_beforeCall).isEqualTo(3);

        sut.createRun(runInputEntity);

        final int numberOfRuns_afterCall = repo.getTotalRuns(USER_ID).get();

        assertThat(numberOfRuns_afterCall).isEqualTo(numberOfRuns_beforeCall + 1);

        final Run runResultEntity = getLatestInsertedRun();

        assertThat(runResultEntity).isNotNull();
        assertThat(runResultEntity.getUserId()).isEqualTo(USER_ID);
        assertThat(runResultEntity.getSeconds()).isEqualTo(runInputEntity.getSeconds());
        assertThat(runResultEntity.getKmNumber())
                .isEqualTo(Double.valueOf(runInputEntity.getKmNumber().toString()));
        assertThat(runResultEntity.getDate())
                .isEqualTo(Date.from(runInputEntity.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    private Run getLatestInsertedRun(){
        final List<Run> runs = repo.findAll();
        return runs.get(runs.size() - 1);
    }

    private RunDto createEntity(){
        final RunDto runDto = new RunDto();
        runDto.userId(USER_ID);
        runDto.kmNumber(10);
        runDto.seconds(111111);
        runDto.date(LocalDate.of(2022,6,30));

        return runDto;
    }
}