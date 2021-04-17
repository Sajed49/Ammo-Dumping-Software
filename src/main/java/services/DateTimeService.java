package services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeService {

    public static final String pattern = "dd-MM-yyyy HH:mm";
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

    public static String display(LocalDateTime dateTime) {
        return dateTimeFormatter.format(dateTime);
    }

    public static LocalDateTime addMinutesToDateTime(LocalDateTime dateTime, Long minutes) {
        return dateTime.plusMinutes(minutes);
    }
}
