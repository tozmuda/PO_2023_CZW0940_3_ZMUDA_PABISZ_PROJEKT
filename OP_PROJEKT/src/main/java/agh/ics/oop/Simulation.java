package agh.ics.oop;


import agh.ics.oop.Animals.AbstractAnimal;
import agh.ics.oop.Animals.AnimalBackAndForth;
import agh.ics.oop.Animals.AnimalBasic;
import agh.ics.oop.Animals.AnimalVersion;
import agh.ics.oop.Maps.LifeGivingCorpsesMap;
import agh.ics.oop.Maps.MapVersion;
import agh.ics.oop.Maps.RainForestMap;
import agh.ics.oop.Maps.TempMapVisualizer;

import java.util.List;

// 1. usunięcie martwych zwierząt
// 2. przemieszczenie zwierząt
// 3. zjadanie roślin
// 4. rozmnażanie
// 5. nowe rośliny
public class Simulation implements Runnable{
    private final WorldMap map;
    private int days;
    private final SimulationParameters parameters;

    // czy liczba dni przez które pola są preferowane też ma być parametrem?
    public Simulation(int mapHeight, int mapWidth, MapVersion mapVersion, AnimalVersion animalsVersion, int delay, int numberOfNewPlants, int startNumberOfPlants,
                      int startNumberOfAnimals, int startEnergy, int plantEnergySupply, int energyNeededForBreeding, int energyLostForBreeding,
                      int minMutations, int maxMutations, int genomeLength) {
        this.map = switch (mapVersion){
            case RAIN_FOREST -> new RainForestMap(mapHeight, mapWidth, plantEnergySupply);
            case LIFE_GIVING_CORPSES -> new LifeGivingCorpsesMap(mapHeight, mapWidth, plantEnergySupply, 5);
        };
        this.days = 0;      // 0 czy 1?
        this.parameters = new SimulationParameters(animalsVersion, delay, numberOfNewPlants, energyNeededForBreeding,
                energyLostForBreeding, minMutations, maxMutations);

        generateAnimals(startNumberOfAnimals, startEnergy, genomeLength);
        this.map.generateNewPlants(startNumberOfPlants);

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
        TempMapVisualizer vis = new TempMapVisualizer(map);
        System.out.println(vis.draw(new Vector2d(0, 0), new Vector2d(map.getWidth(), map.getHeight())));
        // jakoś będzie trzeba przerwać
        while (true){
            days++;
            System.out.printf("Days: %d%n", days);
            removeDead();
            moveAnimals();
            eatPlants();
            breeding();
            generatePlants();

        }
    }

    private void removeDead(){
        map.removeDeadAnimals();
    }

    private void moveAnimals(){
        List<AbstractAnimal> allAnimals = map.getAllAnimals();

        for(AbstractAnimal animal : allAnimals){
            map.move(animal);
            try {
                Thread.sleep(parameters.delay());
                // TODO będzie trzeba usunąć rysowanie mapy w konsoli, na razie dałam dla testów
                TempMapVisualizer vis = new TempMapVisualizer(map);
                System.out.println(vis.draw(new Vector2d(0, 0), new Vector2d(map.getWidth(), map.getHeight())));
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
                field.removePlant();
            }
        }
    }


    // jeszcze dodać warunek kiedy może się rozmnażać
    private void breeding(){
        for(Field field : map.getFields()){
            List<AbstractAnimal> animalList = field.getAnimalsOrder();

            int numberOfNewAnimals = (int) (animalList.size() / 2);
            for(int i = 0; i < numberOfNewAnimals; i++){
                AbstractAnimal a1 = animalList.get(i * 2);
                AbstractAnimal a2 = animalList.get(i * 2 + 1);
                if (a1.getEnergy() < parameters.energyNeededForBreeding() || a2.getEnergy() < parameters.energyNeededForBreeding()) break;

                switch (parameters.animalsVersion()){
                    case BASIC -> map.place(new AnimalBasic(a1, a2, days, parameters.energyLostForBreeding()));
                    case BACK_AND_FORTH -> map.place(new AnimalBackAndForth(a1, a2, days, parameters.energyLostForBreeding()));
                }
            }
        }
    }

    private void generatePlants(){
        map.generateNewPlants(parameters.numberOfNewPlants());
    }
}
