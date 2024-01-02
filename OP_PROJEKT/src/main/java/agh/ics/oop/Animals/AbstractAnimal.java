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
        a1.addChild();
        a2.addChild();
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

    }

    @Override
    public void addChild(){
        this.numberOfChildren++;
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

    public void setGene(int geneNumber, int gene) {
        this.genes.set(geneNumber, gene);
    }
}
