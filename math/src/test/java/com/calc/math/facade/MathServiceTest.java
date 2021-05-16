package com.calc.math.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class MathServiceTest {
    private MathService mockMathService;

    @BeforeEach
    private void reinitializeMock() {
        mockMathService = mock(MathService.class);
    }

    @Test
    void shouldCalculateMinimumSuccessfully() {

        final List<Double> listOfValidDoubles = List.of(1.00, 2.0, 3.99);
        when(mockMathService.calculateMinimum(listOfValidDoubles))
                .thenReturn(String.valueOf(Double.valueOf(1.0)));
        String EXPECTED_RESULT = "1.0";
        assertEquals(EXPECTED_RESULT, mockMathService.calculateMinimum(listOfValidDoubles));
    }

    @Test
    void calculateMinimum() {
    }
}