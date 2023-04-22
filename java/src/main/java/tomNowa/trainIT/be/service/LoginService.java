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

    //TODO correct implementation
    public RunnerDto checkUser(final String userName, final String password){
        final User user = userRepo.findByUserName(userName);
        if (user == null){
            return new RunnerDto();
        }
        return new RunnerDto().name(user.getUserName());
    }

    public void createUser(final String userName, final String password){
        final User user = userRepo.findByUserName(userName);
        if (user != null){
            //TODO create Exception
            throw new IllegalArgumentException();
        }
        final User newUser = new User(userName, password);
        userRepo.saveAndFlush(newUser);
    }
}
