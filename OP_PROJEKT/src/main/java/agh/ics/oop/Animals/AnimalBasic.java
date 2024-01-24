package agh.ics.oop.Animals;
import agh.ics.oop.Vector2d;

public class AnimalBasic extends AbstractAnimal {

    public AnimalBasic(Vector2d v, int numberOfGenes, int spawnDay, int energy) {
        super(v, numberOfGenes, spawnDay, energy);
    }

    public AnimalBasic(AbstractAnimal a1, AbstractAnimal a2, int spawnDay, int energyLost) {
        super(a1, a2, spawnDay, energyLost);
    }

    @Override
    protected void setNextGene() {
        currentGene = (currentGene + 1) % genes.size();
    }
}
