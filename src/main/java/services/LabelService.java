package services;

import javafx.scene.control.Label;

public class LabelService {

    public static void initLabel(Label label, String value) {
        label.setText(value);
    }

    public static Label initLabel(String value) {
        return new Label(value);
    }
}