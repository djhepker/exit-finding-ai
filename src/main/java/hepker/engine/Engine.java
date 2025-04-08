package hepker.engine;

import hepker.ai.Agent;
import hepker.graphics.GraphicsHandler;
import hepker.utils.ColorConstants;
import hepker.utils.Environment;
import hepker.utils.MapUtils;
import lombok.Getter;

import java.awt.Point;
import java.util.ArrayList;

public final class Engine {
    @Getter
    private static int turnCount = 0;

    private final GraphicsHandler gHandler;
    private final ActionHandler actionHandler;
    private final Environment env;
    private final Agent zero;
    private final ArrayList<Point> actionList;

    private boolean isRunning;

    public Engine() {
        try {
            this.zero = new Agent();
            this.gHandler = new GraphicsHandler(7);
            this.actionList = new ArrayList<>();
            gHandler.setTileColor(MapUtils.getArrayIndex(6, 6), ColorConstants.SUCCESS_GREEN);
            gHandler.setTileColor(MapUtils.getArrayIndex(0, 0), ColorConstants.GLACIER_WHITE);
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
            if (env.getDistanceFromTarget() == 0.0) {
                isRunning = false;
            } else {
                String stateKey = env.generateStateKey();
                zero.setStateKey(stateKey);
                env.updateDecisionContainer();
                int actionInt = zero.getActionInt(actionList.size());
                env.performAction(actionInt);
                String stateKeyPrime = env.generateStateKey();
                double reward = env.getDecisionReward();
                zero.giveReward(reward);
                zero.processData(stateKeyPrime, actionInt);
                actionList.clear();
                if (++turnCount >= 100) {
                    isRunning = false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRunning() {
        if (!isRunning) {
            Agent.pushQTableUpdate();
            gHandler.dispose();
        }
        return isRunning;
    }
}
