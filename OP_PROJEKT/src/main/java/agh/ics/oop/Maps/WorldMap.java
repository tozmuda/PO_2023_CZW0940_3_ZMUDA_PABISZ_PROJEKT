package agh.ics.oop.Maps;

import agh.ics.oop.Animals.AbstractAnimal;
import agh.ics.oop.Field;
import agh.ics.oop.Observers.MapChangeListener;
import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldElement;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    Set<Vector2d> getAllPreferredPositions();
    Set<Vector2d> getAllPopularGenome();
    int getMaxEnergy();

    Optional<AbstractAnimal> getStrongestAnimal(Vector2d position);

}
