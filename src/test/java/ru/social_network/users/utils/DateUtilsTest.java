package ru.social_network.users.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    void now() {
        ZoneId EUROPE_MOSCOW_TIME_ZONE = ZoneId.of("Europe/Moscow");
        LocalDateTime beforeDateTime = LocalDateTime.now(EUROPE_MOSCOW_TIME_ZONE);
        LocalDateTime afterDateTime = LocalDateTime.now(EUROPE_MOSCOW_TIME_ZONE);

        LocalDateTime actualDateTime = DateUtils.now();

        assertThat(actualDateTime)
                .isAfterOrEqualTo(beforeDateTime)
                .isBeforeOrEqualTo(afterDateTime);
    }
}