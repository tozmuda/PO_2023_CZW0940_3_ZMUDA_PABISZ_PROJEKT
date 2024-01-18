package agh.ics.oop;


import agh.ics.oop.Animals.AbstractAnimal;
import agh.ics.oop.Animals.AnimalBackAndForth;
import agh.ics.oop.Animals.AnimalBasic;
import agh.ics.oop.Animals.AnimalVersion;
import agh.ics.oop.Maps.LifeGivingCorpsesMap;
import agh.ics.oop.Maps.MapVersion;
import agh.ics.oop.Maps.RainForestMap;
import agh.ics.oop.Maps.TempMapVisualizer;
import agh.ics.oop.Observers.FileOutput;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

// 1. usunięcie martwych zwierząt
// 2. przemieszczenie zwierząt
// 3. zjadanie roślin
// 4. rozmnażanie
// 5. nowe rośliny
public class Simulation implements Runnable{
    private final WorldMap map;
    private int days;
    private final SimulationParameters parameters;

    // TODO pokombinować z parametrami tu
    private static final int DEFAULT_DELAY = 150;
    private int currDelay = 5;

    private boolean pause = false;
    private boolean stop = false;


    // czy liczba dni przez które pola są preferowane też ma być parametrem?
    public Simulation(int mapHeight, int mapWidth, MapVersion mapVersion, AnimalVersion animalsVersion, int numberOfNewPlants, int startNumberOfPlants,
                      int startNumberOfAnimals, int startEnergy, int plantEnergySupply, int energyNeededForBreeding, int energyLostForBreeding,
                      int minMutations, int maxMutations, int genomeLength) {
        this.map = switch (mapVersion){
            case RAIN_FOREST -> new RainForestMap(mapHeight, mapWidth, plantEnergySupply);
            case LIFE_GIVING_CORPSES -> new LifeGivingCorpsesMap(mapHeight, mapWidth, plantEnergySupply, 5);
        };
//        this.map.addObserver(new FileOutput());
        this.days = 0;
        this.parameters = new SimulationParameters(animalsVersion, numberOfNewPlants, energyNeededForBreeding,
                energyLostForBreeding, minMutations, maxMutations);

        generateAnimals(startNumberOfAnimals, startEnergy, genomeLength);
        this.map.generateNewPlants(startNumberOfPlants);
    }

    public void setCurrDelay(int newDelay){
        currDelay = newDelay;
    }

    public void pauseSimulation(){
        pause = true;
    }

    public void resumeSimulation(){
        pause = false;
    }

    public void stopSimulation(){
        stop = true;
    }

    public int getDays(){ return days; }

    public WorldMap getMap() {
        return map;
    }

    public void addMapObserver(MapChangeListener observer){
        map.addObserver(observer);
    }


    private void generateAnimals(int startNumberOfAnimals, int startEnergy, int genomeLength){
        for(int i = 0; i < startNumberOfAnimals; i++){
            switch (parameters.animalsVersion()){
                case BASIC -> map.place(new AnimalBasic(Vector2d.randomPosition(map.getWidth(), map.getHeight()), genomeLength, this.days, startEnergy));
                case BACK_AND_FORTH -> map.place(new AnimalBackAndForth(Vector2d.randomPosition(map.getWidth(), map.getHeight()), genomeLength, this.days, startEnergy));
            }
        }
    }

    public void run() {
        map.mapChanged();
        while (!stop){
            try {
                    Thread.sleep(DEFAULT_DELAY);
                }
                catch (InterruptedException e){
                    throw new RuntimeException();
                }
            if(!pause) {
                days++;
                System.out.printf("Days: %d%n", days);
                removeDead(this.days);
                moveAnimals();
                eatPlants();
                breeding();
                generatePlants();
            }
        }
    }

    private void removeDead(int days){
        map.removeDeadAnimals(days);
    }

    private void moveAnimals(){
        List<AbstractAnimal> allAnimals = map.getAllAnimals();

        for(AbstractAnimal animal : allAnimals){
            map.move(animal);
            try {
                Thread.sleep(DEFAULT_DELAY / currDelay);
                this.map.mapChanged();
            }
            catch (InterruptedException e){
                throw new RuntimeException();
            }

        }
    }

    private void eatPlants(){
        for(Field field : map.getFields()){
            Plant plant = field.getPlant();
            if (plant != null && !field.getAnimals().isEmpty()){
                AbstractAnimal animal = field.getAnimalsOrder().get(0);
                animal.addEnergy(plant.getEnergySupply());
                animal.incrementPlantsEaten();
                field.removePlant();
            }
        }
    }

    private void breeding(){
        for(Field field : map.getFields()){
            List<AbstractAnimal> animalList = field.getAnimalsOrder();

            int numberOfNewAnimals = (int) (animalList.size() / 2);
            for(int i = 0; i < numberOfNewAnimals; i++){
                AbstractAnimal a1 = animalList.get(i * 2);
                AbstractAnimal a2 = animalList.get(i * 2 + 1);
                if (a1.getEnergy() < parameters.energyNeededForBreeding() || a2.getEnergy() < parameters.energyNeededForBreeding()) break;

                int numberOfMutations = (int) round(Math.random() * (parameters.maxMutations() - parameters.minMutations()) + parameters.minMutations());
                ArrayList<Integer> mutatingGenes = new ArrayList<>();
                while(numberOfMutations > 0){
                    int randomGene = (int) round(Math.random() * (a1.getGenes().size() - 1));
                    if(!mutatingGenes.contains(randomGene)) {
                        mutatingGenes.add(randomGene);
                        numberOfMutations -= 1;
                    }
                }

                AbstractAnimal a3 = null;
                switch (parameters.animalsVersion()){
                    case BASIC -> a3 = new AnimalBasic(a1, a2, days, parameters.energyLostForBreeding());
                    case BACK_AND_FORTH -> a3 = new AnimalBackAndForth(a1, a2, days, parameters.energyLostForBreeding());
                }
                map.place(a3);
                for(int j=0; j<mutatingGenes.size(); j++){
                    int rand = (int) round(Math.random() * 7);
                    a3.setGene(mutatingGenes.get(j), rand);
                }
            }
        }
    }

    private void generatePlants(){
        map.generateNewPlants(parameters.numberOfNewPlants());
    }
}