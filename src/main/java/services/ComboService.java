package services;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Label;


public class ComboService {

    public static void initComboBox(JFXComboBox<Label> comboBox, String[] values) {

        comboBox.getItems().clear();
        comboBox.getItems().addAll(FactoryService.convertToLabelFromString(values));
        comboBox.getSelectionModel().selectFirst();
    }

    public static void initComboBoxWithoutSelection(JFXComboBox<Label> comboBox, String[] values) {

        comboBox.getItems().clear();
        comboBox.getItems().addAll(FactoryService.convertToLabelFromString(values));
    }

    public static void autoSelectComboBoxValue(JFXComboBox<Label> comboBox, String labelValue) {
        for (int i = 0; i < comboBox.getItems().size(); i++) {
            if (comboBox.getItems().get(i).getText().equals(labelValue)) {
                comboBox.getSelectionModel().select(i);
                break;
            }
        }
    }
}
