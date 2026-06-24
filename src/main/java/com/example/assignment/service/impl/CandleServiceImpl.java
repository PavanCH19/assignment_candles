package com.example.assignment.service.impl;

import com.example.assignment.dto.CandleDto;
import com.example.assignment.dto.CandleResponseDto;
import com.example.assignment.entity.StockData;
import com.example.assignment.repo.StockDataRepository;
import com.example.assignment.service.CandleService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CandleServiceImpl implements CandleService {

    private final StockDataRepository  stockDataRepository;

    @Override
    @Cacheable(
            value = "candles",
            key = "#symbol + '-' + #timeFrame + '-' + #start + '-' + #end + '-' + #page + '-' + #size"
    )
    public CandleResponseDto getCandles(String symbol, String timeFrame, Instant start, Instant end, int page, int size) {

        List<CandleDto> candles = new ArrayList<>();
        List<StockData> data = stockDataRepository.findCandles(symbol, start, end);
        System.out.println(data);
        System.out.println( "Fetching from Cassandra...");

        if (data.isEmpty()) {
            throw new IllegalArgumentException( "No data found for symbol: " + symbol);
        }

        int interval = Interverl(timeFrame);
        for (int i = 0; i < data.size(); i+=interval) {
            List<StockData> window = data.subList(i, Math.min(i+interval, data.size()));
            aggrigation(candles, window);
        }

        int startIndex = page * size;

        if (startIndex >= candles.size()) {
            throw new IllegalArgumentException("Page exceeds available data");
        }

        int endIndex = Math.min( startIndex + size,  candles.size());

        List<CandleDto> paginatedCandles = new ArrayList<>(candles.subList(startIndex, endIndex));
        System.out.println("Fetching from Cassandra..."+new CandleResponseDto( symbol,  timeFrame,  paginatedCandles,  candles.size()));
        return new CandleResponseDto( symbol,  timeFrame,  paginatedCandles,  candles.size());
    }

    private void aggrigation(List<CandleDto> candles, List<StockData> window) {

        Instant time = window.get(0).getDatetime();

        Double open = window.get(0).getOpen();

        Double high = window.stream()
                .mapToDouble(StockData::getHigh)
                .max()
                .orElse(0.0);

        Double low = window.stream()
                .mapToDouble(StockData::getLow)
                .min()
                .orElse(0.0);

        Double close = window.get(window.size() - 1).getClose();

        Long volume = window.stream()
                .mapToLong(StockData::getVolume)
                .sum();

        candles.add(new CandleDto( time,  open,  high,  low,  close,  volume ));
    }

    private int Interverl(String timeFrame) {
        return switch (timeFrame) {
            case "1m" -> 1;
            case "5m" -> 5;
            case "15m" -> 15;
            case "30m" -> 30;
            case "60m" -> 60;
            case "1d" -> 1440;
            default -> throw new IllegalArgumentException(
                    "Invalid timeframe: " + timeFrame
            );
        };
    }


}