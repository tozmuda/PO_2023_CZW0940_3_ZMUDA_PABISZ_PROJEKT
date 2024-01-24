package agh.ics.oop;

import agh.ics.oop.Animals.AbstractAnimal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.round;


public class Field {
    private final Vector2d position;

    // nie wystarczy tak skoro na jednym polu może być max jedna roślinka?
    private Plant plant = null;


    private final List<AbstractAnimal> animals;

    public Field(Vector2d position){
        this.position = position;
        this.animals = new ArrayList<>();
    }


    public Plant getPlant() {
        return plant;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void addPlant(Plant plant){
        this.plant = plant;
    }

    public void removePlant(){
        plant = null;
    }

    public List<AbstractAnimal> getAnimals(){
        return this.animals;
    }

    private int compareAnimals(AbstractAnimal a1, AbstractAnimal a2){
        if(a1.getEnergy() != a2.getEnergy()) return Integer.compare(a1.getEnergy(), a2.getEnergy());
        if(a1.getSpawnDay() != a2.getSpawnDay()) return Integer.compare(-a1.getSpawnDay(), -a2.getSpawnDay());
        if(a1.getNumberOfChildren() != a2.getNumberOfChildren()) return Integer.compare(a1.getNumberOfChildren(), a2.getNumberOfChildren());
        if ((int) round(Math.random()) == 0) return -1;
        return 1;
    }

    public List<AbstractAnimal> getAnimalsOrder(){
        return animals
                .stream()
                .sorted(this::compareAnimals)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void removeAnimal(AbstractAnimal a){
        this.animals.remove(a);
    }

    public void addAnimal(AbstractAnimal a){
        this.animals.add(a);
    }
}
