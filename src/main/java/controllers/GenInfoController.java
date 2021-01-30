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
    JFXComboBox<Label> loadingPoint = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> roadCondition = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> typeOfStore = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> ammoScale = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> noOfUnit = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> limitations = new JFXComboBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initGenInfo();

        if (DataStore.getInstance().getGenInfo() != null) loadFromDataStore();

        typeOfStore.setOnAction(e -> {
            ammoScale.setVisible(typeOfStore.getValue().getText().equals(IComboConstants.typeOfStore[0]));
        });
    }

    private void initGenInfo() {

        ComboService.initComboBox(loadingPoint, IComboConstants.loadingPoint);
        ComboService.initComboBox(roadCondition, IComboConstants.road);
        ComboService.initComboBox(typeOfStore, IComboConstants.typeOfStore);
        ComboService.initComboBox(ammoScale, IComboConstants.ammunitionScale);
        ComboService.initComboBox(noOfUnit, IComboConstants.noOfUnit);
        ComboService.initComboBox(limitations, IComboConstants.limitations);
    }

    private void loadFromDataStore() {
        ComboService.autoSelectComboBoxValue(loadingPoint, DataStore.getInstance().getGenInfo().getLoadingPoint());
        ComboService.autoSelectComboBoxValue(roadCondition, DataStore.getInstance().getGenInfo().getRoadCondition());
        ComboService.autoSelectComboBoxValue(typeOfStore, DataStore.getInstance().getGenInfo().getTypeOfStore());
        ComboService.autoSelectComboBoxValue(ammoScale, DataStore.getInstance().getGenInfo().getAmmoScale());
        ComboService.autoSelectComboBoxValue(noOfUnit, DataStore.getInstance().getGenInfo().getNoOfFireUnits());
        ComboService.autoSelectComboBoxValue(limitations, DataStore.getInstance().getGenInfo().getLimitations());

        ammoScale.setVisible(typeOfStore.getValue().getText().equals(IComboConstants.typeOfStore[0]));
    }

    @FXML
    private void next() throws IOException {

        if (!checkData()) FactoryService.getErrorPopUp(IConstants.missingDataError);
        else {

            GenInfo genInfo = new GenInfo(loadingPoint.getValue().getText(), roadCondition.getValue().getText().trim(),
                    typeOfStore.getValue().getText(), ammoScale.getValue().getText().trim(),
                    noOfUnit.getValue().getText().trim(), limitations.getValue().getText());

            // Singleton class
            DataStore.getInstance().setGenInfo(genInfo);

            App.setRoot(new TimeCalController(), "TimeCal");
        }
    }

    private boolean checkData() {

        boolean valid = true;

        if (roadCondition.getValue() == null || roadCondition.getValue().getText().trim().equals("")) valid = false;
        if (ammoScale.getValue() == null || ammoScale.getValue().getText().trim().equals("")) valid = false;
        if (noOfUnit.getValue() == null || noOfUnit.getValue().getText().trim().equals("")) valid = false;

        return valid;

    }

}
