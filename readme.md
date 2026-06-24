# Vue 3 + Vite

This template should help get you started developing with Vue 3 in Vite. The template uses Vue 3 `<script setup>` SFCs, check out the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

Learn more about IDE Support for Vue in the [Vue Docs Scaling up Guide](https://vuejs.org/guide/scaling-up/tooling.html#ide-support).


# Stock Candle Aggregation Service

## Overview

This project implements a stock market candle aggregation service using Spring Boot, Cassandra, Redis, and Vue.js.

The application ingests minute-level stock data from CSV files, stores it in Cassandra, aggregates data into different timeframes, and exposes REST APIs for retrieving OHLCV candles.

---

## Technology Stack

### Backend

* Java 17
* Spring Boot
* Spring Data Cassandra
* Redis Cache
* Lombok
* Maven

### Database

* Apache Cassandra

### Frontend

* Vue.js
* Axios
* Highcharts

---

## Features

### Data Ingestion

* CSV data loading into Cassandra
* Efficient schema design

### Candle Aggregation

Supported timeframes:

* 1m
* 5m
* 15m
* 30m
* 60m
* 1d

Aggregation fields:

* Open
* High
* Low
* Close
* Volume

### Caching

Redis caching for frequently requested candle ranges.

### Pagination

Supports page and size parameters for large result sets.

### Error Handling

* Invalid timeframe
* Invalid date range
* Missing parameters
* Symbol not found

### Frontend Client

Vue.js dashboard:

* Symbol input
* Timeframe selection
* Date range selection
* Candlestick chart visualization

---

## Cassandra Schema

```sql
CREATE TABLE stock_data (
    symbol TEXT,
    datetime TIMESTAMP,
    open DOUBLE,
    high DOUBLE,
    low DOUBLE,
    close DOUBLE,
    volume BIGINT,
    PRIMARY KEY(symbol, datetime)
);
```

Partition Key:

```text
symbol
```

Clustering Column:

```text
datetime
```

---

## Setup Instructions

### 1. Start Cassandra

```bash
cassandra
```

Create keyspace and table.

---

### 2. Start Redis

```bash
docker run -d --name redis -p 6379:6379 redis
```

Verify:

```bash
redis-cli ping
```

Expected:

```text
PONG
```

---

### 3. Configure Application

application.properties

```properties
spring.cassandra.keyspace-name=stock_keyspace
spring.cassandra.contact-points=localhost
spring.cassandra.port=9042

spring.data.redis.host=localhost
spring.data.redis.port=6379
```

---

### 4. Build Project

```bash
mvn clean install
```

---

### 5. Run Application

```bash
mvn spring-boot:run
```

---

### 6. Load CSV Data

```http
GET http://localhost:8080/api/v1/load
```

---

### 7. Fetch Aggregated Candles

```http
GET http://localhost:8080/api/v1/candles?symbol=TCS&timeFrame=5m&startDate=2026-01-01T12:00:00&endDate=2026-01-01T12:59:00
```

---

## Project Structure

```text
C:.
│   .gitattributes
│   .gitignore
│   HELP.md
│   mvnw
│   mvnw.cmd
│   pom.xml
│   readme.md
│   
├───.idea
│       .gitignore
│       compiler.xml
│       encodings.xml
│       jarRepositories.xml
│       misc.xml
│       vcs.xml
│       workspace.xml
│       
├───.mvn
│   └───wrapper
│           maven-wrapper.properties
│           
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───example
│   │   │           └───assignment
│   │   │               │   AssignmentApplication.java
│   │   │               │   
│   │   │               ├───config
│   │   │               │       RedisConfig.java
│   │   │               │       
│   │   │               ├───controller
│   │   │               │       candlestickController.java
│   │   │               │       
│   │   │               ├───dto
│   │   │               │       CandleDto.java
│   │   │               │       CandleReqDto.java
│   │   │               │       CandleResponseDto.java
│   │   │               │       PageResponseDto.java
│   │   │               │       
│   │   │               ├───entity
│   │   │               │       StockData.java
│   │   │               │       
│   │   │               ├───repo
│   │   │               │       StockDataRepository.java
│   │   │               │       
│   │   │               ├───service
│   │   │               │   │   CandleService.java
│   │   │               │   │   
│   │   │               │   └───impl
│   │   │               │           CandleServiceImpl.java
│   │   │               │           
│   │   │               └───Utility
│   │   │                       GlobalExceptionHandler.java
│   │   │                       load_data.java
│   │   │                       stock_data.csv
│   │   │                       
│   │   └───resources
│   │       │   application.properties
│   │       │   
│   │       ├───static
│   │       └───templates
│   └───test
│       └───java
│           └───com
│               └───example
│                   └───assignment
│                           AssignmentApplicationTests.java
│                           
└───target
    ├───classes
    │   │   application.properties
    │   │   
    │   └───com
    │       └───example
    │           └───assignment
    │               │   AssignmentApplication.class
    │               │   
    │               ├───config
    │               │       RedisConfig.class
    │               │       
    │               ├───controller
    │               │       candlestickController.class
    │               │       
    │               ├───dto
    │               │       CandleDto.class
    │               │       CandleReqDto.class
    │               │       CandleResponseDto.class
    │               │       PageResponseDto.class
    │               │       
    │               ├───entity
    │               │       StockData.class
    │               │       
    │               ├───repo
    │               │       StockDataRepository.class
    │               │       
    │               ├───service
    │               │   │   CandleService.class
    │               │   │   
    │               │   └───impl
    │               │           CandleServiceImpl.class
    │               │           
    │               └───Utility
    │                       GlobalExceptionHandler.class
    │                       load_data.class
    │                       
    └───generated-sources
        └───annotations
```

---

## Future Improvements

* Cassandra paging state support
* Pre-aggregated candle tables
* Kafka-based ingestion pipeline
* Docker deployment
* Kubernetes deployment
* Prometheus & Grafana monitoring

---

## Author

Pavan Chandrappa Hottigoudra
VTU Mysore
BE Computer Science & Engineering
