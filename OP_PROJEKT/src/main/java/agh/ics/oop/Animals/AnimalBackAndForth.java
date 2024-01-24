package agh.ics.oop.Animals;

import agh.ics.oop.Vector2d;

public class AnimalBackAndForth extends AbstractAnimal {
    private GenomeDirection genomeDirection;

    public AnimalBackAndForth(Vector2d v, int numberOfGenes, int spawnDay, int energy) {
        super(v, numberOfGenes, spawnDay, energy);
        genomeDirection = GenomeDirection.randomGenomeDirection();
    }

    public AnimalBackAndForth(AbstractAnimal a1, AbstractAnimal a2, int spawnDay, int energyLost) {
        super(a1, a2, spawnDay, energyLost);
        genomeDirection = GenomeDirection.randomGenomeDirection();
    }

    @Override
    public void setNextGene(){
        if(currentGene == genes.size() - 1 && genomeDirection == GenomeDirection.RIGHT){
            genomeDirection = genomeDirection.change();
        } else if (currentGene == 0 && genomeDirection == GenomeDirection.LEFT) {
            genomeDirection = genomeDirection.change();
        }
        else {
            currentGene += genomeDirection.getValue();
        }
    }

    public void setGenomeDirection(GenomeDirection genomeDirection) {
        this.genomeDirection = genomeDirection;
    }
}
