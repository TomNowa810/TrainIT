package tomNowa.trainIT.be.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import tomNowa.trainIT.be.model.Run;
import tomNowa.trainIT.be.model.dto.RunCalculationDto;
import tomNowa.trainIT.be.model.dto.Timerange;
import tomNowa.trainIT.be.repository.RunRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CalculationService {

    private RunRepository repo;


    public CalculationService(final RunRepository repo) {
        this.repo = repo;
    }

    public RunCalculationDto createCalculationByTimerange(final int userId, final Timerange timerange){
        final Pair<Date, Date> validTimerange = createValidTimerange(timerange);

        final List<Run> runsInTimerange =
                repo.runsInTimerange(userId, validTimerange.getFirst(), validTimerange.getSecond());

        return mapList2CalculationDto(runsInTimerange, timerange);
    }

    private RunCalculationDto mapList2CalculationDto(final List<Run> runs, final Timerange timerange){
        final RunCalculationDto dto = new RunCalculationDto();
        dto.setTotalRuns(runs.size());
        dto.setDateTimeRange(timerange.toString());

        final Pair<Double, Integer> pair = calculateAvgs(runs);

        dto.setAvgKm(BigDecimal.valueOf(pair.getFirst()));
        dto.setAvgSecondsPerKm(pair.getSecond() );
        return dto;
    }

    private Pair<Double,Integer> calculateAvgs(final List<Run> runs){
        int secondsSum = 0;
        double kmSum = 0;

        for (final Run run : runs){
            secondsSum = secondsSum + run.getSeconds();
            kmSum = kmSum + run.getKmNumber();
        }

        final double kmAvg = kmSum / runs.size();
        final int secondsAvgForKm = secondsSum / runs.size();

        return Pair.of(kmAvg, secondsAvgForKm);
    }

    private Pair<Date, Date> createValidTimerange(final Timerange timerange){
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
