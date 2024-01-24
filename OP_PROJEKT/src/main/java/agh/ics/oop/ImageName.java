package agh.ics.oop;

public enum ImageName {
    ANIMAL1,
    ANIMAL2,
    ANIMAL3,
    ANIMAL4,
    FIELD,
    PLANT,
    FOLLOWED_ANIMAL,
    PLANT_PREFERRED_POSITION,
    POPULAR_GENOME;

    @Override
    public String toString() {
        String filename = switch (this){
            case ANIMAL1 -> "a1";
            case ANIMAL2 -> "a2";
            case ANIMAL3 -> "a3";
            case ANIMAL4 -> "a4";
            case FIELD -> "field";
            case PLANT -> "plant";
            case FOLLOWED_ANIMAL -> "followedAnimal";
            case PLANT_PREFERRED_POSITION -> "plantPref";
            case POPULAR_GENOME -> "popularGenome";
        };
        return "images/" + filename + ".png";
    }

    public int toNumber(){
        return switch (this){
            case ANIMAL1 -> 2;
            case ANIMAL2 -> 3;
            case ANIMAL3 -> 4;
            case ANIMAL4 -> 5;
            case FIELD -> 1;
            case PLANT -> 0;
            case FOLLOWED_ANIMAL -> 8;
            case PLANT_PREFERRED_POSITION -> 6;
            case POPULAR_GENOME -> 7;
        };
    }
}
