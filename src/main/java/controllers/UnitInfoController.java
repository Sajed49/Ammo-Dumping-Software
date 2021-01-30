package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXButton;
import controllers.calculation.BreakdownInfoController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.App;
import models.Unit;
import result.DataStore;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class UnitInfoController implements Initializable {

    @FXML
    VBox vBox = new VBox();
    @FXML
    JFXButton next = new JFXButton("Next");


    ArrayList<UnitController> unitControllers = new ArrayList<>();
    Integer totalUnits = 0;
    String typeOfStore = IComboConstants.typeOfStore[0];
    String ammunitionScale = IComboConstants.ammunitionScale[0];

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        unitControllers.clear();

        if (DataStore.getInstance().getGenInfo() != null) {

            totalUnits = Integer.valueOf(DataStore.getInstance().getGenInfo().getNoOfFireUnits());
            typeOfStore = DataStore.getInstance().getGenInfo().getTypeOfStore();
            ammunitionScale = DataStore.getInstance().getGenInfo().getAmmoScale();

            try {
                for (int i = 0; i < totalUnits; i++) {
                    addUnit();
                    unitControllers.get(i).setSer(i + 1);
                    unitControllers.get(i).setEqptLabelName(typeOfStore);
                    unitControllers.get(i).setAmmoLabel(typeOfStore, ammunitionScale);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (unitControllers.size() <= DataStore.getInstance().getUnitInfo().size()) loadFromDataStore();
        }

        initNextButton();
    }

    private void initNextButton() {
        next.getStyleClass().add("custom-button");
        next.setOnAction(e -> {
            try {
                next();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        vBox.getChildren().add(next);
    }

    private void addUnit() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Unit.fxml"));
        GridPane pane = loader.load();
        unitControllers.add(loader.getController());
        vBox.getChildren().add(pane);
    }

    private void next() throws IOException {

        DataStore.getInstance().getUnitInfo().clear();

        for (UnitController unitController : unitControllers) {
            unitController.sort();
            Unit unit = new Unit(unitController);
            DataStore.getInstance().getUnitInfo().add(unit);
        }
        App.setRoot(new BreakdownInfoController(), "BreakdownInfo");
    }

    private void loadFromDataStore() {

        ArrayList<Unit> units = DataStore.getInstance().getUnitInfo();

        for (int i = 0; i < unitControllers.size(); i++) {

            unitControllers.get(i).setAllSavedData(units.get(i));
        }
    }

}
