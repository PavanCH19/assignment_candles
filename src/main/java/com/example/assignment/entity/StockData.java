package com.example.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("stock_data")
public class StockData {

    @PrimaryKeyColumn(
            name = "symbol",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED
    )
    private String symbol;

    @PrimaryKeyColumn(
            name = "datetime",
            ordinal = 1,
            type = PrimaryKeyType.CLUSTERED
    )
    private Instant datetime;

    @Column("open")
    private Double open;

    @Column("high")
    private Double high;

    @Column("low")
    private Double low;

    @Column("close")
    private Double close;

    @Column("volume")
    private Long volume;
}