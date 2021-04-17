package controllers.calculation;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import main.App;
import models.Unit;
import models.breakdown.Breakdown;
import result.DataStore;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BreakdownInfoController implements Initializable {

    @FXML
    VBox vBox = new VBox();
    @FXML
    JFXButton next = new JFXButton("Next");

    ArrayList<BreakdownController> breakdownControllers = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<Unit> units = DataStore.getInstance().getUnitInfo();

        for (int i = 0; i < units.size(); i++) {
            addBreakdown();
            loadDynamicHeaderLabels(units.get(i), i);
        }

        initNextButton();

    }

    @SneakyThrows(IOException.class)
    private void addBreakdown() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Breakdown.fxml"));
        GridPane pane = loader.load();
        breakdownControllers.add(loader.getController());
        vBox.getChildren().add(pane);
    }


    private void loadDynamicHeaderLabels(Unit unit, int index) {
        Breakdown breakdown = new Breakdown(unit, index);
        BreakdownController controller = breakdownControllers.get(index);
        controller.setFromBreakdown(breakdown);
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


    private void next() throws IOException {

        DataStore.getInstance().getBreakdowns().clear();

        for (BreakdownController breakdownController : breakdownControllers) {

            DataStore.getInstance().getBreakdowns().add(breakdownController.getBreakdown());
        }
        App.setRoot(new TimeTableController(), "TimeTable");
    }


}
