package services;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.Label;


public class ComboService {

    public static void initComboBox(JFXComboBox<Label> comboBox, String[] values){

        comboBox.getItems().clear();
        comboBox.getItems().addAll( FactoryService.convertToLabelFromString( values ));
        comboBox.getSelectionModel().selectFirst();
    }
}
