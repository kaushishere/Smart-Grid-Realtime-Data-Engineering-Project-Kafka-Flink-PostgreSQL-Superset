/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kaush.maven;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;

/**
 * Skeleton for a Flink DataStream Job.
 *
 * <p>For a tutorial how to write a Flink application, check the
 * tutorials and examples on the <a href="https://flink.apache.org">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class DataStreamJob {

	public static void main(String[] args) throws Exception {
		// Sets up the execution environment, which is the main entry point
		// to building Flink applications.
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().disableClosureCleaner();

        KafkaSource<Reading> source = KafkaSource.<Reading>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("smartgrid")
                .setGroupId("my-group")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new ReadingDeserialization())
                .build();

        WatermarkStrategy<Reading> watermarkStrategy= WatermarkStrategy
                .<Reading>forMonotonousTimestamps()
                .withTimestampAssigner((event, timestamp) -> event.timestamp * 1000)
                .withIdleness(Duration.ofSeconds(5));

         DataStreamSource<Reading> readingStream = env.fromSource(source, watermarkStrategy, "Kafka Source");


         // half-hourly average wind power
        DataStream<Double> avgWind = readingStream
                .windowAll(TumblingEventTimeWindows.of(Time.minutes(30)))
                        .aggregate(new WindAccumulator());
        avgWind.print();

        // half-hourly average wind+solar power (INEFFICIENT WAY)
//        DataStream<Tuple2<Double, Double>> avgs = readingStream
//                .windowAll(TumblingEventTimeWindows.of(Time.minutes(30)))
//                .aggregate(new Accumlator());
//        avgs.print();















         env.execute("Testing Flink");
	}

    public static boolean filterOutHealthyGridEvents(Reading r){
        return !r.fault_indicator.equals("normal");
    }

    public static Tuple2<String, Integer> countByFault(Reading r){
        return Tuple2.of(r.fault_indicator, 1);
    }
}
