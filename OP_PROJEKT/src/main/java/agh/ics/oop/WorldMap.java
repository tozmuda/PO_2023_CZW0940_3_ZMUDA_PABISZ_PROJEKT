package agh.ics.oop;

import agh.ics.oop.Animals.AbstractAnimal;

import java.util.List;

public interface WorldMap {
    int getHeight();
    int getWidth();
    void place(AbstractAnimal animal);
    void move(AbstractAnimal animal);
    List<Field> getFields();
    void generateNewPlants(int numberOfPlants);
    void removeDeadAnimals(int days);
    WorldElement objectAt(Vector2d position);
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

}
