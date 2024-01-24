package agh.ics.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {


    @Test
    public void equalsTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(1, 3);

        Assertions.assertEquals(v1, v2);
        Assertions.assertNotEquals(v1, v3);
    }

    @Test
    public void toStringTest(){
        Vector2d v1 = new Vector2d(1, 3);

        Assertions.assertEquals("(1, 3)", v1.toString());
    }

    @Test
    public void precedesTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 1);

        Assertions.assertTrue(v1.precedes(v2));
        Assertions.assertFalse(v1.precedes(v3));
    }

    @Test
    public void followsTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 1);

        Assertions.assertTrue(v1.follows(v2));
        Assertions.assertFalse(v3.follows(v1));
    }

    @Test
    public void upperRightTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 1);
        Vector2d v4 = new Vector2d(2, 2);

        Assertions.assertEquals(v1, v1.upperRight(v2));
        Assertions.assertEquals(v4, v1.upperRight(v3));
    }

    @Test
    public void lowerLeftTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 1);
        Vector2d v4 = new Vector2d(1, 1);

        Assertions.assertEquals(v1, v1.lowerLeft(v2));
        Assertions.assertEquals(v4, v1.lowerLeft(v3));
    }

    @Test
    public void addTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(2, 4);

        Assertions.assertEquals(v3, v1.add(v2));
    }

    @Test
    public void substractTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(1, 2);
        Vector2d v3 = new Vector2d(0, 0);

        Assertions.assertEquals(v3, v1.substract(v2));
    }

    @Test
    public void oppositeTest(){
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(-1, -2);

        Assertions.assertEquals(v2, v1.opposite());
    }
}