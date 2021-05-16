package com.calc.math.facade;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.math.Quantiles.median;
import static com.google.common.math.Quantiles.percentiles;

/**
 * Implementation of the service interface
 */
@Service
public class MathServiceImpl implements MathService {

    @Override
    public String calculateMinimum(List<Double> numbersToCalculate) {

        // stream through list with lambda expression and necessary intermediate and terminal operations
        double calculatedAverage = numbersToCalculate.stream()
                .mapToDouble(d -> d)
                .min().getAsDouble();

        return String.valueOf(calculatedAverage);
    }

    @Override
    public String calculateMaximum(List<Double> numbersToCalculate) {
        return String.valueOf(numbersToCalculate.stream()
                .mapToDouble(d -> d)
                .max().getAsDouble());
    }

    @Override
    public String calculateAverage(List<Double> numbersToCalculate) {
        return String.valueOf(numbersToCalculate.stream()
                .mapToDouble(d -> d)
                .average().getAsDouble());
    }

    @Override
    public String calculateMedian(List<Double> numbersToCalculate) {
        return String.valueOf(median().compute(numbersToCalculate));
    }

    @Override
    public String calculatePercentile(List<Double> numbersToCalculate, Integer k) {
        return String.valueOf(percentiles().index(k).compute(numbersToCalculate));
    }
}
