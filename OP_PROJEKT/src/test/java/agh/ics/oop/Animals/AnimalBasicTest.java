package agh.ics.oop.Animals;

import agh.ics.oop.MapDirection;
import agh.ics.oop.Maps.AbstractWorldMap;
import agh.ics.oop.Maps.RainForestMap;
import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldMap;
import org.junit.Assert;
import org.junit.Test;

public class AnimalBasicTest {

    @Test
    public void breedingConstructorTest(){
        AnimalBasic animal1 = new AnimalBasic(new Vector2d(1,1), 6, 0, 50);
        AnimalBasic animal2 = new AnimalBasic(new Vector2d(1,1), 6, 0, 50);

        AnimalBasic animal3 = new AnimalBasic(animal1, animal2, 2, 20);

        //Genes Test
        Assert.assertEquals(6, animal3.getGenes().size());
        int flag = 1;
        for (int i = 0; i < 3; i++){
            if(! (animal1.getGenes().get(i) == animal3.getGenes().get(i))) flag = 0;
        }
        if(flag==0){
            for (int i = 0; i < 3; i++){
                Assert.assertEquals(animal2.getGenes().get(i), animal3.getGenes().get(i));
                Assert.assertEquals(animal1.getGenes().get(i+3), animal3.getGenes().get(i+3));
            }
        }
        if(flag==1){
            for (int i = 0; i < 3; i++){
                Assert.assertEquals(animal1.getGenes().get(i), animal3.getGenes().get(i));
                Assert.assertEquals(animal2.getGenes().get(i+3), animal3.getGenes().get(i+3));
            }
        }

        //Energy Test
        Assert.assertEquals(30, animal1.getEnergy());
        Assert.assertEquals(30, animal2.getEnergy());
        Assert.assertEquals(40, animal3.getEnergy());

        //Position Test
        Assert.assertEquals(new Vector2d(1, 1), animal1.getPosition());
        Assert.assertEquals(new Vector2d(1, 1), animal2.getPosition());
        Assert.assertEquals(new Vector2d(1, 1), animal3.getPosition());

        //Children status Test
        Assert.assertEquals(1, animal1.getNumberOfChildren());
        Assert.assertEquals(1, animal2.getNumberOfChildren());
        Assert.assertEquals(0, animal3.getNumberOfChildren());

        //Spawn day Test
        Assert.assertEquals(0, animal1.getSpawnDay());
        Assert.assertEquals(0, animal2.getSpawnDay());
        Assert.assertEquals(2, animal3.getSpawnDay());
    }

    @Test
    public void MoveTest(){
        AbstractWorldMap map = new RainForestMap(5, 5, 5);
        AnimalBasic animal1 = new AnimalBasic(new Vector2d(1, 1), 4, 0, 20);
        animal1.setDirection(MapDirection.NORTH);
        animal1.setCurrentGene(0);
        animal1.setGene(0, 0);
        animal1.setGene(1, 2);
        animal1.setGene(2, 1);
        animal1.setGene(3, 4);


        //Normal Moves Test
        Assert.assertEquals(new Vector2d(1, 1), animal1.getPosition());
        Assert.assertEquals(MapDirection.NORTH, animal1.getDirection());

        animal1.move(map);
        Assert.assertEquals(new Vector2d(1, 2), animal1.getPosition());
        Assert.assertEquals(MapDirection.NORTH, animal1.getDirection());

        animal1.move(map);
        Assert.assertEquals(new Vector2d(2, 2), animal1.getPosition());
        Assert.assertEquals(MapDirection.EAST, animal1.getDirection());

        animal1.move(map);
        Assert.assertEquals(new Vector2d(3, 1), animal1.getPosition());
        Assert.assertEquals(MapDirection.SOUTHEAST, animal1.getDirection());

        animal1.move(map);
        Assert.assertEquals(new Vector2d(2, 2), animal1.getPosition());
        Assert.assertEquals(MapDirection.NORTHWEST, animal1.getDirection());

        animal1.move(map);
        Assert.assertEquals(new Vector2d(1, 3), animal1.getPosition());
        Assert.assertEquals(MapDirection.NORTHWEST, animal1.getDirection());

        //X-axis out of bounds Test
        AnimalBasic animal2 = new AnimalBasic(new Vector2d(0, 2), 4, 0, 20);
        animal2.setDirection(MapDirection.NORTH);
        animal2.setGene(0, 6);
        animal2.setGene(1, 4);
        animal2.setCurrentGene(0);

        animal2.move(map);
        Assert.assertEquals(new Vector2d(4, 2), animal2.getPosition());
        Assert.assertEquals(MapDirection.WEST, animal2.getDirection());

        animal2.move(map);
        Assert.assertEquals(new Vector2d(0, 2), animal2.getPosition());
        Assert.assertEquals(MapDirection.EAST, animal2.getDirection());

        //Y-axis out of bounds Test
        AnimalBasic animal3 = new AnimalBasic(new Vector2d(2, 4), 4, 0, 20);
        animal3.setDirection(MapDirection.NORTH);
        animal3.setGene(0, 0);
        animal3.setGene(1, 4);
        animal3.setCurrentGene(0);

        animal3.move(map);
        Assert.assertEquals(new Vector2d(2, 4), animal3.getPosition());
        Assert.assertEquals(MapDirection.SOUTH, animal3.getDirection());
    }
}