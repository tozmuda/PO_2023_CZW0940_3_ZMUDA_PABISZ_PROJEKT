package agh.ics.oop.Maps;

import agh.ics.oop.Animals.AbstractAnimal;
import agh.ics.oop.Field;
import agh.ics.oop.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LifeGivingCorpsesMap extends AbstractWorldMap {

    private final int numberOfPreferredDays;
    private final Map<Vector2d, Integer> preferredPositions = new HashMap<>();

    public LifeGivingCorpsesMap(int height, int width, int plantEnergySupply, int numberOfPreferredDays) {
        super(height, width, plantEnergySupply);
        this.numberOfPreferredDays = numberOfPreferredDays;
    }

    private void updatePreferredPositions(){
        for(Vector2d v : preferredPositions.keySet()){
            int daysLeft = preferredPositions.get(v);
            if (daysLeft != 0) {
                preferredPositions.put(v, daysLeft - 1);
            }
            else{
                preferredPositions.remove(v);
            }
        }
    }

    @Override
    public void removeDeadAnimals(int days) {
        updatePreferredPositions();
        for(AbstractAnimal animal : allAnimalsList){
            if (animal.getEnergy() == 0){
                preferredPositions.put(animal.getPosition(), numberOfPreferredDays);
            }
        }
        super.removeDeadAnimals(days);
    }

    @Override
    public List<Vector2d> getPreferredPositions() {
        return preferredPositions.keySet()
                .stream()
                .filter(v -> fields.get(v).getPlant() == null)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    protected List<Vector2d> getOtherPositions() {
        return fields.keySet()
                .stream()
                .filter(v -> fields.get(v).getPlant() == null
                        && preferredPositions.get(v) == null)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
