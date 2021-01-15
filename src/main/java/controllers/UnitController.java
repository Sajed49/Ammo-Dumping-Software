package controllers;

import Constants.IComboConstants;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import services.ComboService;
import services.TextFieldService;

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

    @FXML
    JFXTextField posnName1 = new JFXTextField();
    @FXML
    JFXTextField posnName2 = new JFXTextField();
    @FXML
    JFXTextField posnName3 = new JFXTextField();

    @FXML
    JFXComboBox<Label> priority1 = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> priority2 = new JFXComboBox<>();
    @FXML
    JFXComboBox<Label> priority3 = new JFXComboBox<>();

    @FXML
    JFXTextField distance1 = new JFXTextField();
    @FXML
    JFXTextField distance2 = new JFXTextField();
    @FXML
    JFXTextField distance3 = new JFXTextField();

    @FXML
    JFXTextField ammo1 = new JFXTextField();
    @FXML
    JFXTextField ammo2 = new JFXTextField();
    @FXML
    JFXTextField ammo3 = new JFXTextField();

    ArrayList<JFXTextField> posnName = new ArrayList<>();
    ArrayList<JFXComboBox<Label>> priority = new ArrayList<>();
    ArrayList<JFXTextField> distance = new ArrayList<>();
    ArrayList<JFXTextField> ammo = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initComboBoxes();
        initPosnName();
        initPriority();
        initDistance();
        initAmmo();

        posCount.setOnAction(e -> {
            int index = posCount.getSelectionModel().getSelectedIndex();
            determineActiveStatus(index);
        });
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

    private void initPosnName() {

        posnName.add(posnName1);
        posnName.add(posnName2);
        posnName.add(posnName3);

        for (int i = 0; i < posnName.size(); i++) {
            TextFieldService.initTextField( posnName.get(i), IComboConstants.posnName[i] );
        }
    }

    private void initPriority() {

        priority.add(priority1);
        priority.add(priority2);
        priority.add(priority3);

        for (int i = 0; i < priority.size(); i++) {
            ComboService.initComboBoxWithoutSelection( priority.get(i), IComboConstants.priority);
            priority.get(i).getSelectionModel().select(i);
        }
    }

    private void initDistance() {

        distance.add(distance1);
        distance.add(distance2);
        distance.add(distance3);

        for (JFXTextField textField : distance) {
            TextFieldService.initTextField(textField, "0.00");
        }
    }

    private void initAmmo() {

        ammo.add(ammo1);
        ammo.add(ammo2);
        ammo.add(ammo3);

        for (JFXTextField textField : ammo) {
            TextFieldService.initTextField(textField, "0");
        }
    }

    private void determineActiveStatus(int index){

        enable(0);

        if( index == 0 ) {
            enable(1);
            enable(2);
        }
        else if( index == 1) {
            enable(1);
            disable(2);
        }
        else {
            disable(1);
            disable(2);
        }
    }

    private void disable(int index){

        posnName.get(index).setDisable( true );
        priority.get(index).setDisable( true );
        distance.get(index).setDisable( true );
        ammo.get(index).setDisable( true );
    }

    private void enable(int index) {
        posnName.get(index).setDisable( false );
        priority.get(index).setDisable( false );
        distance.get(index).setDisable( false );
        ammo.get(index).setDisable( false );
    }
}
