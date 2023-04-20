package tomNowa.trainIT.be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomNowa.trainIT.be.api.LoginApi;
import tomNowa.trainIT.be.model.dto.RunnerDto;
import tomNowa.trainIT.be.service.LoginService;


@RestController
public class LoginController implements LoginApi {

    private final LoginService loginService;

    public LoginController(final LoginService loginService){
        this.loginService = loginService;
    }

    @Override
    public ResponseEntity<RunnerDto> checkLogin(final String userName, final String password){
        return ResponseEntity.ok().body(loginService.checkUser(userName, password));
    }
}
