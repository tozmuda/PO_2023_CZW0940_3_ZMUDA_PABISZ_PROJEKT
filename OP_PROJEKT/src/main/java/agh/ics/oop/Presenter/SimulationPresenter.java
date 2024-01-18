package agh.ics.oop.Presenter;

import agh.ics.oop.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.util.Optional;

import static java.lang.Math.round;

public class SimulationPresenter implements MapChangeListener {

    @FXML
    private Slider speedSlider;
    @FXML
    private Button pauseButton;
    @FXML
    private GridPane mapGrid;
    private Simulation simulation;
    private SimulationEngine simulationEngine;
    private WorldMap map;
    private ImageGenerator imageGenerator;

    public void setMap(WorldMap map){
        this.map = map;
        int w = (int) round(mapGrid.getWidth()) - 10;
        int h = (int) round(mapGrid.getHeight());
        int cellSize = h / (map.getHeight() + 1);
        if (cellSize > w / (map.getWidth() + 1)) cellSize = w / (map.getWidth() + 1);
        imageGenerator = new ImageGenerator(cellSize - 1, (map.getWidth() + 1) * (map.getHeight() + 1), map.getAnimalCount(), map.getPlantCount());
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
//
//    private ImageView getFieldImageView(int image_size) {
//        Image image = new Image("images/field.png");
//        ImageView fieldImageView = new ImageView(image);
//        fieldImageView.setFitHeight(image_size);
//        fieldImageView.setFitWidth(image_size);
//        return fieldImageView;
//    }

    private ImageView getObjectDraw(Vector2d currentPosition,
                                    int maxEnergy) {
        Optional<WorldElement> opt = map.objectAt(currentPosition);
        String imageName = "images/field.png";
        if (opt.isPresent()) {
            imageName = opt.get().getImageName(maxEnergy);
        }
        return imageGenerator.getImageView(imageName, simulation.getDays());
    }

    public void drawMap(){
        clearGrid();
        int maxEnergy = map.getMaxEnergy();
        int height = map.getHeight();
        int width = map.getWidth();
        int w = (int) round(mapGrid.getWidth()) - 10;
        int h = (int) round(mapGrid.getHeight());

        int cellSize = h / (height + 1);
        if (cellSize > w / (width + 1)) cellSize = w / (width + 1);


        for(int c = 0; c <= width; c++){
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellSize));
        }

        for(int r = 0; r <= height; r++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellSize));
        }

        for(int r = 0; r <= height; r++){
            for(int c = 0; c <= width; c++){
                ImageView objectView = getObjectDraw(new Vector2d(c, height - r), maxEnergy);
                mapGrid.add(objectView, c, r, 1, 1);
                GridPane.setHalignment(objectView, HPos.CENTER);
                GridPane.setValignment(objectView, VPos.CENTER);
            }
        }

    }
    @Override
    public void mapChanged(WorldMap worldMap) {
        Platform.runLater(this::drawMap);
    }

    public void onSimulationPauseClicked(){
        if(pauseButton.getText().equals("Pause")){
            simulation.pauseSimulation();
            pauseButton.setText("Resume");
        }
        else{
            simulation.resumeSimulation();
            pauseButton.setText("Pause");
        }
    }

    public void onSimulationStopClicked(){
        simulation.stopSimulation();
        simulationEngine.awaitSimulationEnd();
    }

    public void onSliderChanged(){
        simulation.setCurrDelay((int) speedSlider.getValue());
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void setSimulationEngine(SimulationEngine simulationEngine) {
        this.simulationEngine = simulationEngine;
    }
}
