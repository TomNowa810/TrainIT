package tomNowa.trainIT.be.controller;

import org.springframework.http.ResponseEntity;
import tomNowa.trainIT.be.api.CalculationApi;
import tomNowa.trainIT.be.model.dto.RunCalculationDto;

import java.time.LocalDate;

public class CalculationController implements CalculationApi {


    @Override
    public ResponseEntity<RunCalculationDto> calculationOfMonth(final Integer userId, final LocalDate date) {
        return ResponseEntity.ok().build();
    }
}
