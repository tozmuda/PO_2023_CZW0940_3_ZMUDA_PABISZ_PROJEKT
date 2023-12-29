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
    void removeDeadAnimals();
    WorldElement objectAt(Vector2d position);
    public List<AbstractAnimal> getAllAnimals();

}
