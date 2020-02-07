package org.stellite.antenna.service;

import org.springframework.stereotype.Service;
import org.stellite.antenna.model.PartOfTheDay;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    private static final String FILE_NAME_PATTERN = "snapshot-0%d-%s.png";

    @Override
    public String getFileName(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            timestamp = System.currentTimeMillis();
        }
        LocalDateTime incomingDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                                                                ZoneId.systemDefault());
        return generateFileName(incomingDateTime);
    }

    private String generateFileName(LocalDateTime incomingDateTime) {
        PartOfTheDay partOfTheDay = calculatePartOfTheDay(incomingDateTime.getHour());
        int pictureNumber = calculatePictureNumber(incomingDateTime);

        return String.format(FILE_NAME_PATTERN, pictureNumber, partOfTheDay.getText());
    }

    private int calculatePictureNumber(LocalDateTime incomingDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (incomingDateTime.isAfter(now.minusDays(1))) {
            return 1;
        } else if (incomingDateTime.isAfter(now.minusMonths(2))) {
            return 2;
        } else if (incomingDateTime.isAfter(now.minusMonths(3))) {
            return 3;
        }
        return 4;
    }

    private PartOfTheDay calculatePartOfTheDay(int hour) {
        if (hour >= 6 && hour < 12) {
            return PartOfTheDay.MORNING;
        } else if (hour >= 12 && hour < 17) {
            return PartOfTheDay.DAY;
        }
        return PartOfTheDay.NIGHT;
    }
}
