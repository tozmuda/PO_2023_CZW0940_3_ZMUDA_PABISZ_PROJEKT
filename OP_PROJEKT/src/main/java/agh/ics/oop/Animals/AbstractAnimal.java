package agh.ics.oop.Animals;

import agh.ics.oop.Animal;
import agh.ics.oop.MapDirection;
import agh.ics.oop.Maps.AbstractWorldMap;
import agh.ics.oop.Vector2d;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

public abstract class AbstractAnimal implements Animal {
    private MapDirection direction;
    private Vector2d position;
    protected List<Integer> genes = new ArrayList<>();
    protected int currentGene;
    private int energy;
    private final int spawnDay;
    private int numberOfChildren = 0;
    private int plantsEaten = 0;
    private int daysAlive = 0;
    private int dayOfDeath = 0;
    private List<Animal> children = new ArrayList<>();

    // orientacja i aktualny gen są losowe
    private AbstractAnimal(int numberOfGenes, int spawnDay, int energy){
        this.direction = MapDirection.randomDirection();
        this.currentGene = (int) round(Math.random() * (numberOfGenes - 1));
        this.spawnDay = spawnDay;
        this.energy = energy;
    }


    //normal constructor
    public AbstractAnimal(Vector2d v, int numberOfGenes, int spawnDay, int energy) {
        this(numberOfGenes, spawnDay, energy);
        this.position = v;
        for (int i = 0; i < numberOfGenes; i++) {
            int x = (int) round((Math.random() * 7));
            genes.add(x);
        }

    }


    //constructor for breeding
    public AbstractAnimal(AbstractAnimal a1, AbstractAnimal a2, int spawnDay, int energyLost){
        this(a1.getGenes().size(), spawnDay, 2 * energyLost);
        this.position = a1.getPosition();
        a1.subtractEnergy(energyLost);
        a2.subtractEnergy(energyLost);

        int order = (int) round((Math.random() * 1));
        int firstNumberOfGenes =
                round(a1.getGenes().size() * ((float) a1.getEnergy() / (a1.getEnergy() + a2.getEnergy())));
        int secondNumberOfGenes =
                a1.getGenes().size() - firstNumberOfGenes;

        if(order == 0){
            for(int i=0; i<firstNumberOfGenes; i++) {
                this.genes.add(a1.getGenes().get(i));
            }
            for(int i=firstNumberOfGenes; i<a1.getGenes().size(); i++) {
                this.genes.add(a2.getGenes().get(i));
            }
        }
        else {
            for (int i = 0; i < secondNumberOfGenes; i++) {
                this.genes.add(a2.getGenes().get(i));
            }
            for (int i = secondNumberOfGenes; i < a1.getGenes().size(); i++) {
                this.genes.add(a1.getGenes().get(i));
            }
        }
        a1.addChild(this);
        a2.addChild(this);
    }

    @Override
    public void addChild(Animal child){
        this.numberOfChildren++;
        this.children.add(child);
    }

    @Override
    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public int getSpawnDay() {
        return spawnDay;
    }

    @Override
    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    @Override
    public MapDirection getDirection(){
        return this.direction;
    }

    @Override
    public String toString(){
        return "*";
    }


    // co jak wejdzie w górną/dolną granicę mapy jakby na ukos? Jak się wtedy odbija?
    @Override
    public void move(AbstractWorldMap map){
        turn(genes.get(currentGene));
        subtractEnergy(1);
        setNextGene();
        this.position = this.position.add(this.direction.toUnitVector());
        if (position.getY() < 0){
            turn(4);
            position = position.add(new Vector2d(0, 1));
        } else if (position.getY() > map.getHeight()) {
            turn(4);
            position = position.substract(new Vector2d(0, 1));
        }

        if (position.getX() < 0) {
            position = new Vector2d(map.getWidth(), position.getY());
        } else if (position.getX() > map.getWidth()) {
            position = new Vector2d(0, position.getY());
        }
    }

    abstract protected void setNextGene();

    @Override
    public void turn(int cnt){
        this.direction = this.direction.turn(cnt);
    }

    @Override
    public void addEnergy(int x){
        this.energy += x;
    }

    @Override
    public void subtractEnergy(int x){
        this.energy -= x;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public List<Integer> getGenes() {
        return genes;
    }

    @Override
    public void setGene(int geneNumber, int gene) {
        this.genes.set(geneNumber, gene);
    }

    @Override
    public int getPlantsEaten() {
        return plantsEaten;
    }

    @Override
    public void incrementPlantsEaten(){
        this.plantsEaten += 1;
    }

    @Override
    public int getDaysAlive() {
        return this.daysAlive;
    }

    @Override
    public void incrementDaysAlive(){
        this.daysAlive += 1;
    }

    @Override
    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    @Override
    public int getDayOfDeath() {
        return this.dayOfDeath;
    }

    @Override
    public int getOffspringCount(){
        int cnt = -1;
        List<Animal> t = new ArrayList<>();
        t.add(this);
        while(!t.isEmpty()){
            Animal animal = t.get(0);
            t.remove(0);
            cnt+=1;
            t.addAll(animal.children);
        }
        return cnt;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public void setCurrentGene(int currentGene) {
        this.currentGene = currentGene;
    }
}
