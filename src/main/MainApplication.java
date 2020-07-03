package main;

import entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import misc.Drawable;
import misc.Utils;
import misc.Vec3d;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;


public class MainApplication extends Application {


    ////////////////////////////////////////
    /*        Simulation Parameters       */
    ////////////////////////////////////////


    private static final double tickFrequency = 200;
    private static final double simPanelSizeX = 1000f;
    private static final double simPanelSizeY = 800f;
    private static final Scale simSceneScale = new Scale(200, 200);
    static Simulation simulation; // Don't edit!!


    ////////////////////////////////////////
    /*          Simulation Members        */
    ////////////////////////////////////////

    static GraphicsContext gc; // Don't edit!!
    static Pane simPane;
    private static Marble marble1, marble2;


    ////////////////////////////////////////
    /*         Simulation Building        */
    ////////////////////////////////////////

    private static Track track1, track2;
    private static final ArrayList<Drawable> samples = new ArrayList<>();

    private void buildSimulation() {

        track1 = new Track(-1.1, -0.1, new double[]{0.1, 1.0});
        track2 = new Track(-2, 0, new double[]{0, 20});


        marble1 = new Marble(1, 0.1);
        marble2 = new Marble(1, 0.2);
        marble1.setPos(new Vec3d(.5, 0, -1.07));
        marble2.setPos(new Vec3d(.5, 0, -1.5));
        simulation.addEntities(marble2);
        simulation.addTracks(track1, track2);
        marble1.setVelo(new Vec3d(0.05, 0, 0));


    }

    private void addSamples() {
        samples.add(new Track(-1.2, -0.1, new double[]{0.1, 1.0}));
        samples.add(new Track(-1, 0.1, new double[]{0.1, 1.0}));
        samples.add(new Track(-1, 1, new double[]{0.1, 0.5}));
    }


    ////////////////////////////////////////
    /*           Program Flow             */
    ////////////////////////////////////////

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        initialize(primaryStage);

