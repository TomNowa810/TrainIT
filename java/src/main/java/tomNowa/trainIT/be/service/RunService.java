package tomNowa.trainIT.be.service;

import org.springframework.stereotype.Service;
import tomNowa.trainIT.be.model.Run;
import tomNowa.trainIT.be.model.dto.RunDto;
import tomNowa.trainIT.be.repository.RunRepository;

import java.time.ZoneId;
import java.util.Date;

@Service
public class RunService {

    private final RunRepository repo;

    public RunService(final RunRepository repo) {
        this.repo = repo;
    }

    public void createRun(final RunDto runDto) {
        final Run newRun = map2Run(runDto);
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
}
