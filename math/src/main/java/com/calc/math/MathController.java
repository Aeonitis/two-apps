package com.calc.math;

import com.calc.math.facade.MathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class MathController {

    // Dependency Injection, loosely coupled & abstracted dependent class
    @Autowired
    private MathService mathService;

    @GetMapping("/min")
    public ResponseEntity<String> calculateMinimum(@RequestParam(name = "numbers") @Size(min = 1, max = 10)
                                                           List<Double> numbers,
                                                   @RequestParam(name = "n") @Min(1) @Max(10) Integer amountOfElements) {
        Optional<String> calculationResponse = Optional.of(String.valueOf(
                mathService.calculateMinimum(numbers)));

        log.info("Numbers to calculate: " + numbers + " N: " + amountOfElements);

        return calculationResponse.map(result -> ResponseEntity.ok().body(result))
                .orElse(ResponseEntity.notFound().build());
    }

}
