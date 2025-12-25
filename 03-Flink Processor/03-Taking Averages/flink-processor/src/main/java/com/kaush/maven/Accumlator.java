package com.kaush.maven;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;


public class Accumlator
        //         AggregateFunction<input, accumulator, result>
        implements AggregateFunction<Reading, Tuple3<Double, Double, Long>, Tuple2<Double, Double>> {
    @Override
    // createAccumulator() -> accumulator
    public Tuple3<Double, Double, Long> createAccumulator() {
        // (sumOfWind, sumOfSolar, count)
        return new Tuple3<>(0.0, 0.0, 0L);
    }

    @Override
    // add(input, accumulator) -> accumulator
    public Tuple3<Double, Double, Long> add(Reading newReading, Tuple3<Double, Double, Long> accumulator) {
        return new Tuple3<>(accumulator.f0 + newReading.wind_kw, accumulator.f1 + newReading.solar_kw, accumulator.f2 + 1L);
    }

    @Override
    // getResult(totalAccumulator) -> result
    public Tuple2<Double, Double> getResult(Tuple3<Double, Double, Long> accumulator) {
        return new Tuple2<>(accumulator.f0 / accumulator.f2, accumulator.f1 / accumulator.f2);
    }

    @Override
    // merge(subAccumulator, subAccumulator) -> totalAccumulator
    public Tuple3<Double, Double, Long> merge(Tuple3<Double, Double, Long> a, Tuple3<Double, Double, Long> b) {
        return new Tuple3<>(a.f0 + b.f0, a.f1 + b.f1, a.f2 + b.f2);
    }
}