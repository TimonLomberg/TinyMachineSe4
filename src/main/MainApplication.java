package main;

import entities.*;
import entities.Rectangle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
import misc.Vec3d;

import java.util.ArrayList;
import java.util.HashMap;


public class MainApplication extends Application {


    ////////////////////////////////////////
    /*        Simulation Parameters       */
    ////////////////////////////////////////


    static Simulation simulation; // Don't edit!!

    private static final double tickFrequency = 200;
    private static final double canvasScaleX = 200;
    private static final double canvasScaleY = 200;

    private static final double simPanelSizeX = 1000f;
    private static final double simPanelSizeY = 800f;
    private static final Scale simSceneScale = new Scale(200,200);


    ////////////////////////////////////////
    /*          Simulation Members        */
    ////////////////////////////////////////


    private static Marble marble1, marble2;
    private static Track track1, track2;

    private static ArrayList<Drawable> samples = new ArrayList<>();


    ////////////////////////////////////////
    /*         Simulation Building        */
    ////////////////////////////////////////


    public void initialize() {
        simulation = new Simulation();
        reset();
    }

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
        // marble2.setVelo(new Vec3d(1,0,0));

    }

    private void reset() {
        //gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        simPane.getChildren().clear();
        simulation.clearEntities();
        simulation.clearTracks();
        buildSimulation();
        drawAllShapesNew();
        clipChildren(simPane);
    }


    ////////////////////////////////////////
    /*             Horrible Program Flow  */
    ////////////////////////////////////////


    static GraphicsContext gc; // Don't edit!!
    static Pane simPane;


    double[] simCoordToDrawCoord(double x, double y) {
        return new double[]{ x, -y };
    }


    @Override
    public void start(Stage primaryStage) {

        samples.add(new Track(-1.2, -0.1, new double[]{0.1, 1.0}));
        samples.add(new Track(-1, 0.1, new double[]{0.1, 1.0}));
        samples.add(new Track(-1, 1, new double[]{0.1, 0.5}));




        Text windowText = new Text("Rolling Stones");
        windowText.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, FontPosture.ITALIC,20));

        HBox p = new HBox();
        HBox.setHgrow(p, Priority.ALWAYS);

        ToolBar toolBar = new ToolBar();
        int height = 30;
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);

        toolBar.getItems().addAll(windowText, p);


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


        VBox elementsBox = new VBox();
        elementsBox.setFillWidth(true);
        VBox.setVgrow(elementsBox, Priority.ALWAYS);
        VBox.setMargin(elementsBox, new Insets(10,0,10, 0));
        elementsBox.setPrefWidth(250);


        ScrollPane elementsScrollPane = new ScrollPane();
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
        startButton.setPadding(new Insets(15,0,15,0));
        startButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        startButton.setEffect(ds);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(simulation.isPaused())  {
                    simulation.setPaused(false);
                    startButton.setText("Pause");
                    setElementsColorDisabled(elementsBox);
                } else {
                    simulation.setPaused(true);
                    startButton.setText("Start/Unpause");
                    setElementsColorEnabled(elementsBox);
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
        startButton.setOnMousePressed((e) ->  {
            startButton.setEffect(null);
        });
        startButton.setOnMouseReleased((e) -> {
            startButton.setEffect(ds);
        });


        Region spacer2 = new Region();
        spacer2.setPrefHeight(10);


        Button resetButton = new Button("Reset");
        resetButton.setPrefWidth(200);
        resetButton.setPrefHeight(40);
        resetButton.setEffect(ds);
        resetButton.setPadding(new Insets(15,0,15,0));
        resetButton.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reset();
                simulation.setPaused(true);
                startButton.setText("Start/Unpause");
                setElementsColorEnabled(elementsBox);
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
        resetButton.setOnMousePressed((e) ->  {
            resetButton.setEffect(null);
        });
        resetButton.setOnMouseReleased((e) -> {
            resetButton.setEffect(ds);
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
        controlPanel.getChildren().addAll(controlsText, spacer1, elementsText, spacer5, elementsBox, elementsScrollPane, sliderSpeedText, startButton, spacer2, resetButton, spacer3);


        TextField textField = new TextField();
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                parseInput(textField.getText());
                textField.clear();
            }
        });



        Pane simPane = new Pane();
        MainApplication.simPane = simPane;
        simPane.setPrefHeight(simPanelSizeY);
        simPane.setPrefWidth(simPanelSizeX);
        simPane.setMouseTransparent(false);
        simPane.setPickOnBounds(false);
        simPane.toBack();


        BorderPane simBorderPane = new BorderPane();
        simBorderPane.setCenter(simPane);
        simBorderPane.setBottom(textField);



        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setBorder(new Border(new BorderStroke(Color.DARKORANGE, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(5), Insets.EMPTY)));
        borderPane.setTop(toolBar);
        borderPane.setRight(controlPanel);
        borderPane.setCenter(simBorderPane);


        for(Drawable e : samples) {
            final double psm = 0.5;

            // javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(elementsBox.getPrefWidth(), 100);

            StackPane container = new StackPane();
            container.setMinHeight(100);
            container.prefWidthProperty().bind(elementsScrollPane.widthProperty());
            container.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            container.setAlignment(Pos.CENTER);
            container.setBorder(new Border(new BorderStroke(Color.ORANGERED, Color.ORANGERED, Color.ORANGERED, Color.ORANGERED,
                    BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY)));

            container.setOnMouseClicked((a) -> {
                if (simulation.isPaused()) {
                    System.out.println("test");

                    if(e instanceof Track) {
                        simulation.addTracks(((Track) e).clone());
                        simPane.getChildren().clear();
                        drawAllShapesNew();
                    } else if(e instanceof Marble) {

                    }
                }

            });

            if(e instanceof Track) {
                Track st = (Track) e;


                double offsetX = 0.2;
                double offsetY = -0.1;
                Shape line = new Line(
                        -1*(st.maxBound() - st.minBound()) + offsetX,
                        -1*(-st.heightAt(st.maxBound()) + st.heightAt(st.minBound())) + offsetY,
                        0.5*(st.maxBound() - st.minBound()) + offsetX,
                        0.5*(-st.heightAt(st.maxBound()) + st.heightAt(st.minBound())) + offsetY
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



        Group root = new Group();
        root.getChildren().add(borderPane);





        Scene scene = new Scene(root);
        primaryStage.setTitle("Rolling Stones");
        primaryStage.setScene(scene);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.setResizable(false);

        //canvas.getGraphicsContext2D().scale(canvasScaleX, canvasScaleY);
        //gc = canvas.getGraphicsContext2D();


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


       // gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        simPane.getChildren().clear();
        drawAllShapesNew();


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

    private static void drawSphereNew(Sphere s, Color c) {
        Circle circle = new Circle(s.getPos().x, (s.getPos().z)*-1,
                s.getDiameter()/2, c);

        simPane.getChildren().add(circle);
        circle.getTransforms().add(simSceneScale);

        final double[] orgSceneX = new double[1];
        final double[] orgSceneY = new double[1];
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (simulation.isPaused()) {
                    orgSceneX[0] = event.getSceneX();
                    orgSceneY[0] = event.getSceneY();
                    ((Circle) event.getSource()).toFront();
                }
            }
        });
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (simulation.isPaused() && event.getSource() instanceof Shape) {
                    double offsetX = (event.getSceneX() - orgSceneX[0]) / simSceneScale.getX();
                    double offsetY = (event.getSceneY() - orgSceneY[0]) / simSceneScale.getY();
                    Circle c = (Circle) (event.getSource());

                    c.setCenterX((c.getCenterX() + offsetX));
                    c.setCenterY((c.getCenterY() + offsetY));

                    //c.setTranslateX(offsetX);
                    //c.setTranslateY(offsetY);

                    //c.relocate(c.getCenterX() + offsetX, c.getCenterY() + offsetY);

                    orgSceneX[0] = event.getSceneX();
                    orgSceneY[0] = event.getSceneY();

                    s.setPos(new Vec3d(c.getCenterX() , 0, (c.getCenterY())*-1));
                }
            }
        });


    }

    private static void drawRectangle(Rectangle r, Color c) {
        Paint old = gc.getFill();
        gc.setFill(c);

        //TODO Rectangle drawing

        gc.setFill(old);
    }

    private static void drawSimpleTrack(Track st, Color c) {
        Paint old = gc.getStroke();
        gc.setStroke(c);
        gc.setLineWidth(0.01);

        gc.strokeLine(st.minBound(), -st.heightAt(st.minBound()),
                st.maxBound(), -st.heightAt(st.maxBound()));

        gc.setStroke(old);
    }

    private static void drawSimpleTrackNew(Track st, Color c) {
        Shape line = st.intoShape(c);

        simPane.getChildren().add(line);
        line.getTransforms().add(simSceneScale);

        final double[] orgSceneX = new double[1];
        final double[] orgSceneY = new double[1];
        line.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (simulation.isPaused()) {
                    orgSceneX[0] = event.getSceneX();
                    orgSceneY[0] = event.getSceneY();
                    ((Line) event.getSource()).toFront();
                }
            }
        });
        line.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (simulation.isPaused() && event.getSource() instanceof Shape) {
                    double offsetX = (event.getSceneX() - orgSceneX[0]) / simSceneScale.getX();
                    double offsetY = (event.getSceneY() - orgSceneY[0]) / simSceneScale.getY();
                    Line c = (Line) (event.getSource());

                    c.setStartX((c.getStartX() + offsetX));
                    c.setStartY((c.getStartY() + offsetY));
                    c.setEndX(c.getEndX() + offsetX);
                    c.setEndY(c.getEndY() + offsetY);

                    //c.setTranslateX(offsetX);
                    //c.setTranslateY(offsetY);

                    //c.relocate(c.getCenterX() + offsetX, c.getCenterY() + offsetY);

                    orgSceneX[0] = event.getSceneX();
                    orgSceneY[0] = event.getSceneY();

                    st.setMinBound(st.minBound() + offsetX);
                    st.setMaxBound(st.maxBound() + offsetX);
                    st.setZOffset(st.zOffset() - offsetY);
                }
            }
        });
    }

    private static void drawAllShapes() {

        for(Track t : simulation.getTracks()) {
            drawSimpleTrack((Track) t, Color.RED);
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

    private static void drawAllShapesNew() {
        for(Track t : simulation.getTracks()) {
            drawSimpleTrackNew((Track) t, Color.RED);
        }

        for (Entity e : simulation.getEntities()) {
            if (e instanceof Sphere) {
                drawSphereNew((Sphere) e, Color.LIGHTSKYBLUE);
            } else if (e instanceof Rectangle) {
                //drawRectangle((Rectangle) e, Color.DARKOLIVEGREEN);
            } else  {
                System.err.println("Entity is not valid!");
            }
        }
    }

    private static void clipChildren(Region region) {
        final javafx.scene.shape.Rectangle clippingPlane = new javafx.scene.shape.Rectangle(simPanelSizeX,simPanelSizeY);
        region.setClip(clippingPlane);



    }

    private static void setElementsColorDisabled(VBox p) {
        for(Node n : p.getChildren()) {
            StackPane h = (StackPane) n;

            h.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
    private static void setElementsColorEnabled(VBox p) {
        for(Node n : p.getChildren()) {
            StackPane h = (StackPane) n;

            h.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }


}
