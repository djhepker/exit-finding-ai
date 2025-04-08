package hepker;

import hepker.ai.Agent;
import hepker.engine.SimulationLoop;
// TODO need to close the ui when an episode finishes
public class Main {
    private static SimulationLoop simulationLoop;

    public static void main(String[] args) {
        int numEpochs = 1000;
        simulationLoop = new SimulationLoop();
        do {
            runEpisode();
        } while (--numEpochs > 0);
        Agent.closeDatabase();
        System.out.println("Program finished");
    }

    private static void runEpisode() {
        simulationLoop.start();
        simulationLoop.awaitCompletion();
    }
}
