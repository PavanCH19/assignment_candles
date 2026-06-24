package com.example.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandleResponseDto implements Serializable {

    private String symbol;
    private String timeframe;
    private List<CandleDto> candles;
    private Integer count;


}