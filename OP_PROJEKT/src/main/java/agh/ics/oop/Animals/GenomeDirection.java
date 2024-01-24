package agh.ics.oop.Animals;

import static java.lang.Math.round;

public enum GenomeDirection {
    LEFT, RIGHT;

    public int getValue(){
        return switch (this){
            case LEFT -> -1;
            case RIGHT -> 1;
        };
    }

    public GenomeDirection change(){
        return switch (this){
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    public static GenomeDirection randomGenomeDirection(){
        if ((int) round(Math.random()) == 0){
            return LEFT;
        }
        return RIGHT;
    }
}
