package agh.ics.oop.Presenter;

import agh.ics.oop.ImageName;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class ImageGenerator {

    // grass, field, a1, a2, a3, a4, prefFields
    private final List<List<ImageView>> imagesList= new ArrayList<>();
    private final List<Integer> countList = new ArrayList<>();
    private final int imageSize;
    private final SimulationPresenter simulationPresenter;

    public ImageGenerator(int imageSize, SimulationPresenter simulationPresenter) {
        this.imageSize = imageSize;
        this.simulationPresenter = simulationPresenter;

        for(int i = 0; i < 9; i++){
            imagesList.add(new ArrayList<ImageView>());
            countList.add(0);
        }
    }

    public void resetCounters(){
        for(int i = 0; i < 9; i++) {
            countList.set(i, 0);
        }
    }

    private void generateImageView(ImageName imageName){
        Image image = new Image(imageName.toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(imageSize);
        imageView.setFitWidth(imageSize);

        imageView.setOnMouseClicked((MouseEvent event) -> {
            simulationPresenter.followAnimal(imageView);
        });

        imagesList.get(imageName.toNumber()).add(imageView);
    }

    public ImageView getImageView(ImageName imageName){
        int i = imageName.toNumber();
        int currCount = countList.get(i);
        if(currCount >= imagesList.get(i).size()){
            generateImageView(imageName);
        }

        countList.set(i, currCount + 1);
        return imagesList.get(i).get(currCount);
    }

}
