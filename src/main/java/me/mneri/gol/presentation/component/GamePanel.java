package me.mneri.gol.presentation.component;

import lombok.Setter;
import me.mneri.gol.data.model.World;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    @Setter
    private Color backgroundColor;

    @Setter
    private int cellSizePx;

    @Setter
    private Color foregroundColor;

    private int panX;

    private int panY;

    private final RenderingHints renderingHints = new RenderingHints(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    @Setter
    private World world;

    private void doPaintComponent(Graphics2D g) {
        g.setRenderingHints(renderingHints);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(foregroundColor);

        for (int i = 0; i < world.getWidth(); i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                if (world.get(i, j)) {
                    g.fillRect(panX + i * cellSizePx, panY + j * cellSizePx, cellSizePx, cellSizePx);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        doPaintComponent((Graphics2D) g);
    }

    public void pan(int dx, int dy) {
        panX = Math.max(-world.getWidth() * cellSizePx + getWidth(), Math.min(0, panX + dx));
        panY = Math.max(-world.getHeight() * cellSizePx + getHeight(), Math.min(0, panY + dy));
    }

    public int translatePanelXCoordToWorldXCoord(int x) {
        return (-panX + x) / cellSizePx;
    }

    public int translatePanelYCoordToWorldYCoord(int y) {
        return (-panY + y) / cellSizePx;
    }
}
