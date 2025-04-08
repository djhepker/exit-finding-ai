package hepker.utils;

import hepker.engine.ActionHandler;
import hepker.engine.Engine;
import hepker.graphics.GraphicsHandler;
import lombok.Getter;

import java.awt.Point;
import java.util.ArrayList;

import static java.lang.Double.MAX_VALUE;

public final class Environment implements AIEnvironment {
    private final GraphicsHandler gHandler;
    private final ActionHandler actionHandler;

    private final ArrayList<Point> actionList;
    private Point traveller;
    private Point finish;
    private int[] state;
    @Getter
    private double distanceFromTarget;
    private double distanceRewardAdjustor;

    public Environment(GraphicsHandler argGHandler, ActionHandler argActionHandler, ArrayList<Point> argActionList) {
        this.distanceFromTarget = MAX_VALUE;
        this.gHandler = argGHandler;
        this.actionHandler = argActionHandler;
        this.actionList = argActionList;
        this.finish = gHandler.getFinishSpace();
        this.distanceRewardAdjustor = 0.0;
        this.traveller = new Point(-1, -1);
    }

    @Override
    public String generateStateKey() {
        state = MapUtils.getStateIntArr(gHandler.getPanels());
        return MapUtils.getStateString(state);
    }

    /**
     * Creating point list for possible moves. Clockwise
     */
    @Override
    public void updateDecisionContainer() {
        traveller = MapUtils.getTravellerCoordinates(gHandler.getPanels());
        int x = traveller.x;
        int y = traveller.y;

        if (y != 0) {
            actionList.add(new Point(x, y - 1));
        }
        if (x < 6) {
            actionList.add(new Point(x + 1, y));
        }
        if (y < 6) {
            actionList.add(new Point(x, y + 1));
        }
        if (x != 0) {
            actionList.add(new Point(x - 1, y));
        }
    }

    @Override
    public void performAction(int actionInt) {
        actionHandler.handleAction(actionInt, traveller);
        traveller = actionList.get(actionInt);
    }

    @Override
    public double getDecisionReward() {
        double updatedDistanceFromTarget = Math.hypot(finish.x - traveller.x, finish.y - traveller.y);
        if (traveller.equals(finish)) {
            return 10.0;
        } else if (Engine.getTurnCount() >= Engine.getFAILURE_TURN()) {
            return -10.0;
        }
        double sig = MathUtils.getSigmoid(updatedDistanceFromTarget, 5, 1, -0.93);
        if (updatedDistanceFromTarget > distanceFromTarget) {
            double penaltySig = MathUtils.getSigmoid(++distanceRewardAdjustor, 50, 1, 0.06);
            sig -= penaltySig;
        }
        distanceFromTarget = updatedDistanceFromTarget;

        return sig;
    }

    public boolean arrived() {
        return traveller.equals(finish);
    }
}
