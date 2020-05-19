package debug;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class DebugController implements Initializable{

    @FXML
    public Canvas canvas;

    @FXML
    public Pane pane;

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }

    public Pane getPane(){
        return pane;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        pane = new Pane();



        System.out.println("Controller initialized!");
        System.out.println("Canvas is "+ pane);

    }
}
