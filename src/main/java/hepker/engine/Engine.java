package hepker.engine;

import hepker.ai.Agent;
import hepker.graphics.GraphicsHandler;
import hepker.utils.ColorConstants;
import hepker.utils.Environment;
import hepker.utils.MapUtils;

public final class Engine {
    private final GraphicsHandler gHandler;
    private final ActionHandler actionHandler;
    private final Environment env;
    private final Agent zero;

    public Engine() {
        try {
            this.zero = new Agent();
            this.gHandler = new GraphicsHandler(7);
            this.actionHandler = new ActionHandler(gHandler);
            this.env = new Environment(gHandler, actionHandler);
            gHandler.setTileColor(MapUtils.getArrayIndex(1, 1), ColorConstants.SUCCESS_GREEN);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        String stateKey = env.generateStateKey();
        zero.setStateKey(stateKey);
        env.updateDecisionContainer();
        int listSize = actionHandler.getListSize();
        int actionInt = zero.getActionInt(listSize);
        env.performAction(actionInt);
        env.updateDecisionContainer();
        String stateKeyPrime = env.generateStateKey();
        double reward = env.getDecisionReward();
    }
}
