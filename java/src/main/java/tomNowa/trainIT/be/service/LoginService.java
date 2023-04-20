package tomNowa.trainIT.be.service;

import org.springframework.stereotype.Service;
import tomNowa.trainIT.be.model.User;
import tomNowa.trainIT.be.model.dto.RunnerDto;
import tomNowa.trainIT.be.repository.UserRepository;

@Service
public class LoginService {

    private final UserRepository userRepo;

    public LoginService(final UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public RunnerDto checkUser(final String userName, final String password){
        final User user = userRepo.findByUserName(userName);

        if (user == null){
            return new RunnerDto();
        }
        return new RunnerDto().name(user.getUserName());
    }
}
