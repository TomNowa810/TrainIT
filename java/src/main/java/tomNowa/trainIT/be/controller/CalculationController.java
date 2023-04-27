package tomNowa.trainIT.be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tomNowa.trainIT.be.api.CalculationApi;
import tomNowa.trainIT.be.model.dto.RunCalculationDto;
import tomNowa.trainIT.be.model.dto.Timerange;
import tomNowa.trainIT.be.service.CalculationService;

import java.time.LocalDate;

@RestController
public class CalculationController implements CalculationApi {

    private CalculationService service;

    public CalculationController(final CalculationService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<RunCalculationDto> calculationOfIndividualTimerange(final Integer userId, final LocalDate fromDate, final LocalDate toDate) {
        return CalculationApi.super.calculationOfIndividualTimerange(userId, fromDate, toDate);
    }

    @Override
    public ResponseEntity<RunCalculationDto> calculationOfLastRuns(final Integer userId, final Integer lastRuns) {
        return CalculationApi.super.calculationOfLastRuns(userId, lastRuns);
    }

    @Override
    public ResponseEntity<RunCalculationDto> calculationOfTimerange(final Integer userId, final Timerange timerange) {
        return ResponseEntity.ok(service.createCalculationByTimerange(userId, timerange));
    }
}
