package tomNowa.trainIT.be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomNowa.trainIT.be.api.LoginApi;
import tomNowa.trainIT.be.exceptions.UserCodes;
import tomNowa.trainIT.be.exceptions.UserException;
import tomNowa.trainIT.be.model.dto.RunnerDto;
import tomNowa.trainIT.be.service.LoginService;

import static tomNowa.trainIT.be.exceptions.UserCodes.SUCCESSFUL_USER_CREATION;


@RestController
public class LoginController implements LoginApi {

    private final LoginService loginService;

    public LoginController(final LoginService loginService){
        this.loginService = loginService;
    }

    //TODO INTEGRATE CONTROLLER ADVICE
    @Override
    public ResponseEntity<RunnerDto> checkLogin(final String userName, final String password){
        return ResponseEntity.ok().body(loginService.checkUser(userName, password));
    }

    //TODO INTEGRATE CONTROLLER ADVICE
    @Override
    public ResponseEntity<String> createUser(final String userName, final String password){
        try{
            loginService.createUser(userName,password);
            return ResponseEntity.ok().body(SUCCESSFUL_USER_CREATION.getMessage());

        } catch (final UserException userException){
            return ResponseEntity.badRequest().body(userException.getMessage());
        }
    }

}
