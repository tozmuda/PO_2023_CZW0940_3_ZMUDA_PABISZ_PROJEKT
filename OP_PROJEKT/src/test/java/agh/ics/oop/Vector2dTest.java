package agh.ics.oop;

import org.junit.Test;
import org.junit.Assert;

public class Vector2dTest {


    @Test
    public void equalsTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(1, 3);

        Assert.assertEquals(v1, v2);
        Assert.assertNotEquals(v1, v3);
    }

    @Test
    public void toStringTest(){
        Vector2d v1 = new Vector2d(1, 3);

        Assert.assertEquals("(1, 3)", v1.toString());
    }

    @Test
    public void precedesTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 1);

        Assert.assertTrue(v1.precedes(v2));
        Assert.assertFalse(v1.precedes(v3));
    }

    @Test
    public void followsTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 1);

        Assert.assertTrue(v1.follows(v2));
        Assert.assertFalse(v3.follows(v1));
    }

    @Test
    public void upperRightTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 1);
        Vector2d v4 = new Vector2d(2, 2);

        Assert.assertEquals(v1, v1.upperRight(v2));
        Assert.assertEquals(v4, v1.upperRight(v3));
    }

    @Test
    public void lowerLeftTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 1);
        Vector2d v4 = new Vector2d(1, 1);

        Assert.assertEquals(v1, v1.lowerLeft(v2));
        Assert.assertEquals(v4, v1.lowerLeft(v3));
    }

    @Test
    public void addTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 4);

        Assert.assertEquals(v3, v1.add(v2));
    }

    @Test
    public void substractTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(0, 0);

        Assert.assertEquals(v3, v1.substract(v2));
    }

    @Test
    public void oppositeTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(-1, -2);

        Assert.assertEquals(v2, v1.opposite());
    }
}