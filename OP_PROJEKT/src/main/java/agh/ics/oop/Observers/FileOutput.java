package agh.ics.oop.Observers;

import agh.ics.oop.Maps.WorldMap;

import java.io.*;

public class FileOutput implements SimulationChangeListener {
    private int cnt = 0;
    private final String filename;

    public FileOutput(String filename) {
        this.filename = filename + ".csv";
    }

    @Override
    public void dayChanged(WorldMap worldMap, int day) {
        try {
            // Opening File
            File file = new File(filename);
            FileWriter fr = null;
            if(day==0) fr = new FileWriter(file, false);
            else fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.flush();

            // Writing all statistics
            if(cnt==0){ br.write("day;animal_count;plant_count;free_space_count;most_popular_genotype;average_energy;average_days_lived;average_child_count\n");}
            br.write(Integer.toString(day));
            br.write(";");
            br.write(Integer.toString(worldMap.getAnimalCount()));
            br.write(";");
            br.write(Integer.toString(worldMap.getPlantCount()));
            br.write(";");
            br.write(Integer.toString(worldMap.getFreeFields()));
            br.write(";");
            br.write("" + worldMap.getMostPopularGenotype());
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
            System.out.println(ex.getMessage());
        }
        cnt++;
    }
}

