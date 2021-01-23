package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FactoryService {

    public static ObservableList<Label> convertToLabelFromString(String... args) {

        return FXCollections.observableArrayList(
                Arrays.stream(args).collect(Collectors.toList())
                        .stream().map(Label::new).collect(Collectors.toList()));
    }


    public static void getErrorPopUp(String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

}
