package ru.social_network.users.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

    private static final ZoneId EUROPE_MOSCOW_TIME_ZONE = ZoneId.of("Europe/Moscow");

    public static LocalDateTime now() {
        return LocalDateTime.now(EUROPE_MOSCOW_TIME_ZONE);
    }
}
