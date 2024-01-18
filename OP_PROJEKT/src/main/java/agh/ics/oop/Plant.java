package agh.ics.oop;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plant implements WorldElement{
    private final Vector2d position;
    private final int energySupply;

    public Plant(Vector2d position, int energySupply){
        this.position = position;
        this.energySupply = energySupply;
    }


    public Vector2d getPosition(){
        return this.position;
    }

    public int getEnergySupply(){
        return this.energySupply;
    }

    @Override
    public String toString() {
        return "#";
    }

    @Override
    public String getImageName(int maxEnergy) {
        return "images/plant.png";
    }
}
