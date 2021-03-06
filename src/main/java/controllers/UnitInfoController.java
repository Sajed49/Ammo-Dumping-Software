package controllers;

import Constants.IComboConstants;
import Constants.IConstants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import result.DataStore;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UnitInfoController implements Initializable {

    @FXML
    VBox vBox = new VBox();

    ArrayList<UnitController> unitControllers = new ArrayList<>();
    Integer totalUnits = 0;
    String typeOfStore = IComboConstants.typeOfStore[0];
    String ammunitionScale = IComboConstants.ammunitionScale[0];

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (DataStore.getInstance().getGenInfo() != null) {

            totalUnits = Integer.valueOf(DataStore.getInstance().getGenInfo().getNoOfFireUnits());
            typeOfStore = DataStore.getInstance().getGenInfo().getTypeOfStore();
            ammunitionScale = DataStore.getInstance().getGenInfo().getAmmoScale();
        }


        try {
            for (int i = 0; i < totalUnits; i++) {
                addUnit();
                unitControllers.get(i).setSer(i + 1);
                unitControllers.get(i).setEqptLabelName( typeOfStore );
                unitControllers.get(i).setAmmoLabel(typeOfStore, ammunitionScale);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addUnit() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Unit.fxml"));
        GridPane pane = loader.load();
        unitControllers.add(loader.getController());
        vBox.getChildren().add(pane);
    }
}
