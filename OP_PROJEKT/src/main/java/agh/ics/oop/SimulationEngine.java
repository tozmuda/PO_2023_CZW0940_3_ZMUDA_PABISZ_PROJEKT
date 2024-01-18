package agh.ics.oop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);


    public void runSim(Simulation simulation){
        executorService.submit(simulation);
    }

    public void awaitSimulationEnd(){
        try{
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
