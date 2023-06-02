package tomNowa.trainIT.be.service;

import org.springframework.stereotype.Service;
import tomNowa.trainIT.be.exceptions.UserException;
import tomNowa.trainIT.be.model.User;
import tomNowa.trainIT.be.model.dto.RunnerDto;
import tomNowa.trainIT.be.repository.RunRepository;
import tomNowa.trainIT.be.repository.UserRepository;

import java.util.Optional;

import static tomNowa.trainIT.be.exceptions.UserCodes.*;

@Service
public class LoginService {

    private final UserRepository userRepo;
    private final RunRepository runRepo;

    public LoginService(final UserRepository userRepo, final RunRepository runRepo) {
        this.userRepo = userRepo;
        this.runRepo = runRepo;
    }

    public RunnerDto checkUser(final String userName, final String password) {
        final User user = userRepo.findByUserName(userName);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return mapUser2Runner(user, getUsersTotalRuns(user.getId()));
    }

    public void createUser(final String userName, final String password) {
        final User user = userRepo.findByUserName(userName);
        if (user != null) {
            throw new UserException(CREATION_ERROR_USER_ALREADY_EXIST);
        }
        final User newUser = new User(userName, password);
        userRepo.saveAndFlush(newUser);
    }

    private RunnerDto mapUser2Runner(final User user, final int totalRuns) {
        return new RunnerDto()
                .id(user.getId())
                .name(user.getUserName())
                .totalRuns(totalRuns);
    }

    private int getUsersTotalRuns(final int userId) {
        final Optional<Integer> totalRuns = runRepo.getTotalRuns(userId);
        if (totalRuns.isEmpty()) {
            return 0;
        }
        return totalRuns.get();
    }
}
