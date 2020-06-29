package main;

import entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import misc.Vec3d;


public class MainApplication extends Application {


    ////////////////////////////////////////
    /*        Simulation Parameters       */
    ////////////////////////////////////////


    static Simulation simulation; // Don't edit!!

    private static final double tickFrequency = 200.0;
    private static final double canvasScaleX = 200;
    private static final double canvasScaleY = 200;


    ////////////////////////////////////////
    /*          Simulation Members        */
    ////////////////////////////////////////


    private static Marble marble1, marble2;
    private static SimpleTrack track1, track2;


    ////////////////////////////////////////
    /*         Simulation Building        */
    ////////////////////////////////////////


    public void initialize() {
        simulation = new Simulation();
        reset();
    }

    private void buildSimulation() {

        track1 = new SimpleTrack(-1.1, -0.1, new double[]{0.1, 1.0});
        track2 = new SimpleTrack(-2, 0, new double[]{0, 20});



        marble1 = new Marble(2, 0.1);
        marble2 = new Marble(2, 0.2);
        marble1.setPos(new Vec3d(.5, 0, -1.07));
        marble2.setPos(new Vec3d(.5, 0, -1.5));
        simulation.addEntities(marble1, marble2);
        simulation.addTracks(track1, track2);
        marble1.setVelo(new Vec3d(0.05, 0, 0));
        marble2.setVelo(new Vec3d(1,0,0));

    }

