package services;

import com.jfoenix.controls.JFXDatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateService {

    static final String pattern = "dd-MM-yyyy";
    static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    public static long findDays(JFXDatePicker from, JFXDatePicker to) {

        return to.getValue().toEpochDay() - from.getValue().toEpochDay() + 1;
    }

    public static String display(JFXDatePicker date) {
        return dateFormatter.format(date.getValue());
    }

    public static void setDate(JFXDatePicker datePicker, String givenDate) {
        datePicker.setValue(LocalDate.parse(givenDate, dateFormatter));
    }

    public static void initDate(JFXDatePicker datePicker) {

        datePicker.setConverter(new StringConverter<LocalDate>() {


            {
                datePicker.setValue(LocalDate.now());
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
}
