package tomNowa.trainIT.be;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("tc")
@Testcontainers
@SpringBootTest
@ConfigurationProperties
public class CalculationServiceIntegrationTest {


}
