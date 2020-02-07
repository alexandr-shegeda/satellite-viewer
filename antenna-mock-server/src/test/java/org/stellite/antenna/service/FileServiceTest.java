package org.stellite.antenna.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService = new FileServiceImpl();

    @ParameterizedTest
    @CsvSource({
                       "0,3,snapshot-01-night.png",
                       "0,6,snapshot-01-morning.png",
                       "0,14,snapshot-01-day.png",
                       "0,21,snapshot-01-night.png",

                       "1,3,snapshot-02-night.png",
                       "1,6,snapshot-02-morning.png",
                       "1,14,snapshot-02-day.png",
                       "1,21,snapshot-02-night.png",

                       "2,3,snapshot-03-night.png",
                       "2,6,snapshot-03-morning.png",
                       "2,14,snapshot-03-day.png",
                       "2,8,snapshot-03-morning.png",

                       "4,3,snapshot-04-night.png",
                       "4,6,snapshot-04-morning.png",
                       "4,14,snapshot-04-day.png",
                       "4,21,snapshot-04-night.png",
                       "4,9,snapshot-04-morning.png"
    })
    void getFileName(int minusMonth, int hour, String expectedFileName) {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().minusMonths(minusMonth), LocalTime.of(hour, 21));
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        long timestamp = instant.toEpochMilli();
        String actualFileName = fileService.getFileName(timestamp);
        assertEquals(expectedFileName, actualFileName);
    }
}