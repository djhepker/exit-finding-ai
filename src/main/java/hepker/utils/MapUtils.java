package hepker.utils;

import hepker.engine.Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Point;


public final class MapUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapUtils.class);

    private MapUtils() {

    }

    public static int getArrayIndex(int x, int y) {
        return x + y * 7;
    }

    public static int getArrayIndex(Point arg) {
        int index = arg.x + arg.y * 7;
        if (index < 0 || index >= 49 || arg.x < 0 || arg.y < 0 || arg.x >= 7 || arg.y >= 7) {
            String errorMessage = String.format("Index out of range: x=%d, y=%d, index=%d",
                    arg.x, arg.y, index);
            throw new RuntimeException(errorMessage);
        }
        return index;
    }


    public static int[] getStateIntArr(JPanel[] panels) {
        int[] state = new int[panels.length];
        state[0] = Engine.getTurnCount();
        for (int i = 1; i < panels.length; i++) {
            JPanel element = panels[i];
            if (element.getBackground() == Color.BLACK) {
                state[i] = 0;
            } else if (element.getBackground() == SimulationConstants.SUCCESS_GREEN) {
                state[i] = 1;
            } else if (element.getBackground() == SimulationConstants.ERROR_RED) {
                state[i] = 2;
            } else if (element.getBackground() == SimulationConstants.GLACIER_WHITE) {
                state[i] = 3;
            } else {
                throw new RuntimeException("Unknown color: " + element.getBackground());
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

    public static Point getTravellerCoordinates(JPanel[] panels) {
        for (int j = 0; j < 7; ++j) {
            for (int i = 0; i < 7; ++i) {

                int index = j * 7 + i;

                if (panels[index].getBackground() == SimulationConstants.GLACIER_WHITE) {
                    return new Point(i, j);
                }
            }
        }
        throw new RuntimeException("No traveller found with GLACIER_WHITE color.");
    }
}
