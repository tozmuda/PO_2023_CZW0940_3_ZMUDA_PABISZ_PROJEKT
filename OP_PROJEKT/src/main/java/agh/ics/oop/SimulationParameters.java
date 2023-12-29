package agh.ics.oop;

import agh.ics.oop.Animals.AnimalVersion;

public record SimulationParameters(AnimalVersion animalsVersion, int delay, int numberOfNewPlants, int energyNeededForBreeding,
                                   int energyLostForBreeding, int minMutations, int maxMutations) {
}
