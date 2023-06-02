package tomNowa.trainIT.be.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import tomNowa.trainIT.be.exceptions.UserException;
import tomNowa.trainIT.be.model.Run;
import tomNowa.trainIT.be.model.dto.RunDto;
import tomNowa.trainIT.be.repository.RunRepository;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static tomNowa.trainIT.be.exceptions.UserCodes.CREATION_ERROR_RUN_ALREADY_EXIST;

@Service
public class RunService {

    private final RunRepository repo;

    public RunService(final RunRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void createRun(final RunDto runDto) {
        final Run newRun = map2Run(runDto);
        if (repo.exists(Example.of(newRun, exampleMatcher()))){
            //TODO TEST CASE
            throw new UserException(CREATION_ERROR_RUN_ALREADY_EXIST);
        }
        repo.saveAndFlush(newRun);
    }

    private Run map2Run(final RunDto runDto) {
        final Date date = Date.from(runDto.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return new Run(
                runDto.getUserId(),
                runDto.getKmNumber(),
                runDto.getSeconds(),
                date);
    }

    private ExampleMatcher exampleMatcher() {
        return ExampleMatcher.matching()
                .withIgnorePaths("runId")
                .withMatcher("userId", ignoreCase())
                .withMatcher("seconds", ignoreCase())
                .withMatcher("date", ignoreCase())
                .withMatcher("kmNumber", ignoreCase());
    }
}
