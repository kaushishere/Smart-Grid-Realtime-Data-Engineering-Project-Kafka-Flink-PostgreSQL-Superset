# Real-Time Data Streaming Pipeline

This is the repo to: [Smart Grid Realtime Data Engineering Project using Kafka, Flink, PostgreSQL
](https://www.youtube.com/watch?v=Suot7_-FVPY&t=2s)

[![](./readme-assets/Thumbnail.png)](https://www.youtube.com/watch?v=Suot7_-FVPY&t=2s)

## 01 - Kafka Producer

Produce Synthetic Data for a Smart Grid using a Python script.

[Kaggle Dataset](https://www.kaggle.com/datasets/ziya07/smart-grid-real-time-load-monitoring-dataset/data)

## 02 - Broker

Setting up the Kafka Broker within a Docker container, and testing it is receiving data from our Python Container.

[How to run Kafka in Docker?](https://developer.confluent.io/confluent-tutorials/kafka-on-docker/?utm_medium=sem&utm_source=google&utm_campaign=ch.sem_br.nonbrand_tp.prs_tgt.dsa_mt.dsa_rgn.emea_sbrgn.uki_lng.eng_dv.all_con.confluent-developer&utm_term=&creative=&device=c&placement=&gad_source=1&gad_campaignid=19560855027&gbraid=0AAAAADRv2c2A8wwdkXgBvnowQAbxW6a7P&gclid=Cj0KCQiAuvTJBhCwARIsAL6Demiu7-_HAaWZ2LYY6EmM0g95z2mDAkqPGh7nEa3aQvFB-ToXifaonQMaAiqYEALw_wcB) <br>
[Kavit's repo for the wait-for-it.sh script](https://github.com/Kavit900/data-streaming-kafka-flink-postgres/tree/main/flink-processor) <br>
[Alternative to wait-for-it.sh script: service health checks](https://docs.docker.com/reference/compose-file/services/#depends_on)

## 03 - Flink Processor
### 00 - Flink Getting Started

Setting up Flink within the IntelliJ IDE using Maven's quickstart.

[Maven Quickstart](https://nightlies.apache.org/flink/flink-docs-master/docs/dev/configuration/overview/) <br>
[Anatomy of a Flink Program](https://nightlies.apache.org/flink/flink-docs-master/docs/dev/datastream/overview/) <br>
[Apache Kafka Connector](https://nightlies.apache.org/flink/flink-docs-master/docs/connectors/datastream/kafka/) <br>
[Maven Central Repository](https://mvnrepository.com/repos/central) <br> 

Courses to get you up to speed:<br>
[Java Full Course Bro Code](https://www.youtube.com/watch?v=xTtL8E4LzTQ) <br>
[Maven Full Course](https://www.youtube.com/watch?v=uAQs-YXnY-U)

### 01 - Filter and Count

Filtering out "healthy" grid events and counting grid events by fault type.

[Deserialization Schema](https://stackoverflow.com/questions/62067772/is-jsondeserializationschema-deprecated-in-flink) <br>
[Operators](https://nightlies.apache.org/flink/flink-docs-release-2.2/docs/dev/datastream/operators/overview/)

### 02 - Windowing (Count)

Applying a time window of 30 minutes to our count transformation from the last part. Windowing reduces Flink's memory requirements, by enabling Flink to forget results pertaining to a window that has elapsed. Flink only needs to keep an <b>internal state</b> of the current window.

[Windowing](https://nightlies.apache.org/flink/flink-docs-master/docs/dev/datastream/operators/windows/)
[Watermark Strategy](https://nightlies.apache.org/flink/flink-docs-master/docs/dev/datastream/event-time/generating_watermarks/) <br>
[David Anderson's Course on Apache Flink](https://developer.confluent.io/courses/apache-flink/timely-stream-processing/)

### 03 - Taking Averages

Building upon the windowing from the last section, we will calculate half-hourly wind power averages.

[Aggregate Function](https://nightlies.apache.org/flink/flink-docs-master/docs/dev/datastream/operators/windows/#aggregatefunction) <br>
[Aggregate Function In Depth](https://nightlies.apache.org/flink/flink-docs-release-1.6/api/java/index.html?org/apache/flink/api/common/functions/AggregateFunction.html)

### 04 - Averages using Classes

This section shows the <b>efficient</b> way to take multiple averages during a time window (e.g. average wind and average solar power).

[Process Window Function with Incremental Aggregation](https://nightlies.apache.org/flink/flink-docs-master/docs/dev/datastream/operators/windows/#processwindowfunction-with-incremental-aggregation)

## 04 - Postgres

Sending "half-hourly average" data to a Postgres database.

[Postgres Docker Image](https://www.docker.com/blog/how-to-use-the-postgres-docker-official-image/) <br>
[Preseeding a Postgres Database](https://docs.docker.com/guides/pre-seeding/) <br>
[Flink's JDBC Connector](https://nightlies.apache.org/flink/flink-docs-master/docs/connectors/datastream/jdbc/) <br>
[PostgreSQL JDBC Driver](https://mvnrepository.com/artifact/org.postgresql/postgresql)

## 05 - Flink in Docker

Containerising Flink so that it runs in the same Docker network as our Kafka and Postgres services.

[Docker Set-up for Flink](https://nightlies.apache.org/flink/flink-docs-master/docs/deployment/resource-providers/standalone/docker/)

## 06 - Apache Superset

Data Visualisation Time!  

[Apache Superset Quickstart](https://superset.apache.org/docs/quickstart) <br>
[Apache Superset Database Drivers](https://superset.apache.org/docs/configuration/databases/) - remember Postgres driver is built into Superset's default image so we don't have to do anything <br>


Note - make sure your dataset reads the "timestamp" column as a TIMESTAMP type and it recognises it as TEMPORAL - otherwise, Superset will not be able to make hourly/daily aggregations on your data. You can see verify this by going Superset > Datasets > edit dataset > columns tab > make sure "timestamp" is ticked for the "is temporal" parameter.

