package agh.ics.oop.Observers;

import agh.ics.oop.Maps.WorldMap;

public interface SimulationChangeListener {

    void dayChanged(WorldMap worldMap, int day);
}
