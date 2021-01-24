package models;

import controllers.UnitController;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Unit {

    private String labelSer;
    private String labelEqptName;
    private String labelAmmoLabel;

    private String unitName;
    private String gunType;
    private String noOfGuns;
    private String posCount;

    private ArrayList<Pos> positions = new ArrayList<>();


    public Unit(UnitController controller) {
        this.labelSer = controller.getSer().getText();
        this.labelEqptName = controller.getEqptName().getText();
        this.labelAmmoLabel = controller.getAmmoLabel().getText();

        this.unitName = controller.getUnitName().getValue().getText();
        this.gunType = controller.getGunType().getValue().getText();
        this.noOfGuns = controller.getNoOfGuns().getValue().getText();
        this.posCount = controller.getPosCount().getValue().getText();
    }
}
