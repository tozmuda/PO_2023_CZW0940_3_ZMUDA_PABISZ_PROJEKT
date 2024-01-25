package agh.ics.oop;

import java.util.Objects;

import static java.lang.Math.round;

public class Vector2d {
    private final int x;

    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String toString(){
        return "(" + Integer.toString(this.x) + ", " + Integer.toString(this.y) + ")";
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.getX() && this.y <= other.getY();
    }

    public boolean follows(Vector2d other){
        return this.x >= other.getX() && this.y >= other.getY();
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.getX(), this.y + other.getY());
    }

    public Vector2d substract(Vector2d other){
        return new Vector2d(this.x - other.getX(), this.y - other.getY());
    }

    public Vector2d upperRight(Vector2d other){
        int maxx, maxy;
        maxx = Math.max(this.x, other.getX());
        maxy = Math.max(this.y, other.getY());
        return new Vector2d(maxx, maxy);
    }

    public Vector2d lowerLeft(Vector2d other){
        int minx, miny;
        if(this.x < other.getX()) minx = this.x;
        else minx = other.getX();
        if(this.y < other.getY()) miny = this.y;
        else miny = other.getY();
        return new Vector2d(minx, miny);
    }

    public Vector2d opposite(){
        return new Vector2d((-1) * this.x, (-1) * this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    public static Vector2d randomPosition(int maxWidth, int maxHeight){
        return new Vector2d((int) round(Math.random() * (maxWidth)), (int) round(Math.random() * (maxHeight)));

    }
}
