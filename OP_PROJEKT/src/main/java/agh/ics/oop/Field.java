package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Field {
    private Vector2d position;

    private List<WorldElement> elements;

    public Field(Vector2d position){
        this.position = position;
        this.elements = new ArrayList<>();
    }

    public List<WorldElement> getAnimals(){
        return this.elements
                .stream()
                .filter(e -> e instanceof Animal)
                .collect(Collectors.toList());
    }

    public List<WorldElement> getPlants(){
        return this.elements
                .stream()
                .filter(e -> e instanceof Plant)
                .collect(Collectors.toList());
    }

    public void removeElement(WorldElement e){
        this.elements.remove(e);
    }

    public void addElement(WorldElement e){
        this.elements.add(e);
    }
}
