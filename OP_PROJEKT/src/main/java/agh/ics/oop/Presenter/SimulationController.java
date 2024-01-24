package agh.ics.oop.Presenter;

import agh.ics.oop.*;
import agh.ics.oop.Animals.AnimalVersion;
import agh.ics.oop.Exceptions.FilenameAlreadyInUseException;
import agh.ics.oop.Exceptions.IncorrectParametersException;
import agh.ics.oop.Maps.MapVersion;
import agh.ics.oop.Observers.FileOutput;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class SimulationController {

    private final ConfigurationHandler configHandler = new ConfigurationHandler();
    private final Map<Integer, String> activeFilenames = new HashMap<>();
    @FXML
    private ComboBox<String> loadConfigComboBox;
    @FXML
    private TextField configNameTextField;
    @FXML
    private  CheckBox saveStatisticsCheckBox;
    @FXML
    private TextField filenameTextField;
    @FXML
    private Spinner<Integer> initialAnimalSpinner;
    @FXML
    private Spinner<Integer> genomeLengthSpinner;
    @FXML
    private Spinner<Integer> maxMutationsSpinner;
    @FXML
    private Spinner<Integer> minMutationsSpinner;
    @FXML
    private Spinner<Integer> energyForBreedingSpinner;
    @FXML
    private Spinner<Integer> energyNeededToBreed;
    @FXML
    private Spinner<Integer> initialAnimalEnergySpinner;
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
        try {
            Simulation simulation = createNewSimulation();
            if (saveStatisticsCheckBox.isSelected()) addFileObserver(simulation);

            SimulationPresenter presenter = configureSimulationStage();

            presenter.setSimulation(simulation);
            simulation.addMapObserver(presenter);
            presenter.setMap(simulation.getMap());
            simulation.addObserver(presenter);

            SimulationEngine simulationEngine = new SimulationEngine(simulation);
            presenter.setSimulationEngine(simulationEngine);

            simulationEngine.runSim();
        }
        catch (IncorrectParametersException e){
            showAlert(e.getMessage(), "INCORRECT SIMULATION PARAMETERS");
        }
        catch (FilenameAlreadyInUseException e){
            showAlert(e.getMessage(), "FILENAME ALREADY IN USE");
        }
    }

    private void addFileObserver(Simulation simulation) throws FilenameAlreadyInUseException{
        String filename = filenameTextField.getText();
        if (activeFilenames.containsValue(filename)){
            throw new FilenameAlreadyInUseException("One of running simulations already uses filename: " + filename);
        }
        activeFilenames.put(numberOfSimulations, filename);
        FileOutput fileOutput = new FileOutput(filename);
        simulation.addObserver(fileOutput);
    }

    private SimulationParameters getSimParameters(){
        int mapHeight = mapHeightSpinner.getValue();
        int mapWidth = mapWidthSpinner.getValue();
        MapVersion mapVersion = plantsComboBox.getValue();
        AnimalVersion animalVersion = animalComboBox.getValue();
        int newPlantsEveryDay = newPlantsSpinner.getValue();
        int startPlants = initialPlantsSpinner.getValue();
        int startAnimals = initialAnimalSpinner.getValue();
        int startEnergy = initialAnimalEnergySpinner.getValue();
        int plantEnergy = plantEnergySpinner.getValue();
        int energyToBreed = energyNeededToBreed.getValue();
        int energyLostForBreeding = energyForBreedingSpinner.getValue();
        int minMutations = minMutationsSpinner.getValue();
        int maxMutations = maxMutationsSpinner.getValue();
        int genomeLength = genomeLengthSpinner.getValue();

        return new SimulationParameters(mapHeight, mapWidth, mapVersion, animalVersion,
                newPlantsEveryDay, startPlants, startAnimals, startEnergy, plantEnergy, energyToBreed, energyLostForBreeding,
                minMutations, maxMutations, genomeLength);

    }


    private void setSimParameters(SimulationParameters simParameters){
        mapHeightSpinner.getValueFactory().setValue(simParameters.mapHeight());
        mapWidthSpinner.getValueFactory().setValue(simParameters.mapWidth());
        plantsComboBox.setValue(simParameters.mapVersion());
        animalComboBox.setValue(simParameters.animalsVersion());
        newPlantsSpinner.getValueFactory().setValue(simParameters.numberOfNewPlants());
        initialPlantsSpinner.getValueFactory().setValue(simParameters.startNumberOfPlants());
        initialAnimalSpinner.getValueFactory().setValue(simParameters.startNumberOfAnimals());
        initialAnimalEnergySpinner.getValueFactory().setValue(simParameters.startEnergy());
        plantEnergySpinner.getValueFactory().setValue(simParameters.plantEnergySupply());
        energyNeededToBreed.getValueFactory().setValue(simParameters.energyNeededForBreeding());
        energyForBreedingSpinner.getValueFactory().setValue(simParameters.energyLostForBreeding());
        minMutationsSpinner.getValueFactory().setValue(simParameters.minMutations());
        maxMutationsSpinner.getValueFactory().setValue(simParameters.maxMutations());
        genomeLengthSpinner.getValueFactory().setValue(simParameters.genomeLength());
    }

    private Simulation createNewSimulation() throws IncorrectParametersException {
        SimulationParameters simParameters = getSimParameters();
        simParameters.checkParameters();
        return new Simulation(simParameters);
    }


    public void onSaveConfigClicked(){
        String filename = configNameTextField.getText();
        configHandler.saveConfigToFile(getSimParameters(), filename);
        if(!loadConfigComboBox.getItems().contains(filename)) {
            loadConfigComboBox.getItems().add(filename);
        }
    }

    public void onLoadConfigClicked(){
        String filename = loadConfigComboBox.getValue();
        if (filename != null){
            Optional<SimulationParameters> optSimParameters = configHandler.loadConfigFromFile(filename);
            optSimParameters.ifPresent(this::setSimParameters);
        }
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
        String projectPath = System.getProperty("user.dir");
        String dirPath = Paths.get(projectPath, "src", "main", "resources", "simConfigurations").toString();
        File directory = new File(dirPath);
        if(directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                String filename = file.getName();
                loadConfigComboBox.getItems().add(filename.substring(0, filename.length() - 4));
            }
        }
    }

    private SimulationPresenter configureSimulationStage() throws Exception{
        Stage simStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        simStage.setOnCloseRequest(e -> {
            presenter.simulationEnd();
            activeFilenames.remove(presenter.getId());
        });

        numberOfSimulations++;
        presenter.setId(numberOfSimulations);
        configureStage(simStage, viewRoot);
        simStage.show();

        return presenter;
    }

    private void showAlert(String message, String title){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

}
