package com.example.assignment.service;

import com.example.assignment.dto.CandleResponseDto;

import java.time.Instant;
import java.time.LocalDateTime;

public interface CandleService {

    CandleResponseDto getCandles(
            String symbol,
            String timeFrame,
            Instant startDate,
            Instant endDate,
            int page,
            int size
    );
}