package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

public class Animal implements WorldElement{
    private MapDirection direction;
    private Vector2d position;
    private List<Integer> genes = new ArrayList<>();

    private int energy;

    //normal constructor
    public Animal(Vector2d v, int nuberOfGenes) {
        this.direction = MapDirection.NORTH;
        this.position = v;
        for (int i = 0; i < nuberOfGenes; i++) {
            int x = (int) round((Math.random() * 7));
            genes.add(x);
        }
    }

    //constructor for breeding
    public Animal(Animal a1, Animal a2){
        this.position = a1.getPosition();
        this.direction = MapDirection.NORTH;

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
        else{
            for(int i=0; i<secondNumberOfGenes; i++) {
                this.genes.add(a2.getGenes().get(i));
            }
            for(int i=secondNumberOfGenes; i<a1.getGenes().size(); i++) {
                this.genes.add(a1.getGenes().get(i));
            }
        }
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public MapDirection getDirection(){
        return this.direction;
    }

    public String toString(){
        return "*";
    }

    public void move(){
        this.position = this.position.add(this.direction.toUnitVector());
    }

    public void turn(int cnt){
        this.direction = this.direction.turn(cnt);
    }

    public void addEnergy(int x){
        this.energy += x;
    }

    public void substractEnergy(int x){
        this.energy -= x;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Integer> getGenes() {
        return genes;
    }
}
