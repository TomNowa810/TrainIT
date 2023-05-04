package tomNowa.trainIT.be;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tomNowa.trainIT.be.model.Run;
import tomNowa.trainIT.be.model.dto.RunCalculationDto;
import tomNowa.trainIT.be.model.dto.Timerange;
import tomNowa.trainIT.be.repository.RunRepository;
import tomNowa.trainIT.be.service.CalculationService;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class CalculationServiceIntegrationTest extends IntegrationTestSetup{

    @Autowired
    private RunRepository repository;

    private CalculationService sut;

    private final static int USER_ID = 2;

    @BeforeEach
    void setUp(){
        this.sut = new CalculationService(repository);
        setUpRuns();
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void testCreateCalculationByTimerange(final Timerange timerange, final Integer totalRuns,
                                                   final Double avgKm, final Integer avgSecondsKm){

        final RunCalculationDto expectedCalculation = new RunCalculationDto();
        expectedCalculation.setTotalRuns(totalRuns);
        expectedCalculation.setAvgKm(BigDecimal.valueOf(avgKm));
        expectedCalculation.setAvgSecondsPerKm(avgSecondsKm);

        final RunCalculationDto returnedCalculation = sut.createCalculationByTimerange(USER_ID, timerange);
        System.out.println(returnedCalculation);
        assertThat(returnedCalculation).isNotNull();
        assertThat(returnedCalculation).isEqualTo(expectedCalculation);
    }

    private static Stream<Arguments> provideTestData(){
        return Stream.of(
                Arguments.of(Timerange.LAST_WEEK, 3, 5.73d, 319),
                Arguments.of(Timerange.LAST_TWO_WEEKS, 5, 5.38d, 337),
                Arguments.of(Timerange.LAST_MONTH, 12, 5.06d, 349),
                Arguments.of(Timerange.LAST_QUARTER, 13, 5.08d, 351),
                Arguments.of(Timerange.HALF_YEAR, 13, 5.08d, 351),
                Arguments.of(Timerange.LAST_YEAR, 15, 6.06d, 359)
        );
    }

    private void setUpRuns(){
        repository.saveAndFlush(new Run(5,2,4.75,1700, setUpDate(29)));
        repository.saveAndFlush(new Run(6,2,4.72,1740, setUpDate(26)));
        repository.saveAndFlush(new Run(7,2,4.48,1630, setUpDate(24)));
        repository.saveAndFlush(new Run(8,2,4.65,1680, setUpDate(23)));
        repository.saveAndFlush(new Run(9,2,4.58,1685, setUpDate(20)));
        repository.saveAndFlush(new Run(10,2,4.88,1715, setUpDate(29)));
        repository.saveAndFlush(new Run(11,2,5.78,1960, setUpDate(17)));
        repository.saveAndFlush(new Run(12,3,9.75,3630, setUpDate(16)));
        repository.saveAndFlush(new Run(13,3,7.9,2570, setUpDate(13)));
        repository.saveAndFlush(new Run(14,2,4.75,1730, setUpDate(12)));
        repository.saveAndFlush(new Run(15,3,4.75,1736, setUpDate(10)));
        repository.saveAndFlush(new Run(16,2,4.95,1800, setUpDate(8)));
        repository.saveAndFlush(new Run(17,2,6.30,1960, setUpDate(5)));
        repository.saveAndFlush(new Run(18,2,4.05,1430, setUpDate(3)));
        repository.saveAndFlush(new Run(19,2,6.85,2023, setUpDate(1)));
        repository.saveAndFlush(new Run(20,2,5.32,1980, setUpDate(60)));
        repository.saveAndFlush(new Run(21,2,9.75,3630, setUpDate(200)));
    }

    private Date setUpDate(final int minusDays){
        return Date.valueOf(LocalDate.now().minusDays(minusDays));
    }
}
