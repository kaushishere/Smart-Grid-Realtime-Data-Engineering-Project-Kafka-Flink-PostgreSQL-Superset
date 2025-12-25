package com.kaush.maven;

import org.apache.flink.api.common.functions.AggregateFunction;

public class Accumulator
        //         AggregateFunction<input, accumulator, result>
        implements AggregateFunction<Reading, HalfHourAccumulator, HalfHourAccumulator> {
    @Override
    public HalfHourAccumulator createAccumulator() {
        return new HalfHourAccumulator();
    }

    @Override
    public HalfHourAccumulator add(Reading reading, HalfHourAccumulator accumulator) {
        accumulator.sumOfWind += reading.wind_kw;
        accumulator.sumOfSolar += reading.solar_kw;
        accumulator.count += 1L;
        return accumulator;
    }

    @Override
    public HalfHourAccumulator getResult(HalfHourAccumulator accumulator){
        return accumulator;
    }

    @Override
    public HalfHourAccumulator merge(HalfHourAccumulator a, HalfHourAccumulator b) {
        a.sumOfWind += b.sumOfWind;
        a.sumOfSolar += b.sumOfSolar;
        a.count += b.count;
        return a;
    }
}