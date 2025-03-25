package hepker.engine;

import hepker.graphics.GraphicsHandler;
import hepker.utils.ActionNode;
import hepker.utils.MapUtils;
import lombok.Getter;

import java.awt.Color;

public final class ActionHandler {
    private final GraphicsHandler gHandler;
    @Getter
    private ActionNode head;
    @Getter
    private int listSize;

    public ActionHandler(GraphicsHandler argGHandler) {
        this.gHandler = argGHandler;
        this.listSize = 0;
        this.head = null;
    }

    public void handleAction(int actionInt) {
        for (int i = 0; i < actionInt; ++i) {
            head = head.getNext();
        }
        gHandler.setTileColor(MapUtils.getArrayIndex(head), Color.BLACK);
        head = null;
        listSize = 0;
    }

    /**
     * Pushes node into list. FILO
     *
     * @param node Node to become head node
     */
    public void insertAction(ActionNode node) {
        if (head != null) {
            head.setNext(node);
        }
        head = node;
        ++listSize;
    }
}
