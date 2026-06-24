package com.example.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandleDto implements Serializable {
    private Instant datetime;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
}