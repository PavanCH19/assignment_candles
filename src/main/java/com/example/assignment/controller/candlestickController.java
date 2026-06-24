package com.example.assignment.controller;

import com.example.assignment.Utility.*;
import com.example.assignment.dto.CandleResponseDto;
import com.example.assignment.service.CandleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class candlestickController {

    private final CandleService candleService;

    @GetMapping("/candles")
    public ResponseEntity<CandleResponseDto> getCandles(
            @RequestParam(required = true) String symbol,
            @RequestParam(required = true) String timeFrame,
            @RequestParam(required = true) String startDate,
            @RequestParam(required = true) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        if (symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol cannot be empty");
        }

        Instant start = LocalDateTime.parse(startDate).atZone(ZoneOffset.UTC).toInstant();
        Instant end = LocalDateTime.parse(endDate).atZone(ZoneOffset.UTC).toInstant();

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        CandleResponseDto response = candleService.getCandles(symbol, timeFrame, start, end, page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/load")
    public String load() throws Exception {
        load_data.upload_data();
        return "Data Loaded";
    }
}