        setTickTimer();

    }

    private void initialize(Stage primaryStage) {

        addSamples();

        BorderPane borderPane = generateUIElements();

        initializeUI(primaryStage, borderPane);

        simulation = new Simulation();

        reset();
    }


    private void setTickTimer() {
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

    private static void tick(double deltaTick, GraphicsContext gc) {
        simPane.getChildren().clear();
        drawAllShapes();
        simulation.tick(deltaTick);
    }

    private void reset() {
        simPane.getChildren().clear();
        simulation.clearEntities();
        simulation.clearTracks();
        buildSimulation();
        drawAllShapes();
        Utils.clipChildren(simPane, simPanelSizeX, simPanelSizeY);
    }


    ////////////////////////////////////////
    /*            UI Building             */
    ////////////////////////////////////////

    private void initializeUI(Stage primaryStage, BorderPane borderPane) {
        Group root = new Group();
        root.getChildren().add(borderPane);


        Scene scene = new Scene(root);
        primaryStage.setTitle("Rolling Stones");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    @NotNull
    private BorderPane generateUIElements() {
        ToolBar toolBar = buildToolBar();


        ControlPanel controllPanel = new ControlPanel().invoke();
        VBox elementsBox = controllPanel.getElementsBox();
        ScrollPane elementsScrollPane = controllPanel.getElementsScrollPane();
        VBox controlPanel = controllPanel.getControlPanel();


        SimulationPanel simulationPanel = new SimulationPanel(toolBar, controlPanel).invoke();
        Pane simPane = simulationPanel.getSimPane();
        BorderPane borderPane = simulationPanel.getBorderPane();


        generatePreviewBox(elementsBox, elementsScrollPane, simPane);
        return borderPane;
    }


    @NotNull
    private ToolBar buildToolBar() {
        Text windowText = new Text("Rolling Stones");
        windowText.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, FontPosture.ITALIC, 20));

        HBox p = new HBox();
        HBox.setHgrow(p, Priority.ALWAYS);

        ToolBar toolBar = new ToolBar();
        int height = 30;
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);

        toolBar.getItems().addAll(windowText, p);
        return toolBar;
    }

    private void generatePreviewBox(VBox elementsBox, ScrollPane elementsScrollPane, Pane simPane) {
        for (Drawable e : samples) {
            final double psm = 0.5;

            StackPane container = new StackPane();
            container.setMinHeight(100);
            container.prefWidthProperty().bind(elementsScrollPane.widthProperty());
            container.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            container.setAlignment(Pos.CENTER);
            container.setBorder(new Border(new BorderStroke(Color.ORANGERED, Color.ORANGERED, Color.ORANGERED, Color.ORANGERED,
                    BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY)));
            container.setOnMouseClicked(event -> {
                if(e instanceof Track) {
                    simulation.addTracks(((Track) e).clone());
                    simPane.getChildren().clear();
                    drawAllShapes();
                }
                else if(e instanceof Marble)
                    throw new NotImplementedException();
            });

            if (e instanceof Track) {
                Track st = (Track) e;


                double offsetX = 0.2;
                double offsetY = -0.1;
                Shape line = new Line(
                        -1 * (st.maxBound() - st.minBound()) + offsetX,
                        -1 * (-st.heightAt(st.maxBound()) + st.heightAt(st.minBound())) + offsetY,
                        0.5 * (st.maxBound() - st.minBound()) + offsetX,
                        0.5 * (-st.heightAt(st.maxBound()) + st.heightAt(st.minBound())) + offsetY
                );

                line.getTransforms().add(new Scale(0.5, 0.5));
                line.setStrokeWidth(0.02);
                line.setStroke(Color.RED);
                line.getTransforms().add(simSceneScale);
                line.toFront();

                container.getChildren().add(line);
            }

            elementsBox.getChildren().add(container);
        }
    }

    private class ControlPanel {
        private VBox elementsBox;
        private ScrollPane elementsScrollPane;
        private VBox controlPanel;

        public VBox getElementsBox() {
            return elementsBox;
        }

        public ScrollPane getElementsScrollPane() {
            return elementsScrollPane;
        }

        public VBox getControlPanel() {
            return controlPanel;
        }

        public ControlPanel invoke() {
            Text controlsText = new Text("Controls");
            controlsText.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 22));
            controlsText.setFill(Color.DARKORANGE);


            Region spacer1 = new Region();
            spacer1.setPrefHeight(20);


            Text elementsText = new Text("Elements");
            elementsText.setFont(Font.font(18));
            elementsText.setFill(Color.DARKORANGE);


            Region spacer5 = new Region();
            spacer5.setMinHeight(20);


            elementsBox = new VBox();
            elementsBox.setFillWidth(true);
            VBox.setVgrow(elementsBox, Priority.ALWAYS);
            VBox.setMargin(elementsBox, new Insets(10, 0, 10, 0));
            elementsBox.setPrefWidth(250);


            elementsScrollPane = new ScrollPane();
            elementsScrollPane.setContent(elementsBox);
            elementsScrollPane.setBackground(Background.EMPTY);
            elementsScrollPane.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY, new BorderWidths(3))));
            elementsScrollPane.setMaxHeight(400);
            elementsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


            Text sliderSpeedText = new Text("Speed");
            sliderSpeedText.setFont(new Font(18));
            sliderSpeedText.setFill(Color.DARKORANGE);


            DropShadow ds = new DropShadow();
            ds.setBlurType(BlurType.GAUSSIAN);
            ds.setColor(Color.BLACK);
            ds.setOffsetX(3);
            ds.setOffsetY(3);

            Button startButton = new Button("Start/Unpause");
            startButton.setPrefWidth(200);
            startButton.setMaxHeight(40);
            startButton.setPadding(new Insets(15, 0, 15, 0));
            startButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
            startButton.setEffect(ds);


            Region spacer2 = new Region();
            spacer2.setPrefHeight(10);


            Button resetButton = new Button("Reset");
            resetButton.setPrefWidth(200);
            resetButton.setPrefHeight(40);
            resetButton.setEffect(ds);
            resetButton.setPadding(new Insets(15, 0, 15, 0));
            resetButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));


            Region spacer3 = new Region();
            spacer3.setPrefHeight(50);


            controlPanel = new VBox();
            controlPanel.setAlignment(Pos.BASELINE_CENTER);
            controlPanel.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            controlPanel.setPadding(new Insets(10, 75, 10, 75));
            controlPanel.setBorder(new Border(new BorderStroke(null, null, null, Color.DARKORANGE,
                    BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY, new BorderWidths(5), Insets.EMPTY)));
            controlPanel.setFillWidth(true);
            controlPanel.getChildren().addAll(controlsText, spacer1, elementsText, spacer5, elementsBox, elementsScrollPane,
                    sliderSpeedText, startButton, spacer2, resetButton, spacer3);


            defineButtonEvents(ds, startButton, resetButton, elementsBox);


            return this;
        }
    }

    private class SimulationPanel {
        private final ToolBar toolBar;
        private final VBox controlPanel;
        private Pane simPane;
        private BorderPane borderPane;

        public SimulationPanel(ToolBar toolBar, VBox controlPanel) {
            this.toolBar = toolBar;
            this.controlPanel = controlPanel;
        }

        public Pane getSimPane() {
            return simPane;
        }

        public BorderPane getBorderPane() {
            return borderPane;
        }

        public SimulationPanel invoke() {
            TextField textField = new TextField();
            textField.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    parseInput(textField.getText());
                    textField.clear();
                }
            });


            simPane = new Pane();
            MainApplication.simPane = simPane;
            simPane.setPrefHeight(simPanelSizeY);
            simPane.setPrefWidth(simPanelSizeX);
            simPane.setMouseTransparent(false);
            simPane.setPickOnBounds(false);
            simPane.toBack();


            BorderPane simBorderPane = new BorderPane();
            simBorderPane.setCenter(simPane);
            simBorderPane.setBottom(textField);


            borderPane = new BorderPane();
            borderPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            borderPane.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY, new BorderWidths(5), Insets.EMPTY)));
            borderPane.setTop(toolBar);
            borderPane.setRight(controlPanel);
            borderPane.setCenter(simBorderPane);
            return this;
        }

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
    }

    private static void defineTrackDragNDrop(Track st, Shape line) {
        final double[] orgSceneX = new double[1];
        final double[] orgSceneY = new double[1];
        line.setOnMousePressed(event -> {
            if (simulation.isPaused()) {
                orgSceneX[0] = event.getSceneX();
                orgSceneY[0] = event.getSceneY();
                ((Line) event.getSource()).toFront();
            }
        });
        line.setOnMouseDragged(event -> {
            if (simulation.isPaused() && event.getSource() instanceof Shape) {
                double offsetX = (event.getSceneX() - orgSceneX[0]) / simSceneScale.getX();
                double offsetY = (event.getSceneY() - orgSceneY[0]) / simSceneScale.getY();
                Line c1 = (Line) (event.getSource());

                c1.setStartX((c1.getStartX() + offsetX));
                c1.setStartY((c1.getStartY() + offsetY));
                c1.setEndX(c1.getEndX() + offsetX);
                c1.setEndY(c1.getEndY() + offsetY);


                orgSceneX[0] = event.getSceneX();
                orgSceneY[0] = event.getSceneY();

                st.setPoints(
                        new Vec3d(c1.getStartX() + offsetX, 0, -c1.getStartY() - offsetY),
                        new Vec3d(c1.getEndX() + offsetX, 0, -c1.getEndY() - offsetY)
                );
            }
        });
    }

    private void defineButtonEvents(DropShadow ds, Button startButton, Button resetButton, VBox elementsBox ) {



        startButton.setOnMouseEntered(event -> startButton.setBackground(new Background(new BackgroundFill(Color.ORANGERED, CornerRadii.EMPTY, Insets.EMPTY))));
        startButton.setOnMouseExited(event -> startButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY))));
        startButton.setOnMousePressed((e) -> {
            startButton.setEffect(null);
        });
        startButton.setOnMouseReleased((e) -> {
            startButton.setEffect(ds);
        });
        startButton.setOnAction(event -> {

            if (simulation.isPaused()) {
                simulation.setPaused(false);
                startButton.setText("Pause");
                Utils.setElementsColorDisabled(elementsBox);
            } else {
                simulation.setPaused(true);
                startButton.setText("Start/Unpause");
                Utils.setElementsColorEnabled(elementsBox);
            }

        });


        resetButton.setOnAction(event -> {
            reset();
            simulation.setPaused(true);
            startButton.setText("Start/Unpause");
            Utils.setElementsColorEnabled(elementsBox);
        });

        resetButton.setOnMousePressed((e) -> {
            resetButton.setEffect(null);
        });
        resetButton.setOnMouseReleased((e) -> {
            resetButton.setEffect(ds);
        });
        resetButton.setOnMouseEntered(event -> resetButton.setBackground(new Background(new BackgroundFill(Color.ORANGERED, CornerRadii.EMPTY, Insets.EMPTY))));
        resetButton.setOnMouseExited(event -> resetButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY))));
    }


    ////////////////////////////////////////
    /*            Draw Methods            */
    ////////////////////////////////////////


    private static void drawAllShapes() {
        for (Track t : simulation.getTracks()) {
            drawSimpleTrack(t, Color.RED);
        }

        for (Entity e : simulation.getEntities()) {
            if (e instanceof Sphere) {
                drawSphere((Sphere) e, Color.LIGHTSKYBLUE);
            } else if (e instanceof Rectangle) {
                //drawRectangle((Rectangle) e, Color.DARKOLIVEGREEN);
            } else {
                System.err.println("Entity is not valid!");
            }
        }
    }

    private static void drawSphere(Sphere s, Color c) {
        Circle circle = new Circle(s.getPos().x, (s.getPos().z) * -1,
                s.getDiameter() / 2, c);

        simPane.getChildren().add(circle);
        circle.getTransforms().add(simSceneScale);

        final double[] orgSceneX = new double[1];
        final double[] orgSceneY = new double[1];
        circle.setOnMousePressed(event -> {
            if (simulation.isPaused()) {
                orgSceneX[0] = event.getSceneX();
                orgSceneY[0] = event.getSceneY();
                ((Circle) event.getSource()).toFront();
            }
        });
        circle.setOnMouseDragged(event -> {
            if (simulation.isPaused() && event.getSource() instanceof Shape) {
                double offsetX = (event.getSceneX() - orgSceneX[0]) / simSceneScale.getX();
                double offsetY = (event.getSceneY() - orgSceneY[0]) / simSceneScale.getY();
                Circle c1 = (Circle) (event.getSource());

                c1.setCenterX((c1.getCenterX() + offsetX));
                c1.setCenterY((c1.getCenterY() + offsetY));


                orgSceneX[0] = event.getSceneX();
                orgSceneY[0] = event.getSceneY();

                s.setPos(new Vec3d(c1.getCenterX(), 0, (c1.getCenterY()) * -1));
            }
        });
    }

    private static void drawSimpleTrack(Track st, Color c) {
        Shape line = st.intoShape(c);

        simPane.getChildren().add(line);
        line.getTransforms().add(simSceneScale);

        defineTrackDragNDrop(st, line);
    }

}
