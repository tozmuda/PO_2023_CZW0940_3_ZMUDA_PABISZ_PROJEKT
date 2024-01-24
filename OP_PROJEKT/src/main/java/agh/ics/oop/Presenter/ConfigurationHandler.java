package agh.ics.oop.Presenter;

import agh.ics.oop.Animals.AnimalVersion;
import agh.ics.oop.Maps.MapVersion;
import agh.ics.oop.SimulationParameters;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;


public class ConfigurationHandler {
    public void saveConfigToFile(SimulationParameters simParameters, String filename){
        String projectPath = System.getProperty("user.dir");
        String filePath = Paths.get(projectPath, "src", "main", "resources", "simConfigurations", filename + ".txt").toString();

        try(FileWriter writer = new FileWriter(filePath)){
            writer.write(simParameters.mapHeight() + ";");
            writer.write(simParameters.mapWidth() + ";");
            writer.write(simParameters.mapVersion() + ";");
            writer.write(simParameters.animalsVersion() + ";");
            writer.write(simParameters.numberOfNewPlants() + ";");
            writer.write(simParameters.startNumberOfPlants() + ";");
            writer.write(simParameters.startNumberOfAnimals() + ";");
            writer.write(simParameters.startEnergy() + ";");
            writer.write(simParameters.plantEnergySupply() + ";");
            writer.write(simParameters.energyNeededForBreeding() + ";");
            writer.write(simParameters.energyLostForBreeding() + ";");
            writer.write(simParameters.minMutations() + ";");
            writer.write(simParameters.maxMutations() + ";");
            writer.write(simParameters.genomeLength()+ "");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Optional<SimulationParameters> loadConfigFromFile(String filename){
        SimulationParameters simParameters = null;

        String projectPath = System.getProperty("user.dir");
        String filePath = Paths.get(projectPath, "src", "main", "resources", "simConfigurations", filename + ".txt").toString();

        try(Scanner scanner = new Scanner(Path.of(filePath))){
            String line = scanner.nextLine();
            String[] stringParameters = line.split(";");

            int mapHeight = Integer.parseInt(stringParameters[0]);
            int mapWidth = Integer.parseInt(stringParameters[1]);
            MapVersion mapVersion = MapVersion.valueOf(stringParameters[2]);
            AnimalVersion animalVersion = AnimalVersion.valueOf(stringParameters[3]);
            int newPlantsEveryDay = Integer.parseInt(stringParameters[4]);
            int startPlants = Integer.parseInt(stringParameters[5]);
            int startAnimals = Integer.parseInt(stringParameters[6]);
            int startEnergy = Integer.parseInt(stringParameters[7]);
            int plantEnergy = Integer.parseInt(stringParameters[8]);
            int energyToBreed = Integer.parseInt(stringParameters[9]);
            int energyLostForBreeding = Integer.parseInt(stringParameters[10]);
            int minMutations = Integer.parseInt(stringParameters[11]);
            int maxMutations = Integer.parseInt(stringParameters[12]);
            int genomeLength = Integer.parseInt(stringParameters[13]);

            simParameters = new SimulationParameters(mapHeight, mapWidth, mapVersion, animalVersion,
                    newPlantsEveryDay, startPlants, startAnimals, startEnergy, plantEnergy, energyToBreed, energyLostForBreeding,
                    minMutations, maxMutations, genomeLength);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        return Optional.ofNullable(simParameters);
    }
}

