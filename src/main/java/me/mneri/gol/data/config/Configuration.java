package me.mneri.gol.data.config;

import java.awt.*;
import java.util.Properties;

public class Configuration {
    private final Properties properties;

    public Configuration(Properties properties) {
        this.properties = properties;
    }

    public Color getDefaultBackgroundColor() {
        return getPropertyColor("me.mneri.gol.background-color");
    }

    public int getDefaultCellSizePx() {
        return getPropertyInt("me.mneri.gol.cell-size");
    }

    public Color getDefaultForegroundColor() {
        return getPropertyColor("me.mneri.gol.foreground-color");
    }

    public int getDefaultPanelHeight() {
        return getPropertyInt("me.mneri.gol.panel-height");
    }

    public int getDefaultPanelWidth() {
        return getPropertyInt("me.mneri.gol.panel-width");
    }

    public int getDefaultTaskThreshold() {
        return getPropertyInt("me.mneri.gol.task-threshold");
    }

    public int getDefaultWorldHeight() {
        return getPropertyInt("me.mneri.gol.world-height");
    }

    public int getDefaultWorldWidth() {
        return getPropertyInt("me.mneri.gol.world-width");
    }

    private Color getPropertyColor(String name) {
        return Color.decode(properties.getProperty(name));
    }

    private int getPropertyInt(String name) {
        return Integer.parseInt(properties.getProperty(name));
    }
}
