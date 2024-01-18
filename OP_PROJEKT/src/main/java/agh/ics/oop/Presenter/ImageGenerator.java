package agh.ics.oop.Presenter;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImageGenerator {

    // grass, field, a1, a2, a3, a4
    private final List<List<ImageView>> imagesList= new ArrayList<>();
    private final List<Integer> countList = new ArrayList<>();
    private int day = -1;
    private final int imageSize;

    public ImageGenerator(int imageSize, int noFields, int noAnimals, int noGrass) {
        this.imageSize = imageSize;

        for(int i = 0; i < 6; i++){
            imagesList.add(new ArrayList<ImageView>());
            countList.add(0);
        }
        for(int i = 0; i < noFields; i++){generateImageView(1, "images/field.png");}
        for(int i = 0; i < noGrass; i++){generateImageView(0, "images/plant.png");}
        for(int i = 0; i < noAnimals; i++){
            generateImageView(2, "images/a1.png");
            generateImageView(3, "images/a2.png");
            generateImageView(4, "images/a3.png");
            generateImageView(5, "images/a4.png");

        }
    }

    private void newDay(){
        for(int i = 0; i < 6; i++) {
            countList.set(i, 0);
        }
    }

    private void generateImageView(int i, String imageName){
        Image image = new Image(imageName);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(imageSize);
        imageView.setFitWidth(imageSize);
        imagesList.get(i).add(imageView);
    }

    public ImageView getImageView(String imageName, int day){
        if(this.day != day){
            this.day = day;
            newDay();
        }
        int i = switch (imageName){
            case "images/plant.png" -> 0;
            case "images/field.png" -> 1;
            case "images/a1.png" -> 2;
            case "images/a2.png" -> 3;
            case "images/a3.png" -> 4;
            default -> 5;
        };
        int currCount = countList.get(i);
        if(currCount >= imagesList.get(i).size()){
            generateImageView(i, imageName);
        }

        countList.set(i, currCount + 1);
        return imagesList.get(i).get(currCount);
    }

}
