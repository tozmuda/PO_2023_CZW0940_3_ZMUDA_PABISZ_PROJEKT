package agh.ics.oop.Animals;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Maps.AbstractWorldMap;
import agh.ics.oop.WorldElement;

import java.util.ArrayList;
import java.util.List;

public interface Animal extends WorldElement {
    List<Animal> children = new ArrayList<>();
    void addChild(Animal animal);
    int getSpawnDay();
    int getNumberOfChildren();
    MapDirection getDirection();
    void move (AbstractWorldMap map);
    void turn(int cnt);
    void addEnergy(int x);
    void subtractEnergy(int x);
    int getEnergy();
    List<Integer> getGenes();
    void setGene(int geneNumber, int gene);
    void incrementPlantsEaten();
    void incrementDaysAlive();
    int getPlantsEaten();
    int getDaysAlive();
    void setDayOfDeath(int x);
    int getDayOfDeath();
    int getOffspringCount();
    List<Animal> getChildrenList();
    int getCurrentGene();
}
