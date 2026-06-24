package com.example.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandleReqDto {
    private String symbol;
    private String timeFrame;
    private String startDate;
    private String endDate;
}
