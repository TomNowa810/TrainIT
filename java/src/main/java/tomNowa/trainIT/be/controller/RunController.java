package tomNowa.trainIT.be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomNowa.trainIT.be.api.RunApi;
import tomNowa.trainIT.be.model.dto.RunDto;
import tomNowa.trainIT.be.service.RunService;

@RestController
public class RunController implements RunApi {

    private final RunService service;

    public RunController(final RunService runService) {
        this.service = runService;
    }

    @Override
    public ResponseEntity<Void> createRun(final RunDto runDto) {
        service.createRun(runDto);

        return ResponseEntity.ok().build();
    }
}
