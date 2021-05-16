package com.calc.math.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Date;

/**
 * Class which handles exceptions, handlers generally work as part of a chain of responsibility pattern
 */
@Slf4j
public class MathExceptionHandler {

    /**
     * Catch all checked exceptions and respond with just a bad request
     * <p>
     * Best practice would be to catch exception subtypes instead of an Internal Server Error
     *
     * @param exception exception caught within the application at runtime
     * @param request   web request at time the checked exception is thrown
     * @return Response error to user
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleAllExceptions(Exception exception, WebRequest request) {

        log.error(request.toString(), exception);
//        return buildErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return buildErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Function to build (via Builder Pattern) a standardised error message for the application
     *
     * @param errorMessage Message given to user/client
     * @param httpStatus   Http Status code of response
     * @return a custom ResponseEntity tailored for this app
     */
    private ResponseEntity<MathError> buildErrorResponse(String errorMessage, HttpStatus httpStatus) {

        // final, local variable to convey developer's intent
        final MathError errorResponse = MathError.builder()
                .status(httpStatus)
                .message(errorMessage)
                .timestamp(Date.from(Instant.now()))
                .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    public static ResponseEntity<String> buildBadRequestResponseEntity(String messageToUser) {
        return ResponseEntity.badRequest()
                .body(messageToUser);
    }
}


