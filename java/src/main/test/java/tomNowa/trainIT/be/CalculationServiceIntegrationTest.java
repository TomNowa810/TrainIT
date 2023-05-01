package tomNowa.trainIT.be;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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


@SpringBootTest
public class CalculationServiceIntegrationTest extends IntegrationTestSetup{

    @Autowired
    private RunRepository repository;

    private CalculationService sut;

    private final static int USER_ID = 2;

    @BeforeEach
    void setUp(){
        this.sut = new CalculationService(repository);
        setUpRuns();
    }

    @Test
    void testCreateCalculationByTimerange_lastWeek(){
        final RunCalculationDto expectedCalculation = new RunCalculationDto();
        expectedCalculation.setTotalRuns(3);
        expectedCalculation.setAvgKm(BigDecimal.valueOf(5.73));
        expectedCalculation.setAvgSecondsPerKm(319);

        final RunCalculationDto returnedCalculation = sut.createCalculationByTimerange(USER_ID, Timerange.LAST_WEEK);

        System.out.println(returnedCalculation);
    }

    private void setUpRuns(){
        repository.saveAndFlush(new Run(5,2,4.75,1700, setUpDate(29)));
        repository.saveAndFlush(new Run(6,2,4.72,1740, setUpDate(26)));
        repository.saveAndFlush(new Run(7,2,4.48,1630, setUpDate(24)));
        repository.saveAndFlush(new Run(8,2,4.65,1680, setUpDate(23)));
        repository.saveAndFlush(new Run(9,2,4.58,1685, setUpDate(20)));
        repository.saveAndFlush(new Run(10,2,5.78,1960, setUpDate(19)));
        repository.saveAndFlush(new Run(10,3,9.75,3630, setUpDate(16)));
        repository.saveAndFlush(new Run(10,3,7.9,2570, setUpDate(13)));
        repository.saveAndFlush(new Run(10,2,4.75,1730, setUpDate(12)));
        repository.saveAndFlush(new Run(10,3,4.75,1736, setUpDate(10)));
        repository.saveAndFlush(new Run(10,2,4.95,1800, setUpDate(8)));
        repository.saveAndFlush(new Run(10,2,6.30,1960, setUpDate(5)));
        repository.saveAndFlush(new Run(10,2,4.05,1430, setUpDate(3)));
        repository.saveAndFlush(new Run(10,2,6.85,2023, setUpDate(1)));
    }

    private Date setUpDate(final int minusDays){
        return Date.valueOf(LocalDate.now().minusDays(minusDays));
    }
}
