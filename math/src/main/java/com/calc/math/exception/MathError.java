package com.calc.math.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Model class for error entity
 */
@Data
@Builder
public class MathError {
    private HttpStatus status;
    private String statusCode;
    private String message;
    private Date timestamp;
}
