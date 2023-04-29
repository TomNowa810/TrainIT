package tomNowa.trainIT.be;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tomNowa.trainIT.be.repository.RunRepository;


@SpringBootTest
public class CalculationServiceIntegrationTest extends IntegrationTestSetup{

    @Autowired
    private RunRepository repository;

    @Test
    void integrationTest(){

    }
}
