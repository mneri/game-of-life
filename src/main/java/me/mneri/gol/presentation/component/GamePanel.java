/*
 * Copyright 2020 Massimo Neri <hello@mneri.me>
 *
 * This file is part of mneri/gol.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.mneri.gol.presentation.component;

import lombok.Setter;
import me.mneri.gol.data.model.World;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Panel for displaying the game.
 *
 * @author Massimo Neri
 */
public class GamePanel extends JPanel {
    /**
     * The background color (or the color of a dead cell).
     */
    @Setter
    private Color backgroundColor;

    /**
     * The size of a cell in pixels.
     */
    @Setter
    private int cellSizePx;

    /**
     * The foreground color (or the color of a live cell).
     */
    @Setter
    private Color foregroundColor;

    /**
     * The horizontal offset the world in pixels.
     */
    private int panX;

    /**
     * The vertical offset of the world in pixels.
     */
    private int panY;

    /**
     * The world to paint.
     */
    @Setter
    private World world;

    /**
     * Convenience method on top of {@link GamePanel#paintComponent(Graphics)} to receive a {@link Graphics2D} object.
     *
     * @param g The {@link Graphics2D} object to protect.
     */
    protected void doPaintComponent(final Graphics2D g) {
        Color originalColor = g.getColor();

        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (world != null) {
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

        g.setColor(originalColor);
    }

    /**
     * {@inheritDoc}
     *
     * @param g The {@code Graphics} object to protect.
     */
    @Override
    public void paintComponent(final Graphics g) {
        doPaintComponent((Graphics2D) g);
    }

    /**
     * Increase or decrease the horizontal and vertical offset.
     *
     * @param dx The horizontal movement in pixels. Can be negative.
     * @param dy The vertical movement in pixels. Can be negative.
     */
    public void pan(final int dx, final int dy) {
        panX = Math.max(-world.getWidth() * cellSizePx + getWidth(), Math.min(0, panX + dx));
        panY = Math.max(-world.getHeight() * cellSizePx + getHeight(), Math.min(0, panY + dy));
    }

    /**
     * Translate an x-coordinate from the panel's space to the world's space.
     *
     * @param panelXCoord An x-coordinate in the panel's space.
     * @return The corresponding x-coordinate in the world's space.
     */
    public int translatePanelXCoordToWorldXCoord(final int panelXCoord) {
        return (panelXCoord - panX) / cellSizePx;
    }

    /**
     * Translate a y-coordinate from the panel's space to the world's space.
     *
     * @param panelYCoord A y-coordinate in the panel's space.
     * @return The corresponding y-coordinate in the world's space.
     */
    public int translatePanelYCoordToWorldYCoord(final int panelYCoord) {
        return (panelYCoord - panY) / cellSizePx;
    }

    /**
     * Translate an x-coordinate from the world's space to the panel's space.
     *
     * @param worldXCoord An x-coordinate in the world's space.
     * @return The corresponding x-coordinate in the panel's space.
     */
    private int translateWorldXCoordToPanelXCoord(final int worldXCoord) {
        return worldXCoord * cellSizePx + panX;
    }

    /**
     * Translate an y-coordinate from the world's space to the panel's space.
     *
     * @param worldYCoord An y-coordinate in the world's space.
     * @return The corresponding x-coordinate in the panel's space.
     */
    private int translateWorldYCoordToPanelYCoord(final int worldYCoord) {
        return worldYCoord * cellSizePx + panY;
    }
}
