package services;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Factory {

    public static ObservableList<Label> convertToLabelFromString(String... args) {

        return FXCollections.observableArrayList(
                Arrays.stream(args).collect(Collectors.toList())
                        .stream().map(Label::new).collect(Collectors.toList()));
    }


    public static void initDate(JFXDatePicker datePicker) {

        datePicker.setConverter(new StringConverter<LocalDate>() {
            final String pattern = "dd/MM/yyyy";
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }

    public static void initClock(JFXTimePicker timePicker) {

        timePicker.set24HourView(true);

        timePicker.setConverter(new StringConverter<LocalTime>() {

            // create formatter Object
            final String pattern = "HH:mm";
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            {
                timePicker.setPromptText(pattern.toLowerCase());
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
