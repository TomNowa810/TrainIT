package tomNowa.trainIT.be;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tomNowa.trainIT.be.repository.RunRepository;
import tomNowa.trainIT.be.service.CalculationService;

import java.time.LocalDate;

import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CalculationServiceIntegrationTest extends IntegrationTestSetup{

    @Autowired
    private RunRepository repository;

    @Mock
    private LocalDate localDateMock;

    private CalculationService sut;

    private final static int USER_ID = 2;

    @BeforeEach
    void setUp(){
        this.sut = new CalculationService(repository, localDateMock);
        when(localDateMock.now()).thenReturn(LocalDate.of(2022,7,30));
    }

    @Test
    void testCreateCalculationByTimerange(){


    }
}
