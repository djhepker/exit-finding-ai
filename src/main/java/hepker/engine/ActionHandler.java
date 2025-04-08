package hepker.engine;

import hepker.graphics.GraphicsHandler;
import hepker.utils.ColorConstants;
import hepker.utils.MapUtils;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public final class ActionHandler {
    private final GraphicsHandler gHandler;
    private final ArrayList<Point> actionList;

    public ActionHandler(GraphicsHandler argGHandler, ArrayList<Point> argActionList) {
        this.gHandler = argGHandler;
        this.actionList = argActionList;
    }

    public void handleAction(int actionInt, Point traveller) {
        JPanel[] panels = gHandler.getPanels();
        Point action = actionList.get(actionInt);
        panels[MapUtils.getArrayIndex(action)].setBackground(ColorConstants.GLACIER_WHITE);
        panels[MapUtils.getArrayIndex(traveller)].setBackground(Color.BLACK);
    }
}
