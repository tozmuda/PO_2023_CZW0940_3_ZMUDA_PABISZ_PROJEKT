package agh.ics.oop;

import agh.ics.oop.Animals.AnimalVersion;
import agh.ics.oop.Exceptions.IncorrectParametersException;
import agh.ics.oop.Maps.MapVersion;

public record SimulationParameters(int mapHeight, int mapWidth, MapVersion mapVersion, AnimalVersion animalsVersion, int numberOfNewPlants, int startNumberOfPlants,
                                   int startNumberOfAnimals, int startEnergy, int plantEnergySupply, int energyNeededForBreeding, int energyLostForBreeding,
                                   int minMutations, int maxMutations, int genomeLength) {

    public void checkParameters() throws IncorrectParametersException {
        if (energyLostForBreeding > energyNeededForBreeding) throw new IncorrectParametersException("Animal can't lose more energy for breeding than it needs to bread!");
        if (maxMutations > genomeLength) throw new IncorrectParametersException("The maximum number of mutations can't be greater than the animal's genome length!");
        if (maxMutations < minMutations) throw new IncorrectParametersException("The minimum number of mutations can't be greater than the maximum number of mutations!");
        if (startNumberOfPlants > mapHeight * mapWidth) throw new IncorrectParametersException("The initial number of plants can't be greater than the number of map fields!");
        if (numberOfNewPlants > mapHeight * mapWidth) throw new IncorrectParametersException("The number of plants spawned every day can't be greater than the number of map fields!");
    }
}
