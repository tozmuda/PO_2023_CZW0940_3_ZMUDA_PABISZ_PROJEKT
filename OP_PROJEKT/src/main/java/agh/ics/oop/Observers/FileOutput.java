package agh.ics.oop.Observers;

import agh.ics.oop.MapChangeListener;
import agh.ics.oop.WorldMap;

import java.io.*;

public class FileOutput implements MapChangeListener {
    private int cnt = 0;
    @Override
    public void mapChanged(WorldMap worldMap) {
        try {
            // Opening File
            File file = new File("output.txt");
            FileWriter fr = null;
            if(cnt==0) fr = new FileWriter(file, false);
            else fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.flush();

            // Writing all statistics
            if(cnt==0){ br.write("day;animal_count;plant_count;free_space_count;most_popular_genotype;average_energy;average_days_lived;average_child_count\n");}
            br.write(Integer.toString(cnt));
            br.write(";");
            br.write(Integer.toString(worldMap.getAnimalCount()));
            br.write(";");
            br.write(Integer.toString(worldMap.getPlantCount()));
            br.write(";");
            br.write(worldMap.getMostPopularGenotype().toString());
            br.write(";");
            br.write(Float.toString(worldMap.getAverageEnergy()));
            br.write(";");
            br.write(Float.toString(worldMap.getAverageDaysLived()));
            br.write(";");
            br.write(Float.toString(worldMap.getAverageChildCount()));
            br.write("\n");

            // Closing File
            br.close();
            fr.close();
        } catch (IOException ex) {
            // Report
        }
        cnt++;
    }
}
