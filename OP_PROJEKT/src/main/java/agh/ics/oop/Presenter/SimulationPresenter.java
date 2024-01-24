package agh.ics.oop.Presenter;

import agh.ics.oop.*;
import agh.ics.oop.Animals.AbstractAnimal;
import agh.ics.oop.Maps.WorldMap;
import agh.ics.oop.Observers.MapChangeListener;
import agh.ics.oop.Observers.SimulationChangeListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class SimulationPresenter implements MapChangeListener, SimulationChangeListener {

    @FXML
    private Label animalGenomeLabel;
    @FXML
    private Label animalEnergyLabel;
    @FXML
    private Label animalChildrenLabel;
    @FXML
    private Label animalPlantsLabel;
    @FXML
    private Label animalGeneLabel;
    @FXML
    private Label animalDeathLabel;
    @FXML
    private Label animalAgeLabel;
    @FXML
    private Label animalDescendantsLabel;
    @FXML
    private Button showPrefPositionsButton;
    @FXML
    private Button showPopularGenomeButton;
    @FXML
    private Button unfollowAnimalButton;
    @FXML
    private Label popularGenomeLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label plantsLabel;
    @FXML
    private Label freeFieldsLabel;
    @FXML
    private Label animalsLabel;
    @FXML
    private Label childCountLabel;
    @FXML
    private Label lifetimeLabel;
    @FXML
    private Label energyLevelLabel;
    @FXML
    private Slider speedSlider;
    @FXML
    private Button pauseButton;
    @FXML
    private GridPane mapGrid;
    private Simulation simulation;
    private SimulationEngine simulationEngine;
    private WorldMap map;
    private AbstractAnimal followedAnimal = null;
    private MapDisplay mapDisplay;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMap(WorldMap map){
        this.map = map;
        this.mapDisplay = new MapDisplay(mapGrid, map, this);
    }


    private void updateStatistics(int day){
        titleLabel.setText(String.format("DARWIN WORLD SIMULATION DAY %d", day));
        plantsLabel.setText("" + map.getPlantCount());
        animalsLabel.setText("" + map.getAnimalCount());
        freeFieldsLabel.setText("" + map.getFreeFields());
        childCountLabel.setText("" + map.getAverageChildCount());
        lifetimeLabel.setText("" + map.getAverageDaysLived());
        energyLevelLabel.setText("" + map.getAverageEnergy());
        popularGenomeLabel.setText("" + map.getMostPopularGenotype());
    }

    private void updateAnimalStatistics(){
        animalGenomeLabel.setText("" + followedAnimal.getGenes());
        int currGene = followedAnimal.getCurrentGene();
        animalGeneLabel.setText(String.format("%d   (%d)", followedAnimal.getGenes().get(currGene), currGene));
        animalEnergyLabel.setText("" + followedAnimal.getEnergy());
        animalPlantsLabel.setText("" + followedAnimal.getPlantsEaten());
        animalChildrenLabel.setText("" + followedAnimal.getNumberOfChildren());
        animalDescendantsLabel.setText("" + followedAnimal.getOffspringCount());
        animalAgeLabel.setText("" + followedAnimal.getDaysAlive());
        if (followedAnimal.getDayOfDeath() == -1) animalDeathLabel.setText("-");
        else animalDeathLabel.setText("" + followedAnimal.getDayOfDeath());

    }

    @Override
    public void mapChanged(WorldMap worldMap, int maxEnergy) {
        Platform.runLater(() -> {
            mapDisplay.drawMap(followedAnimal, maxEnergy);
            freeFieldsLabel.setText("" + map.getFreeFields());
        });

    }

    @Override
    public void dayChanged(WorldMap worldMap, int day) {
        Platform.runLater(() -> updateStatistics(day));
        if(followedAnimal != null) Platform.runLater(this::updateAnimalStatistics);
    }

    public void onSimulationPauseClicked(){
        if(pauseButton.getText().equals("Pause")){
            simulation.pauseSimulation();
            pauseButton.setText("Resume");
            showPopularGenomeButton.setVisible(true);
            showPrefPositionsButton.setVisible(true);
        }
        else{
            simulation.resumeSimulation();
            pauseButton.setText("Pause");
            showPrefPositionsButton.setText("Show plant preferred positions");
            showPopularGenomeButton.setText("Show animals with the dominating genome");
            showPopularGenomeButton.setVisible(false);
            showPrefPositionsButton.setVisible(false);
        }
    }

    public void onShowPrefPositionsClicked(){
        if(showPrefPositionsButton.getText().startsWith("S")){
            showPrefPositionsButton.setText("Hide plant preferred positions");
            showPopularGenomeButton.setText("Show animals with the dominating genome");
            Platform.runLater(() -> mapDisplay.drawMapWithPrefPositions(followedAnimal, map.getMaxEnergy()));
        }
        else{
            Platform.runLater(() -> mapDisplay.drawMap(followedAnimal, map.getMaxEnergy()));
            showPrefPositionsButton.setText("Show plant preferred positions");
        }
    }

    public void onShowPopularGenomeClicked(){
        if(showPopularGenomeButton.getText().startsWith("S")){
            showPrefPositionsButton.setText("Show plant preferred positions");
            showPopularGenomeButton.setText("Hide animals with the dominating genome");
            Platform.runLater(() -> mapDisplay.drawMapWithPopularGenome(followedAnimal, map.getMaxEnergy()));
        }
        else{
            Platform.runLater(() -> mapDisplay.drawMap(followedAnimal, map.getMaxEnergy()));
            showPopularGenomeButton.setText("Show animals with the dominating genome");
        }
    }

    public void simulationEnd(){
        simulation.stopSimulation();
        simulationEngine.awaitSimulationEnd();
    }

    public void onSliderChanged(){
        simulation.setCurrDelay((int) speedSlider.getValue());
    }

    public void onUnfollowButtonClicked(){
        followedAnimal = null;
        unfollowAnimalButton.setVisible(false);
        animalGenomeLabel.setText("");
        animalGeneLabel.setText("");
        animalEnergyLabel.setText("");
        animalPlantsLabel.setText("");
        animalChildrenLabel.setText("");
        animalDescendantsLabel.setText("");
        animalAgeLabel.setText("");
        animalDeathLabel.setText("");
        Platform.runLater(() -> mapDisplay.drawMap(followedAnimal, map.getMaxEnergy()));
        showPrefPositionsButton.setText("Show plant preferred positions");
        showPopularGenomeButton.setText("Show animals with the dominating genome");
    }

    public void followAnimal(ImageView clickedImageView){
        if(pauseButton.getText().equals("Resume")){
            int col = GridPane.getColumnIndex(clickedImageView);
            int row = map.getHeight() - GridPane.getRowIndex(clickedImageView);
            Optional<AbstractAnimal> optFollowedAnimal = map.getStrongestAnimal(new Vector2d(col, row));
            if (optFollowedAnimal.isPresent()) {
                followedAnimal = optFollowedAnimal.get();
                unfollowAnimalButton.setVisible(true);
                showPrefPositionsButton.setText("Show plant preferred positions");
                showPopularGenomeButton.setText("Show animals with the dominating genome");
                Platform.runLater(this::updateAnimalStatistics);
                Platform.runLater(() -> mapDisplay.drawMap(followedAnimal, map.getMaxEnergy()));
            }
        }
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void setSimulationEngine(SimulationEngine simulationEngine) {
        this.simulationEngine = simulationEngine;
    }

}
