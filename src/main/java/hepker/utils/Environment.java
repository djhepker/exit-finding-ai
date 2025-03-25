package hepker.utils;

import hepker.engine.ActionHandler;
import hepker.graphics.GraphicsHandler;

public final class Environment implements AIEnvironment {
    private final GraphicsHandler gHandler;
    private final ActionHandler actionHandler;
    private final ActionNode finish;
    private int[] state;
    private ActionNode traveller;

    public Environment(GraphicsHandler argGHandler, ActionHandler argActionHandler) {
        this.gHandler = argGHandler;
        this.actionHandler = argActionHandler;
        this.finish = gHandler.getFinishSpace();
    }

    @Override
    public String generateStateKey() {
        state = MapUtils.getStateIntArr(gHandler.getPanels());
        return MapUtils.getStateString(state);
    }

    /**
     * Using clockwise configuration for state starting at North, option zero. Node
     * ActionList is FILO
     */
    @Override
    public void updateDecisionContainer() {
        traveller = MapUtils.getTravellerCoordinates(gHandler.getPanels());
        int x = traveller.getXCoordinate();
        int y = traveller.getYCoordinate();
        if (x != 0) { // left
            actionHandler.insertAction(new ActionNode(x - 1, y));
        }
        if (y != 6) { // down
            actionHandler.insertAction(new ActionNode(x, y + 1));
        }
        if (x != 6) { // right
            actionHandler.insertAction(new ActionNode(x + 1, y));
        }
        if (y != 0) { // up
            actionHandler.insertAction(new ActionNode(x, y - 1));
        }
    }

    @Override
    public void performAction(int actionInt) {
        actionHandler.handleAction(actionInt);
    }

    @Override
    public double getDecisionReward() {
        return 0;
    }
}
