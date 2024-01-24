package agh.ics.oop.Maps;

import agh.ics.oop.Field;
import agh.ics.oop.Vector2d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.round;

public class RainForestMap extends AbstractWorldMap {

    private final int forestLowerBound;
    private final int forestUpperBound;

    public RainForestMap(int height, int width, int plantEnergySupply) {
        super(height, width, plantEnergySupply);
        forestLowerBound = (height - getForestHeight(height)) / 2;
        forestUpperBound = (height + getForestHeight(height)) / 2 - 1;
    }

    // czasami wychodzi więcej niż 20% ale chciałam żeby było symetrycznie, także mam nadzieję że może tak być
    private int getForestHeight(int mapHeight){
        int forestHeight = (int) round(mapHeight / 5.0);
        if ((mapHeight - forestHeight) % 2 == 1){
            if (forestHeight * 5 >= mapHeight ) forestHeight--;
            else forestHeight++;
        }
        return forestHeight;
    }

    @Override
    public Set<Vector2d> getAllPreferredPositions() {
        return fields.values()
                .stream()
                .filter(field -> field.getPosition().getY() >= forestLowerBound
                        && field.getPosition().getY() <= forestUpperBound)
                .map(Field::getPosition)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    protected List<Vector2d> getPreferredPositions(){
        return fields.values()
                .stream()
                .filter(field -> field.getPlant() == null
                        && field.getPosition().getY() >= forestLowerBound
                        && field.getPosition().getY() <= forestUpperBound)
                .map(Field::getPosition)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    protected List<Vector2d> getOtherPositions(){
        return fields.values()
                .stream()
                .filter(field -> field.getPlant() == null
                        && (field.getPosition().getY() < forestLowerBound
                        || field.getPosition().getY() > forestUpperBound))
                .map(Field::getPosition)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
