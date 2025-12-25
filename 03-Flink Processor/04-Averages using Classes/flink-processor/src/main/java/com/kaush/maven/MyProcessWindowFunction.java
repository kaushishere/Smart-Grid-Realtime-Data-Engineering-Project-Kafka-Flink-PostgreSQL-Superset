package com.kaush.maven;

import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Instant;

public class MyProcessWindowFunction
        // extends ProcessAllWindowFunction<Input, Output, Window>
        extends ProcessAllWindowFunction<HalfHourAccumulator, HalfHourAverages, TimeWindow> {

    // process(window, inputIterable, outputCollector)
    public void process(Context context,
                        Iterable<HalfHourAccumulator> accumulatorIt,
                        Collector<HalfHourAverages> out) {

        HalfHourAverages avgs = new HalfHourAverages();
        HalfHourAccumulator accumulator = accumulatorIt.iterator().next();

        avgs.avgSolar = accumulator.sumOfSolar / accumulator.count;
        avgs.avgWind = accumulator.sumOfWind / accumulator.count;
        avgs.windowStart = Instant.ofEpochMilli(context.window().getStart());

        out.collect(avgs);

    }
}