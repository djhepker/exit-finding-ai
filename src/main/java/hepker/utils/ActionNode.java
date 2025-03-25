package hepker.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public final class ActionNode {
    private final short xCoordinate;
    private final short yCoordinate;
    @Setter
    @Getter
    private ActionNode next;

    public ActionNode(int xCoordinate, int yCoordinate) {
        this.xCoordinate = (short) xCoordinate;
        this.yCoordinate = (short) yCoordinate;
        this.next = null;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }
}
