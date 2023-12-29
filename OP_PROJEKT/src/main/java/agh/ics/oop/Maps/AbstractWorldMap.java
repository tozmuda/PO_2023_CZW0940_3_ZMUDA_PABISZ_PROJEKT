package agh.ics.oop.Maps;

import agh.ics.oop.*;
import agh.ics.oop.Animals.AbstractAnimal;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    // pola od 0 do height/width włącznie
    protected final int height;
    protected final int width;
    protected final int plantEnergySupply; // nie wiem czy to w tym miejscu ale najwyżej się zmieni

    protected final Map<Vector2d, Field> fields = new HashMap<>();
    protected final List<AbstractAnimal> allAnimalsList = new ArrayList<>();

    public AbstractWorldMap(int height, int width, int plantEnergySupply) {
        this.height = height - 1;
        this.width = width - 1;
        this.plantEnergySupply = plantEnergySupply;

        for(int x = 0; x <= this.width; x++){
            for(int y = 0; y <= this.height; y++){
                Vector2d v = new Vector2d(x, y);
                fields.put(v, new Field(v));
            }
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    // zakładam, że zwierzaki mogą się generować na tych samych polach
    @Override
    public void place(AbstractAnimal animal){
        fields.get(animal.getPosition()).addAnimal(animal);
        allAnimalsList.add(animal);
    }

    @Override
    public void move(AbstractAnimal animal){
        fields.get(animal.getPosition()).removeAnimal(animal);
        animal.move(this);
        fields.get(animal.getPosition()).addAnimal(animal);
    }

    @Override
    public List<AbstractAnimal> getAllAnimals(){
        return allAnimalsList;
    }

    @Override
    public List<Field> getFields(){
        return new LinkedList<>(fields.values());
    }

    @Override
    public void generateNewPlants(int numberOfPlants) {
        RandomPlantPositionGenerator plantPositionGenerator =
                new RandomPlantPositionGenerator(getPreferredPositions(), getOtherPositions(), numberOfPlants);

        for(Vector2d plantPosition: plantPositionGenerator){
            fields.get(plantPosition).addPlant(new Plant(plantPosition, plantEnergySupply));
        }
    }

    @Override
    public void removeDeadAnimals(){
        List<AbstractAnimal> toRemove= new LinkedList<>();
        for(AbstractAnimal animal : allAnimalsList){
            if (animal.getEnergy() == 0){
                toRemove.add(animal);
            }
        }

        for(AbstractAnimal animal : toRemove){
            allAnimalsList.remove(animal);
            fields.get(animal.getPosition()).removeAnimal(animal);
        }

    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if(!fields.get(position).getAnimals().isEmpty()) return fields.get(position).getAnimals().get(0);
        return fields.get(position).getPlant();
    }

    protected abstract List<Vector2d> getPreferredPositions();
    protected abstract List<Vector2d> getOtherPositions();


}
