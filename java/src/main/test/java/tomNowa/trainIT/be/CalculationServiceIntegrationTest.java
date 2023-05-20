package tomNowa.trainIT.be;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tomNowa.trainIT.be.exceptions.UserException;
import tomNowa.trainIT.be.model.Run;
import tomNowa.trainIT.be.model.dto.RunCalculationDto;
import tomNowa.trainIT.be.model.dto.Timerange;
import tomNowa.trainIT.be.repository.RunRepository;
import tomNowa.trainIT.be.service.CalculationService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;


@SpringBootTest
class CalculationServiceIntegrationTest extends IntegrationTestSetup {

    @Autowired
    private RunRepository repository;

    private CalculationService sut;

    private final static int VALID_USER_ID = 2;
    private final static int INVALID_USER_ID = 10;

    private final static String ERROR_MESSAGE = "Keine Läufe im angegebenen Zeitraum";

    @BeforeEach
    void setUp() {
        sut = new CalculationService(repository);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataByLastRuns")
    void testCalculationOfLastRuns(final int numberOfLastRuns, final RunCalculationDto expected) {
        final RunCalculationDto returnedCalculation = sut.calculationOfLastRuns(VALID_USER_ID, numberOfLastRuns);

        testForEquality(returnedCalculation, expected);
    }

    @Test
    void testCalculationOfLastRuns_expectException() {
        final UserException exception = assertThrows(UserException.class,
                () -> sut.calculationOfLastRuns(INVALID_USER_ID, 10));

        assertThat(exception.getMessage()).isEqualTo(ERROR_MESSAGE);
    }

    private static Stream<Arguments> provideTestDataByLastRuns() {
        return Stream.of(
                Arguments.of(2, createRunCalculationDto(2, 5.45d, 324)),
                Arguments.of(5, TestData.TWO_WEEKS),
                Arguments.of(20, TestData.LAST_YEAR)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataByTimerage")
    void testCreateCalculationByTimerange(final Timerange timerange, final RunCalculationDto expectedCalculation) {
        final RunCalculationDto returnedCalculation = sut.createCalculationByTimerange(VALID_USER_ID, timerange);

        testForEquality(returnedCalculation, expectedCalculation);
    }

    @Test
    void testCreateCalculationByTimerange_expectException() {
        final UserException exception = assertThrows(UserException.class,
                () -> sut.createCalculationByTimerange(INVALID_USER_ID, Timerange.HALF_YEAR));

        assertThat(exception.getMessage()).isEqualTo(ERROR_MESSAGE);
    }

    private static Stream<Arguments> provideTestDataByTimerage() {
        return Stream.of(
                Arguments.of(Timerange.LAST_WEEK, TestData.ONE_WEEK),
                Arguments.of(Timerange.LAST_TWO_WEEKS, TestData.TWO_WEEKS),
                Arguments.of(Timerange.LAST_MONTH, TestData.LAST_MONTH),
                Arguments.of(Timerange.LAST_QUARTER, TestData.LAST_QUARTER),
                Arguments.of(Timerange.HALF_YEAR, TestData.HALF_YEAR),
                Arguments.of(Timerange.LAST_YEAR, TestData.LAST_YEAR)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestDataByDynamicTimerage")
    void testCalculationOfIndividualTimerange(final LocalDate from, final LocalDate to, final RunCalculationDto expected) {
        final RunCalculationDto returnedCalculation = sut.calculationOfIndividualTimerange(VALID_USER_ID, from, to);

        testForEquality(returnedCalculation, expected);
    }

    private static Stream<Arguments> provideTestDataByDynamicTimerage() {
        return Stream.of(
                Arguments.of(setUpDate(0).toLocalDate(), setUpDate(7).toLocalDate(), TestData.ONE_WEEK),
                Arguments.of(setUpDate(0).toLocalDate(), setUpDate(364).toLocalDate(), TestData.LAST_YEAR)
        );
    }

    private void testForEquality(final RunCalculationDto result, final RunCalculationDto expect) {
        assertThat(result).isNotNull()
                .extracting(
                        RunCalculationDto::getTotalRuns,
                        RunCalculationDto::getAvgKm,
                        RunCalculationDto::getAvgSecondsPerKm
                ).containsExactly(
                        expect.getTotalRuns(),
                        expect.getAvgKm(),
                        expect.getAvgSecondsPerKm()
                );
    }

    private static RunCalculationDto createRunCalculationDto(final Integer totalRuns, final Double avgKm, final Integer avgSecondsKm) {
        return new RunCalculationDto().totalRuns(totalRuns).avgKm(BigDecimal.valueOf(avgKm)).avgSecondsPerKm(avgSecondsKm);
    }

    private static Date setUpDate(final int minusDays) {
        return Date.valueOf(LocalDate.now().minusDays(minusDays));
    }

    @PostConstruct
    private void setUpRuns() {
        repository.saveAndFlush(new Run(5, 2, 4.75, 1700, setUpDate(29)));
        repository.saveAndFlush(new Run(6, 2, 4.72, 1740, setUpDate(26)));
        repository.saveAndFlush(new Run(7, 2, 4.48, 1630, setUpDate(24)));
        repository.saveAndFlush(new Run(8, 2, 4.65, 1680, setUpDate(23)));
        repository.saveAndFlush(new Run(9, 2, 4.58, 1685, setUpDate(20)));
        repository.saveAndFlush(new Run(10, 2, 4.88, 1715, setUpDate(29)));
        repository.saveAndFlush(new Run(11, 2, 5.78, 1960, setUpDate(17)));
        repository.saveAndFlush(new Run(12, 3, 9.75, 3630, setUpDate(16)));
        repository.saveAndFlush(new Run(13, 3, 7.9, 2570, setUpDate(13)));
        repository.saveAndFlush(new Run(14, 2, 4.75, 1730, setUpDate(12)));
        repository.saveAndFlush(new Run(15, 3, 4.75, 1736, setUpDate(10)));
        repository.saveAndFlush(new Run(16, 2, 4.95, 1800, setUpDate(8)));
        repository.saveAndFlush(new Run(17, 2, 6.30, 1960, setUpDate(5)));
        repository.saveAndFlush(new Run(18, 2, 4.05, 1430, setUpDate(3)));
        repository.saveAndFlush(new Run(19, 2, 6.85, 2023, setUpDate(1)));
        repository.saveAndFlush(new Run(20, 2, 5.32, 1980, setUpDate(60)));
        repository.saveAndFlush(new Run(21, 2, 9.75, 3630, setUpDate(200)));
    }

    static class TestData {
        static final RunCalculationDto ONE_WEEK = createRunCalculationDto(3, 5.73d, 319);
        static final RunCalculationDto TWO_WEEKS = createRunCalculationDto(5, 5.38d, 337);
        static final RunCalculationDto LAST_MONTH = createRunCalculationDto(12, 5.06d, 349);
        static final RunCalculationDto LAST_QUARTER = createRunCalculationDto(13, 5.08d, 351);
        static final RunCalculationDto HALF_YEAR = createRunCalculationDto(13, 5.08d, 351);
        static final RunCalculationDto LAST_YEAR = createRunCalculationDto(14, 5.42d, 352);
    }
}