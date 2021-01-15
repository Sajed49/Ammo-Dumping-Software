package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import services.ComboService;

import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    JFXComboBox<Label> posCount = new JFXComboBox<>();

    private static final int posSize = 3;

    ArrayList<JFXComboBox<Label>> ammo = new ArrayList<>();


    @FXML
    JFXComboBox<Label> ammo1 = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> ammo2 = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> ammo3 = new JFXComboBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initComboBoxes();
        initAmmo();
    }

    private void initComboBoxes() {

        ComboService.initComboBox(unitName, IComboConstants.unitName);
        ComboService.initComboBox(gunType, IComboConstants.gunType);
        ComboService.initComboBox(noOfGuns, IComboConstants.noOfGuns);
        ComboService.initComboBox(posCount, IComboConstants.posCount);
    }

    public void setSer(Integer ser) {
        this.ser.setText(String.valueOf(ser));
    }

    private void initAmmo() {

        ammo.add(ammo1);
        ammo.add(ammo2);
        ammo.add(ammo3);

        for (JFXComboBox<Label> box : ammo) {
            ComboService.initComboBox(box, IComboConstants.ammoCount);
        }
    }
}
