package com.calc.math.facade;

import java.util.List;

/**
 * MathService
 * Implementer interface encapsulates implementation independently via the Bridge pattern
 */
public interface MathService {

    // min - given list of numbers and a quantifier (how many) provides min number(s)
    String calculateMinimum(List<Double> numbersToCalculate);

    // max - given list of numbers and a quantifier (how many) provides max number(s)
    String calculateMaximum(List<Double> numbersToCalculate);

    // avg - given list of numbers calculates their average
    String calculateAverage(List<Double> numbersToCalculate);

    // median - given list of numbers calculates their median
    String calculateMedian(List<Double> numbersToCalculate);

    // percentile - given list of numbers and quantifier 'q', compute the qth percentile of the list elements
    String calculatePercentile(List<Double> numbersToCalculate, Integer percentile);
}
