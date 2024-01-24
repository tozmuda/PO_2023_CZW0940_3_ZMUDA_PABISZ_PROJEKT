package agh.ics.oop.Presenter;

import agh.ics.oop.Animals.Animal;
import agh.ics.oop.ImageName;
import agh.ics.oop.Vector2d;
import agh.ics.oop.WorldElement;
import agh.ics.oop.Maps.WorldMap;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.lang.Math.round;

public class MapDisplay {

    private final GridPane mapGrid;
    private final ImageGenerator imageGenerator;
    private final WorldMap map;
    private final int cellSize;

    public MapDisplay(GridPane mapGrid, WorldMap map, SimulationPresenter presenter) {
        this.mapGrid = mapGrid;
        this.map = map;

        this.cellSize = findCellSize();
        this.imageGenerator = new ImageGenerator(cellSize - 1, presenter);
    }

    private int findCellSize(){
        int w = (int) round(mapGrid.getWidth()) - 10;
        int h = (int) round(mapGrid.getHeight());
        int cellSize = h / (map.getHeight() + 1);
        if (cellSize > w / (map.getWidth() + 1)) cellSize = w / (map.getWidth() + 1);
        return cellSize;
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private ImageView getObjectDraw(Vector2d currentPosition,
                                    int maxEnergy) {
        Optional<WorldElement> opt = map.objectAt(currentPosition);
        ImageName imageName = ImageName.FIELD;
        if (opt.isPresent()) {
            imageName = opt.get().getImageName(maxEnergy);
        }
        return imageGenerator.getImageView(imageName);
    }

    private void setConstraints(int width, int height){
        for(int c = 0; c <= width; c++){
            mapGrid.getColumnConstraints().add(new ColumnConstraints(cellSize));
        }
        for(int r = 0; r <= height; r++) {
            mapGrid.getRowConstraints().add(new RowConstraints(cellSize));
        }

    }

    private void addObject(ImageView objectView, int c, int r){
        mapGrid.add(objectView, c, r, 1, 1);
        GridPane.setHalignment(objectView, HPos.CENTER);
        GridPane.setValignment(objectView, VPos.CENTER);
    }

    public void drawMap(Animal followedAnimal, int maxEnergy){
        int height = map.getHeight();
        int width = map.getWidth();
        ImageView objectView;

        clearGrid();
        setConstraints(width, height);

        for(int r = 0; r <= height; r++){
            for(int c = 0; c <= width; c++){
                Vector2d v = new Vector2d(c, height - r);
                if(followedAnimal != null && followedAnimal.getDayOfDeath() == -1
                        && Objects.equals(followedAnimal.getPosition(), v)){
                    objectView = imageGenerator.getImageView(ImageName.FOLLOWED_ANIMAL);
                }
                else objectView = getObjectDraw(v, maxEnergy);
                addObject(objectView, c, r);
            }
        }

        imageGenerator.resetCounters();

    }


    public void drawMapWithPrefPositions(Animal followedAnimal, int maxEnergy){
        int height = map.getHeight();
        int width = map.getWidth();
        ImageView objectView;
        Set<Vector2d> prefPositions = map.getAllPreferredPositions();

        clearGrid();
        setConstraints(width, height);

        for(int r = 0; r <= height; r++){
            for(int c = 0; c <= width; c++){
                Vector2d v = new Vector2d(c, height - r);
                if(prefPositions.contains(v)){
                    objectView = imageGenerator.getImageView(ImageName.PLANT_PREFERRED_POSITION);
                } else if (followedAnimal != null && followedAnimal.getDayOfDeath() == -1
                        && Objects.equals(followedAnimal.getPosition(), v)) {
                    objectView = imageGenerator.getImageView(ImageName.FOLLOWED_ANIMAL);
                } else {
                    objectView = getObjectDraw(v, maxEnergy);
                }
                addObject(objectView, c, r);
            }
        }
        imageGenerator.resetCounters();
    }

    public void drawMapWithPopularGenome(Animal followedAnimal, int maxEnergy){
        int height = map.getHeight();
        int width = map.getWidth();
        ImageView objectView;
        Set<Vector2d> popularGenome = map.getAllPopularGenome();

        clearGrid();
        setConstraints(width, height);


        for(int r = 0; r <= height; r++){
            for(int c = 0; c <= width; c++){
                Vector2d v = new Vector2d(c, height - r);
                if(popularGenome.contains(v)){
                    objectView = imageGenerator.getImageView(ImageName.POPULAR_GENOME);
                } else if (followedAnimal != null && followedAnimal.getDayOfDeath() == -1
                        && Objects.equals(followedAnimal.getPosition(), v)) {
                    objectView = imageGenerator.getImageView(ImageName.FOLLOWED_ANIMAL);
                }
                else {
                    objectView = getObjectDraw(v, maxEnergy);
                }
                addObject(objectView, c, r);
            }
        }
        imageGenerator.resetCounters();
    }
}