    private void reset() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        simulation.clearEntities();
        simulation.clearTracks();
        buildSimulation();
        drawAllShapes();
    }


    ////////////////////////////////////////
    /*             Program Flow           */
    ////////////////////////////////////////


    static GraphicsContext gc; // Don't edit!!

    class WindowButtons extends HBox {

        public WindowButtons() {
            Button closeBtn = new Button("X");
            closeBtn.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(2), Insets.EMPTY)));

            closeBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    closeBtn.setBackground(new Background(new BackgroundFill(Color.INDIANRED, new CornerRadii(2), Insets.EMPTY)));
                }
            });

            closeBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    closeBtn.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(2), Insets.EMPTY)));
                }
            });

            closeBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });

            this.setAlignment(Pos.BASELINE_RIGHT);
            this.getChildren().add(closeBtn);
        }
    }

    @Override
    public void start(Stage primaryStage) {

        Text windowText = new Text("Rolling Stones");
        windowText.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, FontPosture.ITALIC,20));

        HBox p = new HBox();
        HBox.setHgrow(p, Priority.ALWAYS);

        ToolBar toolBar = new ToolBar();
        int height = 30;
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);

        toolBar.getItems().addAll(windowText, p, new WindowButtons());


        Text controlsText = new Text("Controls");
        controlsText.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 22));
        controlsText.setFill(Color.DARKORANGE);


        Region spacer1 = new Region();
        spacer1.setPrefHeight(20);


        Text elementsText = new Text("Elements");
        elementsText.setFont(Font.font(18));
        elementsText.setFill(Color.DARKORANGE);


        VBox elementsBox = new VBox();
        VBox.setVgrow(elementsBox, Priority.ALWAYS);
        VBox.setMargin(elementsBox, new Insets(10,0,0, 0));
        elementsBox.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(3))));
        elementsBox.setFillWidth(true);


        Text sliderSpeedText = new Text("Speed");
        sliderSpeedText.setFont(new Font(18));
        sliderSpeedText.setFill(Color.DARKORANGE);


        Button startButton = new Button("Start/Unpause");
        startButton.setPrefWidth(200);
        startButton.setMaxHeight(40);
        startButton.setPadding(new Insets(15,0,15,0));
        startButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(simulation.isPaused())  {
                    simulation.setPaused(false);
                    startButton.setText("Pause");
                } else {
                    simulation.setPaused(true);
                    startButton.setText("Start/Unpause");
                }

            }
        });
        startButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startButton.setBackground(new Background(new BackgroundFill(Color.ORANGERED, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        startButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });


        Region spacer2 = new Region();
        spacer2.setPrefHeight(10);


        Button resetButton = new Button("Reset");
        resetButton.setPrefWidth(200);
        resetButton.setPrefHeight(40);
        resetButton.setPadding(new Insets(15,0,15,0));
        resetButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reset();
                simulation.setPaused(true);
                startButton.setText("Start/Unpause");
            }
        });
        resetButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resetButton.setBackground(new Background(new BackgroundFill(Color.ORANGERED, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        resetButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resetButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });

        Region spacer3 = new Region();
        spacer3.setPrefHeight(50);


        VBox controlPanel = new VBox();
        controlPanel.setAlignment(Pos.BASELINE_CENTER);
        controlPanel.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        controlPanel.setPadding(new Insets(10,75,10,75));
        controlPanel.setBorder(new Border(new BorderStroke(null , null , null, Color.DARKORANGE,
                BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(5), Insets.EMPTY)));
        controlPanel.setFillWidth(true);
        controlPanel.getChildren().addAll(controlsText, spacer1, elementsText, elementsBox, sliderSpeedText, startButton, spacer2, resetButton, spacer3);


        TextField textField = new TextField();
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                parseInput(textField.getText());
                textField.clear();
            }
        });


        Canvas canvas = new Canvas(1080, 970);

        AnchorPane.setLeftAnchor(textField, 0.0);
        AnchorPane.setRightAnchor(textField, 0.0);
        AnchorPane.setBottomAnchor(textField, 0.0);


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(canvas, textField);


        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(5), Insets.EMPTY)));
        borderPane.setTop(toolBar);
        borderPane.setRight(controlPanel);
        borderPane.setCenter(anchorPane);


        Group root = new Group();
        root.getChildren().add(borderPane);





        Scene scene = new Scene(root);
        primaryStage.setTitle("Rolling Stones");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.setResizable(false);

        canvas.getGraphicsContext2D().scale(canvasScaleX, canvasScaleY);
        gc = canvas.getGraphicsContext2D();


        initialize();


        AnimationTimer timer = new AnimationTimer() {
            long lastTick = 0;

            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;

                    return;
                }

                if (now - lastTick > 1000000000 / tickFrequency && !simulation.isPaused()) {
                    lastTick = now;
                    tick(1 / tickFrequency, gc);
                }
            }
        };
        timer.start();


    }

    public static void tick(double deltaTick, GraphicsContext gc) {


        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        drawAllShapes();


        simulation.tick(deltaTick);
    }

    public static void main(String[] args) {
        launch(args);
    }





    ////////////////////////////////////////
    /*               Inputs               */
    ////////////////////////////////////////


    private void parseInput(String input) {
        String[] inputSplit = input.split(" ");
        switch (inputSplit[0]) {
            case "/start":
            case "/unpause":
            case "/play":
                simulation.setPaused(false);
                break;

            case "/pause":
            case "/stop":
                simulation.setPaused(true);
                break;

            case "/reset":
            case "/restart":
                reset();
                simulation.setPaused(true);
                break;

            default:
        }
    }


    ////////////////////////////////////////
    /*            Draw Methods            */
    ////////////////////////////////////////


    @Deprecated
    private static void drawShapes() {
        gc.setFill(Color.RED);

        gc.fillOval(marble1.getPos().x - marble1.getDiameter() / 2, (marble1.getPos().z - marble1.getDiameter() / 2) * -1,
                marble1.getDiameter(), marble1.getDiameter());

        gc.fillOval(marble2.getPos().x - marble2.getDiameter() / 2, (marble2.getPos().z - marble2.getDiameter() / 2) * -1,
                marble2.getDiameter(), marble2.getDiameter());
    }

    private static void drawSphere(Sphere s, Color c) {
        Paint old = gc.getFill();
        gc.setFill(c);
        gc.fillOval(s.getPos().x - s.getDiameter() / 2, (s.getPos().z + s.getDiameter() / 2)*-1,
                s.getDiameter(), s.getDiameter());

        gc.setFill(old);
    }

    private static void drawRectangle(Rectangle r, Color c) {
        Paint old = gc.getFill();
        gc.setFill(c);

        //TODO Rectangle drawing

        gc.setFill(old);
    }

    private static void drawSimpleTrack(SimpleTrack st, Color c) {
        Paint old = gc.getStroke();
        gc.setStroke(c);
        gc.setLineWidth(0.01);

        gc.strokeLine(st.getXIntervall()[0], -st.getFunc().valueAt(st.getXIntervall()[0], 0),
                st.getXIntervall()[1], -st.getFunc().valueAt(st.getXIntervall()[1], 0));

        gc.setStroke(old);
    }

    private static void drawAllShapes() {

        for(Track t : simulation.getTracks()) {
            if(t instanceof SimpleTrack) {
                drawSimpleTrack((SimpleTrack) t, Color.RED);
            }
        }

        for (Entity e : simulation.getEntities()) {
            if (e instanceof Sphere) {
                drawSphere((Sphere) e, Color.LIGHTSKYBLUE);
            } else if (e instanceof Rectangle) {
                drawRectangle((Rectangle) e, Color.DARKOLIVEGREEN);
            } else  {
                System.err.println("Entity is not valid!");
            }
        }
    }


}
