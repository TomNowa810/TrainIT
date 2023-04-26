package tomNowa.trainIT.be.controller;

import org.springframework.http.ResponseEntity;
import tomNowa.trainIT.be.api.CalculationApi;
import tomNowa.trainIT.be.model.dto.RunCalculationDto;
import tomNowa.trainIT.be.model.dto.Timerange;
import tomNowa.trainIT.be.service.CalculationService;

import java.time.LocalDate;

public class CalculationController implements CalculationApi {

    private CalculationService service;

    public CalculationController(final CalculationService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<RunCalculationDto> calculationOfTimerange(final Integer userId, final Timerange timerange) {
        return ResponseEntity.ok(service.createCalculationByTimerange(userId, timerange));
    }
}
