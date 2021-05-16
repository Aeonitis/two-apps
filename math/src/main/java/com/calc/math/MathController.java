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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

import static com.calc.math.validation.MathValidator.errorResponseIfInputParamsAreInvalid;
import static com.calc.math.validation.MathValidator.invalidQuantityOfElementsInListRequestParams;

@Slf4j
@RestController
public class MathController {

    private final String MESSAGE_INVALID_LIST_PARAMS = "The quantity of elements are unequal to amount of elements in the list";

    // Dependency Injection, loosely coupled & abstracted dependent class
    @Autowired
    private MathService mathService;

    @GetMapping("/min")
    public ResponseEntity<String> calculateMinimum(@RequestParam(name = "numbers") @Size(min = 1, max = 10)
                                                           List<Double> numbers,
                                                   @RequestParam(name = "n") @Min(1) @Max(10) Integer amountOfElements) {

        log.info("Numbers to calculate: " + numbers.size() + " N: " + amountOfElements);

        if (invalidQuantityOfElementsInListRequestParams(numbers, amountOfElements)) {
            return errorResponseIfInputParamsAreInvalid(numbers, amountOfElements, MESSAGE_INVALID_LIST_PARAMS);
        }

        Optional<String> calculationResponse = Optional.of(String.valueOf(
                mathService.calculateMinimum(numbers)));

        return mappedResponseEntity(calculationResponse);
    }

    private ResponseEntity<String> mappedResponseEntity(Optional<String> calculationResponse) {
        ResponseEntity<String> responseEntity = calculationResponse.map(result -> ResponseEntity.ok().body(result))
                .orElse(ResponseEntity.notFound().build());
        log.info("Result: " + responseEntity);
        return responseEntity;
    }

    /**
     * Instruction: given list of numbers and a quantifier (how many) provides max number(s)
     *
     * @param numbers          numbers to be calculated
     * @param amountOfElements amount of numbers (N)
     * @return Result as a text string with the maximum value
     */
    @GetMapping("/max")
    public ResponseEntity<String> calculateMaximum(@RequestParam(name = "numbers") @Size(min = 1, max = 10)
                                                   @NotEmpty(message = "Blank input entered for 'numbers' parameter.")
                                                           List<Double> numbers,
                                                   @RequestParam(name = "n") @Min(1) @Max(10) Integer amountOfElements) {

        log.info("Numbers to calculate: " + numbers.size() + " N: " + amountOfElements);

        if (invalidQuantityOfElementsInListRequestParams(numbers, amountOfElements)) {
            return errorResponseIfInputParamsAreInvalid(numbers, amountOfElements, MESSAGE_INVALID_LIST_PARAMS);
        }

        Optional<String> calculationResponse = Optional.of(String.valueOf(
                mathService.calculateMaximum(numbers)));

        return mappedResponseEntity(calculationResponse);
    }


    @GetMapping("/avg")
    public ResponseEntity<String> calculateAverage(@RequestParam(name = "numbers") @Size(min = 1, max = 10)
                                                   @NotEmpty(message = "Blank input entered for 'numbers' parameter.")
                                                           List<Double> numbers,
                                                   @RequestParam(name = "n") @Min(1) @Max(10) Integer amountOfElements) {

        log.info("Numbers to calculate: " + numbers.size() + " N: " + amountOfElements);

        if (invalidQuantityOfElementsInListRequestParams(numbers, amountOfElements)) {
            return errorResponseIfInputParamsAreInvalid(numbers, amountOfElements, MESSAGE_INVALID_LIST_PARAMS);
        }

        Optional<String> calculationResponse = Optional.of(String.valueOf(
                mathService.calculateAverage(numbers)));

        return mappedResponseEntity(calculationResponse);
    }


    @GetMapping("/median")
    public ResponseEntity<String> calculateMedian(@RequestParam(name = "numbers") @Size(min = 1, max = 10)
                                                  @NotEmpty(message = "Blank input entered for 'numbers' parameter.")
                                                          List<Double> numbers) {
        log.info("Numbers to calculate for Median: " + numbers.size());

        Optional<String> calculationResponse = Optional.of(String.valueOf(
                mathService.calculateMedian(numbers)));

        return mappedResponseEntity(calculationResponse);
    }

    @GetMapping("/percentile")
    public ResponseEntity<String> calculatePercentile(@RequestParam(name = "numbers") @Size(min = 1, max = 10)
                                                      @NotEmpty(message = "Blank input entered for 'numbers' parameter.")
                                                              List<Double> numbers,
                                                      @RequestParam(name = "k") @Min(0) @Max(100) Integer percentile) {

        log.info("Numbers to calculate: " + numbers.size() + " K: " + percentile);

        Optional<String> calculationResponse = Optional.of(String.valueOf(
                mathService.calculatePercentile(numbers, percentile)));

        return mappedResponseEntity(calculationResponse);
    }

}
