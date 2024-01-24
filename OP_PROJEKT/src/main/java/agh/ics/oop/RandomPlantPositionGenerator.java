package agh.ics.oop;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.lang.Math.round;

public class RandomPlantPositionGenerator implements Iterable<Vector2d>{
    private final Iterator<Vector2d> positionsIterator;
    public RandomPlantPositionGenerator(List<Vector2d> preferredPositions, List<Vector2d> otherPosition, int numberOfPlants){
        positionsIterator = new PositionsIterator(preferredPositions, otherPosition, numberOfPlants);
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return positionsIterator;
    }

}

class PositionsIterator implements Iterator<Vector2d>{

    private final List<Vector2d> preferredPositions;
    private final List<Vector2d> otherPositions;

    private int preferredRange;
    private int otherRange;
    private int plantsLeft;
    private final Random rand = new Random();
    public PositionsIterator (List<Vector2d> preferredPositions, List<Vector2d> otherPosition, int numberOfPlants){
        this.preferredPositions = preferredPositions;
        this.otherPositions = otherPosition;
        this.preferredRange = preferredPositions.size();
        this.otherRange = otherPosition.size();
        this.plantsLeft = numberOfPlants;
    }

    private void setNextPosition(List<Vector2d> positions, int range){
        int i = rand.nextInt(range);
        Vector2d tmp = positions.get(range - 1);
        positions.set(range - 1, positions.get(i));
        positions.set(i, tmp);
    }

    @Override
    public Vector2d next() {
        int category = -1;
        if(preferredRange == 0 && otherRange != 0) category = 1;
        else if (preferredRange != 0 && otherRange == 0) category = 0;
        else {
            int x = (int) round(Math.random() * 4);
            if (x < 4) category = 0;
            else category = 1;
        }
        plantsLeft--;
        if (category == 0){
            setNextPosition(preferredPositions, preferredRange);
            preferredRange--;
            return preferredPositions.get(preferredRange);
        }
        else{
            setNextPosition(otherPositions, otherRange);
            otherRange--;
            return otherPositions.get(otherRange);
        }


    }

    @Override
    public boolean hasNext() {
        return plantsLeft != 0 && (preferredRange != 0 || otherRange != 0);
    }
}
