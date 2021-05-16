package com.calc.math.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.calc.math.exception.MathExceptionHandler.buildBadRequestResponseEntity;

/**
 * Business Logic validation specific to the app domain
 */
@Slf4j
public class MathValidator {

    /**
     * Param validation as resources are essentially just parameters & non-CRUD functionality in scope of assignment
     * @param numbers param to check
     * @param amountOfElements amount of expected elements
     * @param messageToUser message tailored for specific error to user
     * @return
     */
    public static ResponseEntity<String> errorResponseIfInputParamsAreInvalid(List<Double> numbers, int amountOfElements, String messageToUser) {
        ResponseEntity<String> optionalResponseEntity = null;

        if (invalidQuantityOfElementsInListRequestParams(numbers, amountOfElements)) {
            log.warn("Unequal size", numbers, amountOfElements);
            optionalResponseEntity = buildBadRequestResponseEntity(messageToUser);
        }
        return optionalResponseEntity;
    }

    public static boolean invalidQuantityOfElementsInListRequestParams(List<Double> inputElements, int inputAmountOfElements) {
        return inputElements.size() != inputAmountOfElements;
    }
}
