package models;

import controllers.UnitController;
import lombok.Data;
import models.pos.ArtiPos;
import models.pos.OtherPos;
import models.pos.Pos;

import java.util.ArrayList;

@Data
public class Unit {

    private String labelSer;
    private String labelEqptName;
    private String labelAmmoLabel;

    private String unitName;
    private String gunType;
    private Integer noOfGuns;
    private Integer posCount;

    private ArrayList<Pos> positions = new ArrayList<>();


    public Unit(UnitController controller) {

        this.labelSer = controller.getSer().getText();
        this.labelEqptName = controller.getEqptName().getText();
        this.labelAmmoLabel = controller.getAmmoLabel().getText();

        this.unitName = controller.getUnitName().getValue().getText();
        this.gunType = controller.getGunType().getValue().getText();
        this.noOfGuns = Integer.parseInt(controller.getNoOfGuns().getValue().getText());
        this.posCount = Integer.parseInt(controller.getPosCount().getValue().getText());

//        System.out.println( this.posCount );
        for (int i = 0; i < this.posCount; i++) {

            Pos pos;
            if (this.labelEqptName.equalsIgnoreCase("Eqpt Name"))
                pos = new ArtiPos(controller.getPosnName().get(i).getText(), Integer.parseInt(controller.getPriority().get(i).getValue().getText()),
                        Double.parseDouble(controller.getDistance().get(i).getText()), Double.parseDouble(controller.getAmmo().get(i).getText()));
            else
                pos = new OtherPos(controller.getPosnName().get(i).getText(), Integer.parseInt(controller.getPriority().get(i).getValue().getText()),
                        Double.parseDouble(controller.getDistance().get(i).getText()), Double.parseDouble(controller.getAmmo().get(i).getText()));

            positions.add(pos);
        }
    }
}
