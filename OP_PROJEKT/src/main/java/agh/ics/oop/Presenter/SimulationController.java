package agh.ics.oop.Presenter;

import agh.ics.oop.Animals.AnimalVersion;
import agh.ics.oop.Maps.MapVersion;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulationController {

    @FXML
    private Spinner<Integer> initialAnimalSpinner;
    @FXML
    private Spinner<Integer> genomeLenghtSpinner;
    @FXML
    private Spinner<Integer> maxMutationsSpinner;
    @FXML
    private Spinner<Integer> minMutationsSpinner;
    @FXML
    private Spinner<Integer> energyForBreedingSpinner;
    @FXML
    private Spinner<Integer> energyNeededToBreed;
    @FXML
    private Spinner<Integer> initialAnimalEnegrySpinner;
    @FXML
    private Spinner<Integer> mapHeightSpinner;
    @FXML
    private Spinner<Integer> mapWidthSpinner;
    @FXML
    private Spinner<Integer> initialPlantsSpinner;
    @FXML
    private Spinner<Integer> newPlantsSpinner;
    @FXML
    private Spinner<Integer> plantEnergySpinner;
    @FXML
    private ComboBox<MapVersion> plantsComboBox;
    @FXML
    private ComboBox<AnimalVersion> animalComboBox;


    private int numberOfSimulations;

    public void onSimulationStartClicked() throws Exception{
        Stage simStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        numberOfSimulations++;
        configureStage(simStage, viewRoot);
        simStage.show();

        Simulation simulation = createNewSimulation();
        presenter.setSimulation(simulation);
        simulation.addMapObserver(presenter);
        presenter.setMap(simulation.getMap());

        SimulationEngine simulationEngine = new SimulationEngine();
        presenter.setSimulationEngine(simulationEngine);
        simulationEngine.runSim(simulation);
        // TODO fileoutput observer



    }

//TODO sprawdzanie poprawności parametrów?
    private Simulation createNewSimulation(){
        int mapHeight = mapHeightSpinner.getValue();
        int mapWidth = mapWidthSpinner.getValue();
        MapVersion mapVersion = plantsComboBox.getValue();
        AnimalVersion animalVersion = animalComboBox.getValue();
        int newPlantsEveryDay = newPlantsSpinner.getValue();
        int startPlants = initialPlantsSpinner.getValue();
        int startAnimals = initialAnimalSpinner.getValue();
        int startEnergy = initialAnimalEnegrySpinner.getValue();
        int plantEnergy = plantEnergySpinner.getValue();
        int energyToBreed = energyNeededToBreed.getValue();
        int energyLostForBreeding = energyForBreedingSpinner.getValue();
        int minMutations = minMutationsSpinner.getValue();
        int maxMutations = maxMutationsSpinner.getValue();
        int genomeLength = genomeLenghtSpinner.getValue();


        return new Simulation(mapHeight, mapWidth, mapVersion, animalVersion,
                newPlantsEveryDay, startPlants, startAnimals, startEnergy,
                plantEnergy, energyToBreed, energyLostForBreeding,
                minMutations, maxMutations, genomeLength);
    }

    private void configureStage(Stage simStage, BorderPane viewRoot){
        var scene = new Scene(viewRoot);
        simStage.setScene(scene);
        simStage.setTitle("Simulation " + numberOfSimulations);
        simStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        simStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void setComboBoxes(){
        animalComboBox.getItems().add(AnimalVersion.BASIC);
        animalComboBox.getItems().add(AnimalVersion.BACK_AND_FORTH);
        plantsComboBox.getItems().add(MapVersion.RAIN_FOREST);
        plantsComboBox.getItems().add(MapVersion.LIFE_GIVING_CORPSES);

        animalComboBox.setValue(AnimalVersion.BASIC);
        plantsComboBox.setValue(MapVersion.RAIN_FOREST);

        // loadConfig
    }
}
