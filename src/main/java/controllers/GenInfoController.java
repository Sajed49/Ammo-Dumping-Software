package controllers;

import Constants.IComboConstants;
import Constants.IConstants;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.App;
import models.GenInfo;
import result.DataStore;
import services.ComboService;
import services.FactoryService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GenInfoController implements Initializable {

    @FXML
    JFXComboBox<Label> roadCondition = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> AmmoScale = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> noOfFireUnits = new JFXComboBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initGenInfo();

        if (DataStore.getInstance().getGenInfo() != null) loadFromDataStore();
    }

    private void initGenInfo() {

        ComboService.initComboBox(roadCondition, IComboConstants.road);
        ComboService.initComboBox(AmmoScale, IComboConstants.ammunitionScale);
        ComboService.initComboBox(noOfFireUnits, IComboConstants.noOfFireUnit);
    }

    private void loadFromDataStore() {
        FactoryService.autoSelectComboBoxValue(roadCondition, DataStore.getInstance().getGenInfo().getRoadCondition());
        FactoryService.autoSelectComboBoxValue(AmmoScale, DataStore.getInstance().getGenInfo().getAmmoScale());
        FactoryService.autoSelectComboBoxValue(noOfFireUnits, DataStore.getInstance().getGenInfo().getNoOfFireUnits());
    }

    @FXML
    private void next() throws IOException {

        if (!checkData()) FactoryService.getErrorPopUp(IConstants.missingDataError);
        else {

            GenInfo genInfo = new GenInfo(roadCondition.getValue().getText().trim(),
                    AmmoScale.getValue().getText().trim(), noOfFireUnits.getValue().getText().trim());

            // Singleton class
            DataStore.getInstance().setGenInfo(genInfo);

            App.setRoot(new TimeCalController(), "TimeCal");
        }
    }

    private boolean checkData() {

        boolean valid = true;

        if (roadCondition.getValue() == null || roadCondition.getValue().getText().trim().equals("")) valid = false;
        if (AmmoScale.getValue() == null || AmmoScale.getValue().getText().trim().equals("")) valid = false;
        if (noOfFireUnits.getValue() == null || noOfFireUnits.getValue().getText().trim().equals("")) valid = false;

        return valid;

    }

}
