package agh.ics.oop.Maps;

import agh.ics.oop.*;
import agh.ics.oop.Animals.AbstractAnimal;
import agh.ics.oop.Observers.MapChangeListener;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap {
    // pola od 0 do height/width włącznie
    protected final int height;
    protected final int width;
    protected final int plantEnergySupply; // nie wiem czy to w tym miejscu ale najwyżej się zmieni

    protected final Map<Vector2d, agh.ics.oop.Field> fields = new HashMap<>();
    protected final List<AbstractAnimal> allAnimalsList = new ArrayList<>();
    protected final List<AbstractAnimal> allDeadAnimalsList = new ArrayList<>();

    private final List<MapChangeListener> observers = new ArrayList<>();


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

    public void addObserver(MapChangeListener observer) {
        this.observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        this.observers.remove(observer);
    }

    public void mapChanged() {
        int maxEnergy = getMaxEnergy();
        for (MapChangeListener observer : this.observers) {
            observer.mapChanged(this, maxEnergy);
        }
    }

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
    public void removeDeadAnimals(int days){
        List<AbstractAnimal> toRemove= new LinkedList<>();
        for(AbstractAnimal animal : allAnimalsList){
            if (animal.getEnergy() == 0){
                toRemove.add(animal);
            }
            else{
                animal.incrementDaysAlive();
            }
        }

        for(AbstractAnimal animal : toRemove){
            allAnimalsList.remove(animal);
            fields.get(animal.getPosition()).removeAnimal(animal);
            animal.setDayOfDeath(days - 1);
            allDeadAnimalsList.add(animal);
        }

    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        if(!fields.get(position).getAnimals().isEmpty()) return Optional.of(fields.get(position).getAnimalsOrder().get(0));
        return Optional.ofNullable(fields.get(position).getPlant());
    }

    protected abstract List<Vector2d> getOtherPositions();
    protected abstract List<Vector2d> getPreferredPositions();

    public static float round1(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }

    @Override
    public int getAnimalCount(){
        return this.allAnimalsList.size();
    }

    @Override
    public int getPlantCount(){
        int cnt = 0;
        for(Map.Entry<Vector2d, Field> entry : this.fields.entrySet()){
            if(entry.getValue().getPlant() != null) cnt+=1;
        }
        return cnt;
    }

    @Override
    public int getFreeFields(){
        int cnt = 0;
        for(Map.Entry<Vector2d, Field> entry : this.fields.entrySet()){
            if(entry.getValue().getAnimals().isEmpty() && entry.getValue().getPlant() == null) cnt+=1;
        }
        return cnt;
        // po co tu była ta jedynka?
    }

    @Override
    public float getAverageEnergy(){
        int sum = 0;
        int cnt = 0;
        for (AbstractAnimal animal : this.allAnimalsList) {
            sum += animal.getEnergy();
            cnt += 1;
        }
        if(cnt == 0) return 0;
        return round1((float) sum / cnt, 2);
    }

    @Override
    public float getAverageDaysLived(){
        int sum = 0;
        int cnt = 0;
        for (AbstractAnimal animal : this.allDeadAnimalsList) {
            sum += animal.getDaysAlive();
            cnt += 1;
        }
        if(cnt == 0) return 0;
        return round1((float) sum / cnt, 2);
    }

    @Override
    public float getAverageChildCount(){
        int sum = 0;
        int cnt = 0;
        for (AbstractAnimal animal : this.allAnimalsList) {
            sum += animal.getNumberOfChildren();
            cnt += 1;
        }
        if(cnt == 0) return 0;
        return round1((float) sum / cnt, 2);
    }

    @Override
    public List<Integer> getMostPopularGenotype(){
        Map<List<Integer>, Integer> frequencyMap = new HashMap<>();

        // Iterate through the list of ArrayLists and count the frequency
        for (AbstractAnimal animal : this.allAnimalsList) {
            frequencyMap.put(animal.getGenes(), frequencyMap.getOrDefault(animal.getGenes(), 0) + 1);
        }

        // Find the ArrayList with the maximum frequency
        List<Integer> mostPopularGenotype = null;
        int maxFrequency = 0;

        for (Map.Entry<List<Integer>, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostPopularGenotype = entry.getKey();
            }
        }
        return mostPopularGenotype;
    }


    @Override
    public int getMaxEnergy() {
        int maxi = 0;
        for(AbstractAnimal animal : allAnimalsList){
            if (maxi < animal.getEnergy()) maxi = animal.getEnergy();
        }
        return maxi;
    }


    @Override
    public Set<Vector2d> getAllPopularGenome() {
        List<Integer> mostPopularGenome = getMostPopularGenotype();
        return allAnimalsList.stream()
                .filter(animal -> animal.getGenes().equals(mostPopularGenome) )
                .map(AbstractAnimal::getPosition)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<AbstractAnimal> getStrongestAnimal(Vector2d position) {
        if(!fields.get(position).getAnimals().isEmpty()) return Optional.of(fields.get(position).getAnimalsOrder().get(0));
        return Optional.empty();
    }
}
