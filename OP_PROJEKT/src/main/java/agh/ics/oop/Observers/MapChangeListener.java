package agh.ics.oop.Observers;

import agh.ics.oop.Maps.WorldMap;

public interface MapChangeListener {
    void mapChanged(WorldMap worldMap, int maxEnergy);
}
