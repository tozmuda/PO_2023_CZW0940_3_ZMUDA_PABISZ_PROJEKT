package agh.ics.oop;

import agh.ics.oop.Animals.AbstractAnimal;

import java.util.List;
import java.util.Optional;

public interface WorldMap {
    int getHeight();
    int getWidth();
    void place(AbstractAnimal animal);
    void move(AbstractAnimal animal);
    List<Field> getFields();
    void generateNewPlants(int numberOfPlants);
    void removeDeadAnimals(int days);
    Optional<WorldElement> objectAt(Vector2d position);
    public List<AbstractAnimal> getAllAnimals();

    int getAnimalCount();
    int getPlantCount();
    int getFreeFields();
    float getAverageEnergy();
    float getAverageDaysLived();
    float getAverageChildCount();
    List<Integer> getMostPopularGenotype();
    void addObserver(MapChangeListener observer);
    void removeObserver(MapChangeListener observer);
    void mapChanged();

    int getMaxEnergy();

}
