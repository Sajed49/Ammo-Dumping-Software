package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Data;
import services.ComboService;

import java.net.URL;
import java.util.ResourceBundle;


public class UnitController implements Initializable {

    @FXML
    Label ser = new Label();

    @FXML
    JFXComboBox<Label> unitName = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> gunType = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> noOfGuns = new JFXComboBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initComboBoxes();
    }

    private void initComboBoxes() {

        ComboService.initComboBox(unitName, IComboConstants.unitName);
        ComboService.initComboBox(gunType, IComboConstants.gunType);
        ComboService.initComboBox(noOfGuns, IComboConstants.noOfGuns);
    }

    public void setSer(Integer ser) {
        this.ser.setText( String.valueOf( ser ));
    }

    public String getSer() {
        return ser.getText();
    }
}
