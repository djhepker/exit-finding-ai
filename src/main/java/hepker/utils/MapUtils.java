package hepker.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;


public final class MapUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapUtils.class);

    private MapUtils() {

    }

    public static int getArrayIndex(int x, int y) {
        return x + y * 7;
    }

    public static int getArrayIndex(ActionNode node) {
        return node.getXCoordinate() + node.getYCoordinate() * 7;
    }

    public static int[] getStateIntArr(JPanel[] panels) {
        int[] state = new int[panels.length];
        for (JPanel element : panels) {
            for (int i = 0; i < state.length; i++) {
                if (element.getBackground() == Color.BLACK) {
                    state[i] = 0;
                } else if (element.getBackground() == ColorConstants.SUCCESS_GREEN) {
                    state[i] = 1;
                } else if (element.getBackground() == ColorConstants.ERROR_RED) {
                    state[i] = 2;
                } else if (element.getBackground() == ColorConstants.GLACIER_WHITE) {
                    state[i] = 3;
                } else {
                    throw new RuntimeException("Unknown color: " + element.getBackground());
                }
            }
        }
        return state;
    }

    public static String getStateString(int[] state) {
        if (state == null) {
            LOGGER.error("Null state");
            throw new IllegalArgumentException("Null state");
        }
        StringBuilder stateKeyBuilder = new StringBuilder();
        int numberOfOpenSpaces = 0;
        for (int element : state) {
            if (element == 0) {
                ++numberOfOpenSpaces;
            } else if (numberOfOpenSpaces > 0) {
                char compressedNullSpaces = (char) (31 + numberOfOpenSpaces); // Board is never larger than ascii
                stateKeyBuilder.append(compressedNullSpaces)
                        .append(element);
                numberOfOpenSpaces = 0;
            } else {
                stateKeyBuilder.append(element);
            }
        }
        if (numberOfOpenSpaces > 0) {
            char compressedNullSpaces = (char) (31 + numberOfOpenSpaces);
            stateKeyBuilder.append(compressedNullSpaces);
        }
        return stateKeyBuilder.toString();
    }

    public static ActionNode getTravellerCoordinates(JPanel[] panels) {
        int squareComponent = (int) Math.sqrt(panels.length);
        for (int j = 0; j < squareComponent; ++j) {
            for (int i = 0; i < squareComponent; ++i) {
                if (panels[getArrayIndex(i, j)].getBackground() == ColorConstants.GLACIER_WHITE) {
                    return new ActionNode(i, j);
                }
            }
        }
        return null;
    }

    public static double getDistanceFromFinish(ActionNode node, ActionNode finish) {

    }
}
