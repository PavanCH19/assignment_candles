package com.example.assignment.repo;

import com.example.assignment.entity.StockData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface StockDataRepository extends CassandraRepository<StockData, String> {
    @Query("""
        SELECT * FROM stock_data
        WHERE symbol = ?0
        AND datetime >= ?1
        AND datetime < ?2
        """)
    List<StockData> findCandles(
            String symbol,
            Instant startDate,
            Instant endDate
    );
}