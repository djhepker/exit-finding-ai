package hepker.graphics;

import hepker.utils.ColorConstants;
import hepker.utils.MapUtils;
import lombok.Getter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

public final class GraphicsHandler extends JFrame {
    @Getter
    private final JPanel[] panels;
    private final JPanel parentPanel;

    public GraphicsHandler(int worldDimensions) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.parentPanel = new JPanel(new GridLayout(worldDimensions, worldDimensions));
        this.panels = new JPanel[worldDimensions * worldDimensions];
        parentPanel.setBorder(new LineBorder(Color.WHITE, 3));
        for (int i = 0; i < panels.length; ++i) {
            JPanel panel = new JPanel();
            panels[i] = panel;
            panel.setBackground(Color.BLACK);
            parentPanel.add(panel);
        }
        this.add(parentPanel);
        this.setSize(1200, 800);
        this.setVisible(true);
    }

    public void setTileColor(int position, Color color) {
        try {
            panels[position].setBackground(color);
        } catch (NullPointerException e) {
            String error = String.format("Nullptr thrown for position %d", position);
            throw new RuntimeException(error, e);
        }
    }

    public Point getFinishSpace() {
        for (int j = 0; j < 7; ++j) {
            for (int i = 0; i < 7; ++i) {
                int index = MapUtils.getArrayIndex(i, j);
                if (panels[index].getBackground() == ColorConstants.SUCCESS_GREEN) {
                    return new Point(i, j);
                }
            }
        }
        throw new RuntimeException("No finish space found");
    }
}
