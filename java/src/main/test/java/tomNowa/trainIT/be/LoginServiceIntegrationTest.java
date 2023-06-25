package tomNowa.trainIT.be;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tomNowa.trainIT.be.exceptions.UserException;
import tomNowa.trainIT.be.model.User;
import tomNowa.trainIT.be.model.dto.RunnerDto;
import tomNowa.trainIT.be.repository.RunRepository;
import tomNowa.trainIT.be.repository.UserRepository;
import tomNowa.trainIT.be.service.LoginService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tomNowa.trainIT.be.exceptions.UserCodes.*;

@SpringBootTest
public class LoginServiceIntegrationTest extends IntegrationTestSetup {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RunRepository runRepo;

    private LoginService sut;

    private final User VALID_USER = new User(1, "runningMike", "myPassword123");
    private final int TOTAL_RUNS_OF_VALID_USER = 4;

    @BeforeEach
    void setUp() {
        sut = new LoginService(userRepo, runRepo);
    }

    @Test
    void testCheckUser_userIsPresent() {
        final RunnerDto runnerDto = sut.checkUser(VALID_USER.getUserName(), VALID_USER.getPassword());
        assertThat(runnerDto).isNotNull();
        assertThat(runnerDto.getId()).isEqualTo(1);
        assertThat(runnerDto.getName()).isEqualTo(VALID_USER.getUserName());

        assertThat(runnerDto.getTotalRuns()).isEqualTo(TOTAL_RUNS_OF_VALID_USER);
    }

    @Test
    void testCheckUser_userIsNotPresent() {
        assertNull(sut.checkUser("INVALID", "PASSWORD"));
    }

    @Test
    void testCheckUser_userIsPresentWrongPassword() {
        assertNull(sut.checkUser(VALID_USER.getUserName(), "INVALID"));
    }

    @Test
    void testCreateUser_valid() {
        final String userName = "UserNameDummyString";
        final String password = "DummyPassword";

        assertThat(userRepo.findByUserName(userName)).isNull();

        sut.createUser(userName, password);

        final User newUserTupleResult = userRepo.findByUserName(userName);

        assertThat(newUserTupleResult).isNotNull();
        assertThat(newUserTupleResult.getUserName()).isEqualTo(userName);
        assertThat(newUserTupleResult.getPassword()).isEqualTo(password);
        assertThat(newUserTupleResult.getId()).isNotNull();
        assertThat(newUserTupleResult.getId()).isEqualTo(4);

        userRepo.delete(newUserTupleResult);
    }

    @Test
    void testCreateUser_userIsAlreadyPresent() {
        final Exception exception = assertThrows(UserException.class, () -> sut.createUser(VALID_USER.getUserName(), "PASSWORD"));
        assertThat(exception.getMessage()).isEqualTo(CREATION_ERROR_USER_ALREADY_EXIST.getMessage());
    }
}
