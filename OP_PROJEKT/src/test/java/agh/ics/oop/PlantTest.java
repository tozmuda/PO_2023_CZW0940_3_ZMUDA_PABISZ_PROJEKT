package agh.ics.oop;

import org.junit.Assert;
import org.junit.Test;

public class PlantTest {

    @Test
    public void getPositionTest(){
        Plant plant1 = new Plant(new Vector2d(2, 4), 50);
        Plant plant2 = new Plant(new Vector2d(4, 4), 50);

        Assert.assertEquals(new Vector2d(2, 4), plant1.getPosition());
        Assert.assertEquals(new Vector2d(4, 4), plant2.getPosition());
    }

    @Test
    public void getEnergySupplyTest(){
        Plant plant1 = new Plant(new Vector2d(2, 4), 1000);
        Plant plant2 = new Plant(new Vector2d(4, 4), 50);

        Assert.assertEquals(1000, plant1.getEnergySupply());
        Assert.assertEquals(50, plant2.getEnergySupply());
    }
}
