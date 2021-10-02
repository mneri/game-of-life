package me.mneri.gol.presentation.component;

import lombok.Setter;
import me.mneri.gol.data.model.World;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

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

    private void doPaintComponent(final Graphics2D g) {
        g.setRenderingHints(renderingHints);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(foregroundColor);

        // Get the world coordinates of the panel's top left corner.
        final int minWorldX = translatePanelXCoordToWorldXCoord(0);
        final int minWorldY = translatePanelYCoordToWorldYCoord(0);

        // Get the world coordinates of the panel's bottom right corner.
        final int maxWorldX = translatePanelXCoordToWorldXCoord(getWidth());
        final int maxWorldY = translatePanelYCoordToWorldYCoord(getHeight());

        // Paint the cells between to panel's top left and bottom right corners.
        for (int worldX = minWorldX; worldX <= maxWorldX; worldX++) {
            for (int worldY = minWorldY; worldY <= maxWorldY; worldY++) {
                if (world.get(worldX, worldY)) {
                    int panelX = translateWorldXCoordToPanelXCoord(worldX);
                    int panelY = translateWorldYCoordToPanelYCoord(worldY);
                    g.fillRect(panelX, panelY, cellSizePx, cellSizePx);
                }
            }
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        doPaintComponent((Graphics2D) g);
    }

    public void pan(final int dx, final int dy) {
        panX = Math.max(-world.getWidth() * cellSizePx + getWidth(), Math.min(0, panX + dx));
        panY = Math.max(-world.getHeight() * cellSizePx + getHeight(), Math.min(0, panY + dy));
    }

    public int translatePanelXCoordToWorldXCoord(final int panelXCoord) {
        return (panelXCoord - panX) / cellSizePx;
    }

    public int translatePanelYCoordToWorldYCoord(final int panelYCoord) {
        return (panelYCoord - panY) / cellSizePx;
    }

    private int translateWorldXCoordToPanelXCoord(final int worldXCoord) {
        return worldXCoord * cellSizePx + panX;
    }

    private int translateWorldYCoordToPanelYCoord(final int worldYCoord) {
        return worldYCoord * cellSizePx + panY;
    }
}
