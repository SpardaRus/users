package ru.social_network.users.utils;

import ru.social_network.users.annotaion.ExcludeFromJacocoGeneratedReport;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ExcludeFromJacocoGeneratedReport
public class DateUtils {

    private DateUtils() {
    }

    private static final ZoneId EUROPE_MOSCOW_TIME_ZONE = ZoneId.of("Europe/Moscow");

    public static LocalDateTime now() {
        return LocalDateTime.now(EUROPE_MOSCOW_TIME_ZONE);
    }
}
