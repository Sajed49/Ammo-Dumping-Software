package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import services.Factory;

import java.net.URL;
import java.util.ResourceBundle;

public class GenInfoController implements Initializable {

    @FXML
    JFXComboBox<Label> roadCondition = new JFXComboBox<Label>();
    @FXML
    JFXComboBox<Label> AmmoScale = new JFXComboBox<Label>();
    @FXML
    JFXComboBox<Label> noOfFireUnits = new JFXComboBox<Label>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initGenInfo();
    }

    private void initGenInfo(){

        roadCondition.getItems().clear();
        AmmoScale.getItems().clear();
        noOfFireUnits.getItems().clear();

        roadCondition.getItems().addAll( Factory.convertToLabelFromString( IComboConstants.road ));
        AmmoScale.getItems().addAll( Factory.convertToLabelFromString( IComboConstants.ammunitionScale ));
        noOfFireUnits.getItems().addAll( Factory.convertToLabelFromString( IComboConstants.noOfFireUnit ));
    }
}
