package agh.ics.oop;

import agh.ics.oop.Animals.AnimalVersion;
import agh.ics.oop.Maps.MapVersion;
import agh.ics.oop.Maps.RainForestMap;

public class World {
    public static void main(String[] args) {

        Simulation sim = new Simulation(10, 10, MapVersion.RAIN_FOREST, AnimalVersion.BASIC, 500, 2, 18,
                10, 10, 5, 6, 3, 0, 2, 3);

        sim.run();
    }
}
