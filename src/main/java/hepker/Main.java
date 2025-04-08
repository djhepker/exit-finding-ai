package hepker;

import hepker.ai.Agent;
import hepker.engine.SimulationLoop;
import hepker.utils.SimulationConstants;

/**
 * Complete simulation demonstrating Agent's capabilities. Modify speed of simulation using SimulationConstants below
 */
public class Main {
    private static final int GAME_SPEED = SimulationConstants.MAX_GAME_SPEED; // modify for user viewing

    private static SimulationLoop simulationLoop;

    public static void main(String[] args) {
        int numEpochs = 1000;
        simulationLoop = new SimulationLoop(GAME_SPEED);
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
