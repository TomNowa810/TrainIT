package tomNowa.trainIT.be.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import tomNowa.trainIT.be.exceptions.UserException;
import tomNowa.trainIT.be.model.Run;
import tomNowa.trainIT.be.model.dto.RunCalculationDto;
import tomNowa.trainIT.be.model.dto.Timerange;
import tomNowa.trainIT.be.repository.RunRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static tomNowa.trainIT.be.exceptions.UserCodes.USER_HAS_NO_RUNS_IN_TIMERANGE;

@Service
public class CalculationService {

    private final RunRepository repo;

    public CalculationService(final RunRepository repo) {
        this.repo = repo;
    }

    public RunCalculationDto createCalculationByTimerange(final int userId, final Timerange timerange) {
        final Pair<Date, Date> validTimerange = createValidTimerange(timerange);

        final List<Run> runsInTimerange =
                repo.runsInTimerange(userId, validTimerange.getFirst(), validTimerange.getSecond());

        if (runsInTimerange.isEmpty()) {
            throw new UserException(USER_HAS_NO_RUNS_IN_TIMERANGE);
        }

        return mapList2CalculationDto(runsInTimerange);
    }

    public RunCalculationDto calculationOfIndividualTimerange(final int userId, final LocalDate from, final LocalDate to) {
        final List<Run> runsInTimerange = repo.runsInTimerange(userId, Date.valueOf(from), Date.valueOf(to));

        if (runsInTimerange.isEmpty()) {
            throw new UserException(USER_HAS_NO_RUNS_IN_TIMERANGE);
        }

        return mapList2CalculationDto(runsInTimerange);
    }

    public RunCalculationDto calculationOfLastRuns(final int userId, final int numberOfLastRuns) {
        final List<Run> runsInTimerange = repo.lastRuns(userId, numberOfLastRuns);

        if (runsInTimerange.isEmpty()) {
            throw new UserException(USER_HAS_NO_RUNS_IN_TIMERANGE);
        }

        return mapList2CalculationDto(runsInTimerange);
    }

    private RunCalculationDto mapList2CalculationDto(final List<Run> runs) {
        final RunCalculationDto dto = new RunCalculationDto();
        dto.setTotalRuns(runs.size());

        final Pair<Double, Integer> pair = calculateAvgs(runs);

        dto.setAvgKm(BigDecimal.valueOf(pair.getFirst()));
        dto.setAvgSecondsPerKm(pair.getSecond());
        return dto;
    }

    //TODO implement and calculate progress-seconds
    private Pair<Double, Integer> calculateAvgs(final List<Run> runs) {
        double secondsAvg = 0;
        double kmSum = 0;

        for (final Run run : runs) {
            secondsAvg = secondsAvg + run.getSeconds() / run.getKmNumber();
            kmSum = kmSum + run.getKmNumber();
        }

        final double kmAvg = kmSum / runs.size();
        final double kmAvgRounded = Math.round(100.0 * kmAvg) / 100.0;

        final int secondsAvgForKm = (int) secondsAvg / runs.size();

        return Pair.of(kmAvgRounded, secondsAvgForKm);
    }

    private Pair<Date, Date> createValidTimerange(final Timerange timerange) {
        final Date fromDate = Date.valueOf(LocalDate.now());

        return switch (timerange) {
            case LAST_WEEK -> Pair.of(fromDate,
                    Date.valueOf(LocalDate.now().minusWeeks(1)));

            case LAST_TWO_WEEKS -> Pair.of(fromDate,
                    Date.valueOf(LocalDate.now().minusWeeks(2)));

            case LAST_MONTH -> Pair.of(fromDate,
                    Date.valueOf(LocalDate.now().minusMonths(1)));

            case LAST_QUARTER -> Pair.of(fromDate,
                    Date.valueOf(LocalDate.now().minusMonths(4)));

            case HALF_YEAR -> Pair.of(fromDate,
                    Date.valueOf(LocalDate.now().minusMonths(6)));

            case LAST_YEAR -> Pair.of(fromDate,
                    Date.valueOf(LocalDate.now().minusYears(1)));
        };
    }
}
