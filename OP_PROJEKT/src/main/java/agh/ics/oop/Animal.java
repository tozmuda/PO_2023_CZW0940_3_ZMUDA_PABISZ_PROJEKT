package agh.ics.oop;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Maps.AbstractWorldMap;
import agh.ics.oop.WorldElement;

import java.util.List;

public interface Animal extends WorldElement {
    void addChild();
    int getSpawnDay();
    int getNumberOfChildren();
    MapDirection getDirection();
    void move (AbstractWorldMap map);
    void turn(int cnt);
    void addEnergy(int x);
    void subtractEnergy(int x);
    int getEnergy();
    List<Integer> getGenes();
}
