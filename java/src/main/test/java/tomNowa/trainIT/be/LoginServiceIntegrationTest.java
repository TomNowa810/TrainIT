package tomNowa.trainIT.be;

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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tomNowa.trainIT.be.exceptions.UserCodes.*;

@SpringBootTest
@ActiveProfiles("dev")
public class LoginServiceIntegrationTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RunRepository runRepo;

    private LoginService sut;

    private final String USER_NAME = "ThomasNixdorf";
    private final String USER_PASSWORD = "password123";

    @BeforeEach
    void setUp(){
        this.sut = new LoginService(userRepo, runRepo);
    }

    @Test
    void integrationTest(){
        testCheckUser_userIsPresent();

        testCheckUser_userIsNotPresent();

        testCheckUser_userIsPresentWrongPassword();

        testCreateUser_userIsAlreadyPresent();

        testCreateUser_valid();
    }

    private void testCheckUser_userIsPresent(){
        final int userId = 2; //DB ID - 2 (USERS DOES NOT GET DELETED)

        final RunnerDto runnerDto = sut.checkUser(USER_NAME, USER_PASSWORD);
        assertThat(runnerDto).isNotNull();
        assertThat(runnerDto.getId()).isEqualTo(userId);
        assertThat(runnerDto.getName()).isEqualTo(USER_NAME);

        final int totalRuns = runRepo.getTotalRuns(userId).get();

        assertThat(runnerDto.getTotalRuns()).isEqualTo(totalRuns);
    }

    private void testCheckUser_userIsNotPresent(){
        final Exception exception = assertThrows(UserException.class, () -> sut.checkUser("INVALID", "PASSWORD"));
        assertThat(exception.getMessage()).isEqualTo(LOGIN_ERROR_USER_NOT_REGISTERED.getMessage());
    }

    private void testCheckUser_userIsPresentWrongPassword(){
        final Exception exception = assertThrows(UserException.class, () -> sut.checkUser(USER_NAME, "INVALID"));
        assertThat(exception.getMessage()).isEqualTo(LOGIN_ERROR_PASSWORD_INCORRECT.getMessage());
    }

    private void testCreateUser_valid(){
        final String userName = "UserNameDummyString";
        final String password = "DummyPassword";

        assertThat(userRepo.findByUserName(userName)).isNull();

        sut.createUser(userName, password);

        final User newUserTupleResult = userRepo.findByUserName(userName);

        assertThat(newUserTupleResult).isNotNull();
        assertThat(newUserTupleResult.getUserName()).isEqualTo(userName);
        assertThat(newUserTupleResult.getPassword()).isEqualTo(password);
        assertThat(newUserTupleResult.getId()).isNotNull();

        userRepo.delete(newUserTupleResult);
    }

    private void testCreateUser_userIsAlreadyPresent(){
        final Exception exception = assertThrows(UserException.class, () -> sut.createUser(USER_NAME, "PASSWORD"));
        assertThat(exception.getMessage()).isEqualTo(CREATION_ERROR_USER_ALREADY_EXIST.getMessage());
    }
}
