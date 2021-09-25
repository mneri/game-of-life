package me.mneri.gol.presentation.mvc;

import lombok.Getter;
import me.mneri.gol.presentation.component.GamePanel;
import me.mneri.gol.presentation.util.FPS;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;
import java.awt.*;

public class GameWindowView extends JFrame {
    @Getter
    private JComboBox<FPS> fpsComboBox;

    @Getter
    private GamePanel gamePanel;

    @Getter
    private JButton startButton;

    @Getter
    private JButton stopButton;

    @Inject
    private void postConstruct(
            @Named("me.mneri.gol.panel-height") int defaultPanelHeight,
            @Named("me.mneri.gol.panel-width") int defaultPanelWidth) {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(topPanel, BorderLayout.NORTH);

        fpsComboBox = new JComboBox<>(FPS.values());
        fpsComboBox.setSelectedIndex(FPS.SLOW.ordinal());
        topPanel.add(fpsComboBox);

        startButton = new JButton("Start");
        topPanel.add(startButton);

        stopButton = new JButton("Stop");
        topPanel.add(stopButton);

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(defaultPanelWidth, defaultPanelHeight));
        add(gamePanel, BorderLayout.CENTER);

        pack();
    }
}
