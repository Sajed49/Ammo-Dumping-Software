package services;

import com.jfoenix.controls.JFXTimePicker;
import javafx.util.StringConverter;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeService {

    public static final String pattern = "HH:mm";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

    public static long findTimeDiffInMinutes(JFXTimePicker from, JFXTimePicker to) {
        return Duration.between(from.getValue(), to.getValue()).toMinutes();
    }


    public static String formatToDisplay(long input) {
        return input / 60 + " hr " + input % 60 + " min";
    }

    public static String display(JFXTimePicker time) {
        return formatter.format(time.getValue());
    }

    public static void setTime(JFXTimePicker timePicker, String givenTime) {
        timePicker.setValue(LocalTime.parse(givenTime, formatter));
    }

    public static void initClock(JFXTimePicker timePicker) {

        timePicker.set24HourView(true);

        timePicker.setConverter(new StringConverter<LocalTime>() {

            // create formatter Object


            {
                timePicker.setValue(LocalTime.parse(LocalTime.now().format(formatter)));
            }

            @Override
            public String toString(LocalTime localTime) {

                if (timePicker.getValue() != null) {
                    return formatter.format(timePicker.getValue());
                } else {
                    return "";
                }
            }

            @Override
            public LocalTime fromString(String s) {
                if (s != null && !s.isEmpty()) {
                    return LocalTime.parse(s, formatter);
                } else {
                    return null;
                }
            }
        });
    }

}
