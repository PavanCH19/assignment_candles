package com.example.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto {
    private String symbol;

    private String timeframe;

    private Integer page;

    private Integer size;

    private Integer totalCandles;

    private Integer totalPages;

    private List<CandleDto> candles;
}
