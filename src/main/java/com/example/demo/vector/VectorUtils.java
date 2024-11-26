package com.example.demo.vector;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class VectorUtils {

    private VectorUtils() {}

    public static LocalDateTime getLocalDateTimeFromDouble(Double time) {
        long l = BigDecimal.valueOf(time).longValue();
        Instant instant = Instant.ofEpochMilli(l);
        return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));
    }
}
