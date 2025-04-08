package hepker.engine;

import hepker.ai.Agent;
import hepker.graphics.GraphicsHandler;
import hepker.utils.SimulationConstants;
import hepker.utils.Environment;
import hepker.utils.EpsilonHelper;
import hepker.utils.MapUtils;
import lombok.Getter;

import java.awt.Point;
import java.util.ArrayList;

public final class Engine {
    @Getter
    private static final int FAILURE_TURN = 100;
    @Getter
    private static int turnCount = 0;

    private final double epsilon;
    private final GraphicsHandler gHandler;
    private final ActionHandler actionHandler;
    private final Environment env;
    private final Agent zero;
    private final ArrayList<Point> actionList;

    private boolean isRunning;

    public Engine() {
        try {
            this.epsilon = EpsilonHelper.loadEpsilon();
            this.zero = new Agent();
            zero.setEpsilon(epsilon);
            this.gHandler = new GraphicsHandler(7);
            this.actionList = new ArrayList<>();
            gHandler.setTileColor(MapUtils.getArrayIndex(6, 6), SimulationConstants.SUCCESS_GREEN);
            gHandler.setTileColor(MapUtils.getArrayIndex(0, 0), SimulationConstants.GLACIER_WHITE);
            this.actionHandler = new ActionHandler(gHandler, actionList);
            this.env = new Environment(gHandler, actionHandler, actionList);
            this.isRunning = true;
            turnCount = 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        try {
            if (env.arrived()) {
                isRunning = false;
            } else {
                String stateKey = env.generateStateKey();
                zero.setStateKey(stateKey);
                env.updateDecisionContainer();
                int actionInt = zero.getActionInt(actionList.size());
                env.performAction(actionInt);
                String stateKeyPrime = env.generateStateKey();
                if (++turnCount >= FAILURE_TURN) { // Necessary for negative reward if failure
                    isRunning = false;
                }
                double reward = env.getDecisionReward();
                zero.giveReward(reward);
                zero.processData(stateKeyPrime, actionInt);
                actionList.clear();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRunning() {
        if (!isRunning) {
            Agent.pushQTableUpdate();
            EpsilonHelper.saveEpsilon(EpsilonHelper.subtractClean(epsilon, 0.001));
            System.out.println("Epsilon: " + epsilon);
            gHandler.dispose();
        }
        return isRunning;
    }

    public boolean travellerArrived() {
        return env.arrived();
    }
}
