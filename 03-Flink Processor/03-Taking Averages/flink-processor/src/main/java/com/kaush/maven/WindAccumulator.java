package com.kaush.maven;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;


public class WindAccumulator
        //         AggregateFunction<input, accumulator, result>
        implements AggregateFunction<Reading, Tuple2<Double, Long>, Double> {
    @Override
    // createAccumulator() -> accumulator
    public Tuple2<Double, Long> createAccumulator() {
        // (sumOfWind, count)
        return new Tuple2<>(0.0, 0L);
    }

    @Override
    // add(input, accumulator) -> accumulator
    public Tuple2<Double, Long> add(Reading newReading, Tuple2<Double, Long> accumulator) {
        return new Tuple2<>(accumulator.f0 + newReading.wind_kw, accumulator.f1 + 1L);
    }

    @Override
    // getResult(totalAccumulator) -> result
    public Double getResult(Tuple2<Double, Long> accumulator) {
        return (accumulator.f0 / accumulator.f1);
    }

    @Override
    // merge(subAccumulator, subAccumulator) -> totalAccumulator
    public Tuple2<Double, Long> merge(Tuple2<Double, Long> a, Tuple2<Double, Long> b) {
        return new Tuple2<>(a.f0 + b.f0, a.f1 + b.f1);
    }
}