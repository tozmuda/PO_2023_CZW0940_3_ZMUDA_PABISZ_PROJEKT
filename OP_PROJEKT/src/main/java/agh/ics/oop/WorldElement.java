package agh.ics.oop;

import javafx.scene.image.ImageView;

public interface WorldElement {

    Vector2d getPosition();

    String toString();

    String getImageName(int maxEnergy);
}
