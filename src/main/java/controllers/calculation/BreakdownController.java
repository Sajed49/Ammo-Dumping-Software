package controllers.calculation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Data;
import models.breakdown.Breakdown;
import services.LabelService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

@Data
public class BreakdownController implements Initializable {

    @FXML
    Label ser = new Label();
    @FXML
    Label eqptName = new Label("Eqpt Name");


    @FXML
    Label unitName = new Label();
    @FXML
    Label gunType = new Label();
    @FXML
    Label noOfGuns = new Label();
    @FXML
    Label posCount = new Label();

    @FXML
    Label position1 = new Label();
    @FXML
    Label position2 = new Label();
    @FXML
    Label position3 = new Label();

    @FXML
    Label vehicles1 = new Label();
    @FXML
    Label vehicles2 = new Label();
    @FXML
    Label vehicles3 = new Label();

    @FXML
    Label runTime1 = new Label();
    @FXML
    Label runTime2 = new Label();
    @FXML
    Label runTime3 = new Label();

    @FXML
    Label haltTime1 = new Label();
    @FXML
    Label haltTime2 = new Label();
    @FXML
    Label haltTime3 = new Label();

    @FXML
    Label passTime1 = new Label();
    @FXML
    Label passTime2 = new Label();
    @FXML
    Label passTime3 = new Label();

    ArrayList<Label> positions = new ArrayList<>();
    ArrayList<Label> vehicles = new ArrayList<>();
    ArrayList<Label> runTime = new ArrayList<>();
    ArrayList<Label> haltTime = new ArrayList<>();
    ArrayList<Label> passTime = new ArrayList<>();

    Breakdown breakdown;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initPosnName();
        initVehicleName();
        initRunTime();
        initHaltTime();
        initPassTime();
    }

    private void initPosnName() {
        positions.addAll(Arrays.asList(position1, position2, position3));

        for (int i = 0; i < Integer.parseInt(posCount.getText()); i++) {
            LabelService.initLabel(positions.get(i), "0");
        }
    }

    private void initVehicleName() {
        vehicles.addAll(Arrays.asList(vehicles1, vehicles2, vehicles3));

        for (int i = 0; i < Integer.parseInt(posCount.getText()); i++) {
            LabelService.initLabel(vehicles.get(i), "0");
        }
    }

    private void initRunTime() {
        runTime.addAll(Arrays.asList(runTime1, runTime2, runTime3));

        for (int i = 0; i < Integer.parseInt(posCount.getText()); i++) {
            LabelService.initLabel(runTime.get(i), "0");
        }
    }

    private void initHaltTime() {
        haltTime.addAll(Arrays.asList(haltTime1, haltTime2, haltTime3));

        for (int i = 0; i < Integer.parseInt(posCount.getText()); i++) {
            LabelService.initLabel(haltTime.get(i), "0");
        }
    }

    private void initPassTime() {
        passTime.addAll(Arrays.asList(passTime1, passTime2, passTime3));

        for (int i = 0; i < Integer.parseInt(posCount.getText()); i++) {
            LabelService.initLabel(passTime.get(i), "0");
        }
    }

    public void setFromBreakdown(Breakdown breakdown) {

        this.breakdown = breakdown;

        ser.setText(breakdown.getLabelSer());
        eqptName.setText(breakdown.getLabelEqptName());
        unitName.setText(breakdown.getUnitName());
        gunType.setText(breakdown.getGunType());
        noOfGuns.setText(String.valueOf(breakdown.getNoOfGuns()));
        posCount.setText(String.valueOf(breakdown.getPosCount()));


        for (int i = 0; i < breakdown.getPosArrayList().size(); i++) {

            positions.get(i).setText(breakdown.getPosArrayList().get(i).getPosName());
            vehicles.get(i).setText(String.valueOf(breakdown.getPosArrayList().get(i).getVehicles()));
            runTime.get(i).setText(breakdown.getPosArrayList().get(i).getRunTime());
            haltTime.get(i).setText(breakdown.getPosArrayList().get(i).getHaltTime());
            passTime.get(i).setText(breakdown.getPosArrayList().get(i).getPassTime());
        }
    }
}
