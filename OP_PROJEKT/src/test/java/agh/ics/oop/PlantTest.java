package agh.ics.oop;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlantTest {

    @Test
    public void getPositionTest(){
        Plant plant1 = new Plant(new Vector2d(2, 4), 50);
        Plant plant2 = new Plant(new Vector2d(4, 4), 50);

        Assertions.assertEquals(new Vector2d(2, 4), plant1.getPosition());
        Assertions.assertEquals(new Vector2d(4, 4), plant2.getPosition());
    }

    @Test
    public void getEnergySupplyTest(){
        Plant plant1 = new Plant(new Vector2d(2, 4), 1000);
        Plant plant2 = new Plant(new Vector2d(4, 4), 50);

        Assertions.assertEquals(1000, plant1.getEnergySupply());
        Assertions.assertEquals(50, plant2.getEnergySupply());
    }
}
